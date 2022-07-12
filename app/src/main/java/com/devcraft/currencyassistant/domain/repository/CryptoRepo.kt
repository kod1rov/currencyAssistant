package com.devcraft.currencyassistant.domain.repository

import com.devcraft.currencyassistant.entities.Crypto

interface CryptoRepo {
    suspend fun getCryptoData() : List<Crypto>
    suspend fun insertCryptoData(list: List<Crypto>)
}