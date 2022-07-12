package com.devcraft.currencyassistant.domain.use_case

import com.devcraft.currencyassistant.domain.repository.CryptoRepo
import com.devcraft.currencyassistant.entities.Crypto

class InsertCrypto(private val repo: CryptoRepo) {
    suspend operator fun invoke(crypto: List<Crypto>) = repo.insertCryptoData(crypto)
}