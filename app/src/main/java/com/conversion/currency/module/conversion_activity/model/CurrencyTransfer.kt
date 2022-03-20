package com.conversion.currency.module.conversion_activity.model

class CurrencyTransfer(
    var data: MutableList<CurrencyLookup> = mutableListOf()
) {
    fun getFullCurrencyNames(): MutableList<String> {
        val rtKeys: MutableList<String> = mutableListOf()
        for (info in data) {
            //Even if total length is big, code will be seen
            val nameKey = "(${info.code})${info.name}"
            rtKeys.add(nameKey)
        }
        return rtKeys
    }
}