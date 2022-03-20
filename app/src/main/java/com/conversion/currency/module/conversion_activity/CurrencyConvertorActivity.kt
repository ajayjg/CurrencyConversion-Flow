package com.conversion.currency.module.conversion_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.conversion.currency.R
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.androiddevs.mvvmnewsapp.adapters.ConversionGridAdapter
import com.conversion.currency.databinding.ActivityMainBinding
import com.conversion.currency.util.checkInputValue
import com.conversion.currency.util.hideKeyboard
import com.conversion.currency.util.network.Result
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CurrencyConvertorActivity : AppCompatActivity() {

    private val mViewModel by viewModels<CurrencyConvertorViewModel>()
    lateinit var mBinding: ActivityMainBinding
    lateinit var gridAdapter: ConversionGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        mBinding.apply {
            lifecycleOwner = this@CurrencyConvertorActivity
            viewModel = mViewModel
        }
        setContentView(mBinding.root)

        gridAdapter = ConversionGridAdapter()
        mBinding.rvCurrencyConv.apply {
            adapter = gridAdapter
            layoutManager = GridLayoutManager(this@CurrencyConvertorActivity, 3)
        }

        mBinding.actCurrencyInput.doAfterTextChanged {
            val getString = it?.toString() ?: "1"
            val getNumber: Double = checkInputValue(getString)
            gridAdapter.differ.submitList(
                mViewModel.getCalculatedRates(getNumber)?.map { it.copy() })
        }

        lifecycleScope.launchWhenStarted {
            mViewModel.stateFlow.collect {
                when (it) {
                    is Result.Success -> {
                        mBinding.actCurrencyInput.isEnabled = true
                        mBinding.progressBar.visibility = View.GONE
                        it.data?.let {
                            val adapter =
                                ArrayAdapter(
                                    this@CurrencyConvertorActivity,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    it.getFullCurrencyNames()
                                )
                            mBinding.spCurrencySelect.adapter = adapter
                            mBinding.spCurrencySelect.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                                    override fun onItemSelected(
                                        parent: AdapterView<*>,
                                        view: View, pos: Int, id: Long
                                    ) {
                                        hideKeyboard(this@CurrencyConvertorActivity)
                                        val getString = mBinding.actCurrencyInput.text.toString()
                                        val getNumber: Double = checkInputValue(getString)
                                        gridAdapter.updateList(
                                            getNumber,
                                            it.data[pos].rate,
                                            it.data
                                        )
                                    }
                                }
                            val dataSize = it.data.size
                            mBinding.tvNoList.text =
                                if (dataSize > 0) "" else getText(R.string.no_conversions)
                            gridAdapter.differ.submitList(it.data)
                        }
                    }
                    is Result.Error -> {
                        mBinding.progressBar.visibility = View.GONE
                        mBinding.tvNoList.text = it.message
                        Toast.makeText(
                            this@CurrencyConvertorActivity,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Result.Loading -> {
                        mBinding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
        mViewModel.initiate()
    }
}