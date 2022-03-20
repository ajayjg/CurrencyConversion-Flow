package com.conversion.currency.util.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.conversion.currency.module.conversion_activity.model.CurrencyLookup

@Dao
interface CurrencyDao {
    @Query("SELECT * from currency_lookup_table")
    suspend fun getAllList(): List<CurrencyLookup>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAllRate(rateList: List<CurrencyLookup>)
}