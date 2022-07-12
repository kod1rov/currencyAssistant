package com.devcraft.currencyassistant.domain.use_case

import com.devcraft.currencyassistant.domain.repository.CryptoRepo
import com.devcraft.currencyassistant.entities.Crypto

class  GetCrypto(private val repo: CryptoRepo) {
    suspend operator fun invoke(): List<Crypto> = repo.getCryptoData()
}