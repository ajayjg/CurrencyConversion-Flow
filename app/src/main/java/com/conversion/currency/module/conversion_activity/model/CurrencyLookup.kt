package com.conversion.currency.module.conversion_activity.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_lookup_table")
data class CurrencyLookup (
    @PrimaryKey
    @ColumnInfo(name = "code")
    var code: String = "",
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "rate")
    var rate: Double = 1.0
)