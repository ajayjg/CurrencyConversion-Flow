package com.conversion.currency.module.conversion_activity.model

import com.google.gson.annotations.SerializedName

data class CurrencyRatesModel(
    @SerializedName("source") val source: String,
    @SerializedName("quotes") val quotes: Map<String, Double>
)