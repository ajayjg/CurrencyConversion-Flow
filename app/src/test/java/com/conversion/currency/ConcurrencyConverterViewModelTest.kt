package com.conversion.currency

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.net.ConnectivityManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.conversion.currency.module.conversion_activity.CurrencyConvertorViewModel
import com.conversion.currency.module.conversion_activity.model.CurrencyTransfer
import com.conversion.currency.module.conversion_activity.repository.CurrencyConverterDataSource
import com.conversion.currency.util.isNetworkConnected
import com.conversion.currency.util.network.APIService
import com.conversion.currency.util.network.Result
import com.conversion.currency.util.room.ConverencyDatabase
import com.nhaarman.mockito_kotlin.mock
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ConcurrencyConverterViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    protected val mockAppContext: Application = Mockito.mock(Application::class.java)
    private lateinit var viewModel: CurrencyConvertorViewModel
    var mockPrefs: SharedPreferences = Mockito.mock(SharedPreferences::class.java)
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var apiService: APIService

    @Mock
    lateinit var roomDatabase: ConverencyDatabase

    @Before
    fun setUp() {
        `when`(mockAppContext.getSharedPreferences(anyString(), anyInt()))
            .thenReturn(mockPrefs)
        apiService = mock()
        roomDatabase = Room.inMemoryDatabaseBuilder(
            mockAppContext, ConverencyDatabase::class.java
        ).allowMainThreadQueries().build()
        val repository =
            CurrencyConverterDataSource(roomDatabase.currencyDao(), apiService, mockPrefs)
        viewModel = CurrencyConvertorViewModel(mockAppContext, repository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testIsInitialisationWorking() = runBlockingTest {
        viewModel.initiate()
        `when`(isNetworkConnected(mockAppContext)).thenReturn(false)
        val stateFlow = MutableStateFlow<Result<CurrencyTransfer>>(Result.Loading(null))
        assertEquals(stateFlow, viewModel.stateFlow.value)
    }

    @Test
    fun testNotConnectedToNetworkWithoutDbData() {

    }

    @Test
    fun testNotConnectedToNetworkWithDbData() {

    }

    @Test
    fun testConnectedToNetworkWithDbData() {

    }

    @Test
    fun testConnectedToNetworkWithoutDbData() {

    }

    @Test
    fun testCalculatedRates() {

    }
}