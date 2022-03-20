package com.conversion.currency.util.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.conversion.currency.module.conversion_activity.model.CurrencyLookup

@Database(entities = [CurrencyLookup::class], version = 1)
abstract class ConverencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}