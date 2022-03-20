package com.conversion.currency.module.conversion_activity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.conversion.currency.R
import com.conversion.currency.module.conversion_activity.model.CurrencyLookup
import com.conversion.currency.module.conversion_activity.model.CurrencyRatesModel
import com.conversion.currency.module.conversion_activity.model.CurrencyTransfer
import com.conversion.currency.module.conversion_activity.model.CurrencyTypes
import com.conversion.currency.module.conversion_activity.repository.CurrencyConverterDataSource
import com.conversion.currency.util.isNetworkConnected
import com.conversion.currency.util.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CurrencyConvertorViewModel @Inject constructor(
    var context: Application,
    private var dataSource: CurrencyConverterDataSource
) : ViewModel() {

    private val _currencyTransferStateFlow =
        MutableStateFlow<Result<CurrencyTransfer>>(Result.Loading(null))
    val stateFlow = _currencyTransferStateFlow.asStateFlow()

    private var currentMultiplier = 1.0
    private var previousMultiplier = 1.0

    fun initiate() {
        if (isNetworkConnected(context)) {
            _currencyTransferStateFlow.value = Result.Loading(null)
            makeInitRequest()
        } else {
            viewModelScope.launch {
                val offlineRateList = dataSource.loadRateList().toMutableList()
                if (offlineRateList.isEmpty()) {
                    _currencyTransferStateFlow.value =
                        Result.Error(context.getString(R.string.check_network))
                } else {
                    _currencyTransferStateFlow.value =
                        Result.Success(
                            CurrencyTransfer(
                                dataSource.loadRateList().toMutableList()
                            )
                        )
                }
            }
        }
    }

    private fun makeInitRequest() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val lastSave = dataSource.getLogTime()
                val period = 30 * 60 * 1000
                val logTime = Calendar.getInstance().time.time
                if ((lastSave + period) > logTime) {
                    // get the data from Room, fetched 30 mins back
                    _currencyTransferStateFlow.value =
                        Result.Success(
                            CurrencyTransfer(
                                dataSource.loadRateList().toMutableList()
                            )
                        )
                } else {
                    // fetch the data from api, 30mins over
                    val currencyTypes = async { dataSource.getCurrencyTypes() }
                    val rateData = async { delay(3000);dataSource.getCurrencyRates() }
                    val currentTransfer =
                        convertCurrencyTransfer(
                            currencyTypes.await().body(),
                            rateData.await().body()
                        )
                    _currencyTransferStateFlow.value = Result.Success(currentTransfer)
                }
            } catch (exception: Throwable) {
                _currencyTransferStateFlow.value = Result.Error(
                    exception.message
                        ?: context.getString(R.string.something_went_wrong)
                )
            }
        }
    }

    private suspend fun convertCurrencyTransfer(
        currencyTypes: CurrencyTypes?,
        rateData: CurrencyRatesModel?
    ): CurrencyTransfer {
        val returnData = CurrencyTransfer()
        val rates = rateData?.quotes
        val currencies = currencyTypes?.currencies
        rates?.let {
            val typeKeys: MutableList<String> = mutableListOf()
            val rateSource = rateData.source
            if (currencies != null) {
                typeKeys.addAll(currencies.keys.toList())
            } else {
                for (key in rates.keys.toList()) {
                    typeKeys.add(key.replaceFirst(rateSource, ""))
                }
            }
            val sources = rateData.source
            for (typeKey in typeKeys) {
                val fullKey = "${sources}${typeKey}"
                if (it.containsKey(fullKey)) {
                    var fullName = typeKey
                    if (currencies != null) {
                        fullName = currencies[typeKey] ?: ""
                    }
                    val currencyInfo = CurrencyLookup(
                        code = typeKey,
                        name = fullName,
                        rate = rates[fullKey] ?: 1.0
                    )
                    //Show USD on top by default
                    if (currencyInfo.code != "USD") {
                        returnData.data.add(currencyInfo)
                    } else {
                        returnData.data.add(0, currencyInfo)
                    }
                }
            }
            //Save all of the data in the Room DB and SP
            dataSource.updateAllRate(returnData.data)
            val logTime = Calendar.getInstance().time.time
            dataSource.putLogTime(logTime)
        }
        return returnData
    }

    fun getCalculatedRates(
        target: Double
    ): MutableList<CurrencyLookup>? {
        previousMultiplier = currentMultiplier
        currentMultiplier = target
        _currencyTransferStateFlow.value.data?.let {
            for (rate in it.data) {
                rate.rate /= previousMultiplier
                rate.rate *= currentMultiplier
            }
        }
        return _currencyTransferStateFlow.value.data?.data
    }
}
