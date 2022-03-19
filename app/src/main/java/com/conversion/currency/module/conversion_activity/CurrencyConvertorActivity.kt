package com.conversion.currency.module.conversion_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.conversion.currency.R
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels

@AndroidEntryPoint
class CurrencyConvertorActivity : AppCompatActivity() {

    private val mViewModel by viewModels<CurrencyConvertorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}