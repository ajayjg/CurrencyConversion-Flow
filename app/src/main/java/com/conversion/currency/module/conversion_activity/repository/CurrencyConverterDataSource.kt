package com.conversion.currency.module.conversion_activity.repository

import android.content.SharedPreferences
import com.conversion.currency.module.conversion_activity.model.CurrencyLookup
import com.conversion.currency.module.conversion_activity.model.CurrencyRatesModel
import com.conversion.currency.module.conversion_activity.model.CurrencyTypes
import com.conversion.currency.util.network.APIService
import com.conversion.currency.util.room.CurrencyDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CurrencyConverterDataSource @Inject constructor(
    var currencyDao: CurrencyDao,
    var apiService: APIService,
    var sharedPreferences: SharedPreferences
) {
    suspend fun loadRateList(): List<CurrencyLookup> = currencyDao.getAllList()
    suspend fun updateAllRate(saveList: List<CurrencyLookup>) = currencyDao.updateAllRate(saveList)

    suspend fun getCurrencyTypes() = apiService.getCurrencyTypes()
    suspend fun getCurrencyRates() = apiService.getCurrencyRates()

    fun getLogTime() = sharedPreferences.getLong("logTime", -1)
    fun putLogTime(logTime: Long) {
        sharedPreferences.edit().putLong("logTime", logTime).apply()
    }

}