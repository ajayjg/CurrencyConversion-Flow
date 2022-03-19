package com.conversion.currency.module.conversion_activity.model

import com.google.gson.annotations.SerializedName

data class CurrencyModel(
    @SerializedName("success") var success: Boolean? = null,
    @SerializedName("terms") var terms: String? = null,
    @SerializedName("privacy") var privacy: String? = null,
    @SerializedName("timestamp") var timestamp: Int? = null,
    @SerializedName("source") var source: String? = null,
    @SerializedName("quotes") var quotes: Double? = null
)