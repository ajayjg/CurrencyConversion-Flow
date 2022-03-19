package com.androiddevs.mvvmnewsapp.api

import com.conversion.currency.module.conversion_activity.model.CurrencyModel
import com.conversion.currency.util.Constants
import com.daily.news.module.news_feed.model.NewsResponse
import com.daily.news.utils.Constants
import com.daily.news.utils.Constants.Companion.STARTING_PAGE_INDEX
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("/live")
    suspend fun getConversionFactors(
        @Query("access_key")
        access_key: String = Constants.API_KEY,
        @Query("format")
        format: Int = 1
    ): Response<CurrencyModel>

}