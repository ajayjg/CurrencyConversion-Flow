package com.conversion.currency.util.network

import com.conversion.currency.module.conversion_activity.model.CurrencyRatesModel
import com.conversion.currency.module.conversion_activity.model.CurrencyTypes
import com.conversion.currency.util.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("/api/list?access_key=${API_KEY}&format=1")
    suspend fun getCurrencyTypes(): Response<CurrencyTypes>

    @GET("/api/live?access_key=${API_KEY}&format=1")
    suspend fun getCurrencyRates(@Query("source") source: String = "USD"): Response<CurrencyRatesModel>
}