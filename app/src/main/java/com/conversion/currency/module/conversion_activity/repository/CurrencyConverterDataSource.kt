package com.conversion.currency.module.conversion_activity.repository

import javax.inject.Inject

class CurrencyConverterDataSource @Inject constructor(
    persistence: CurrencyConverterPersistence,
    service: CurrencyConverterService
)