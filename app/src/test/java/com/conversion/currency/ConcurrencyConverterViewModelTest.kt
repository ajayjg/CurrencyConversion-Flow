package com.conversion.currency

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.conversion.currency.module.conversion_activity.CurrencyConvertorViewModel
import com.conversion.currency.module.conversion_activity.repository.CurrencyConverterDataSource
import com.conversion.currency.util.network.APIService
import com.conversion.currency.util.room.ConverencyDatabase
import com.nhaarman.mockito_kotlin.mock
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito


@RunWith(AndroidJUnit4::class)
class ConcurrencyConverterViewModelTest : TestCase() {

    private lateinit var viewModel: CurrencyConvertorViewModel

    @Mock
    lateinit var mockContext: Context

    @Mock
    lateinit var mockPrefs: SharedPreferences

    @Mock
    lateinit var apiService: APIService

    @Mock
    lateinit var roomDatabase: ConverencyDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Application>()
        Mockito.`when`(mockContext.getSharedPreferences(anyString(), anyInt()))
            .thenReturn(mockPrefs)
        apiService = mock()
        roomDatabase = Room.inMemoryDatabaseBuilder(
            context, ConverencyDatabase::class.java
        ).allowMainThreadQueries().build()
        val repository =
            CurrencyConverterDataSource(roomDatabase.currencyDao(), apiService, mockPrefs)
        viewModel = CurrencyConvertorViewModel(context, repository)
    }

    @Test
    fun testCurrencyConverterViewmodel() {

    }

    @Test
    fun testCurrencyInfoInsertion() {

    }
}