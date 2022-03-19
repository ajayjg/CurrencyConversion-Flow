package com.conversion.currency.module.conversion_activity

import android.content.Context
import androidx.lifecycle.ViewModel
import com.conversion.currency.module.conversion_activity.repository.CurrencyConverterDataSource
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@HiltViewModel
class CurrencyConvertorViewModel(context: Context, dataSource : CurrencyConverterDataSource) : ViewModel() {

}