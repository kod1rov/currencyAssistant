package com.devcraft.currencyassistant.domain.use_case

data class CryptoUseCase(
    val getCryptoData: GetCrypto,
    val insertCrypto: InsertCrypto
)
