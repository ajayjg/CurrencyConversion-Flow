package com.conversion.currency.util

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.conversion.currency.BuildConfig
import com.conversion.currency.util.Constants.Companion.PREFERENCE_KEY
import com.conversion.currency.util.network.APIService
import com.conversion.currency.util.room.CurrencyDao
import com.conversion.currency.util.room.ConverencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): APIService = retrofit.create(APIService::class.java)

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): ConverencyDatabase {
        return Room.databaseBuilder(context, ConverencyDatabase::class.java, "CurrencyTable.db")
            .build()
    }

    @Provides
    fun provideChannelDao(appDatabase: ConverencyDatabase): CurrencyDao {
        return appDatabase.currencyDao()
    }

    @Provides
    @Singleton
    fun setupSharedPreferences(@ApplicationContext app: Context):
            SharedPreferences = app.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE)
}