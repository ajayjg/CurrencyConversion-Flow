//package com.conversion.currency
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.room.Room
//import androidx.test.core.app.ApplicationProvider
//import com.conversion.currency.module.conversion_activity.model.CurrencyLookup
//import com.conversion.currency.util.room.ConverencyDatabase
//import com.conversion.currency.util.room.CurrencyDao
//import kotlinx.coroutines.runBlocking
//import org.junit.After
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//
//class CurrencyDoaTest {
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var database : ConverencyDatabase
//    private lateinit var dao : CurrencyDao
//
//    @Before
//    fun setup() {
//        database = Room.inMemoryDatabaseBuilder(
//            ApplicationProvider.getApplicationContext(), ConverencyDatabase::class.java
//        ).allowMainThreadQueries().build()
//        dao = database.currencyDao()
//    }
//
//    @After
//    fun teardown() {
//        database.close()
//    }
//
////    @Test
////    fun insertCurrencyInfo() = runBlocking {
////        val currencyInfo = CurrencyLookup("USD","Dollar",2.0)
////    }
//}