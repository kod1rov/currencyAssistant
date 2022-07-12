package com.devcraft.currencyassistant.data.data_source.repository

import com.devcraft.currencyassistant.data.data_source.AppDao
import com.devcraft.currencyassistant.domain.repository.CryptoRepo
import com.devcraft.currencyassistant.entities.Crypto

class CryptoRepoImpl(private val dao: AppDao) : CryptoRepo {

    override suspend fun getCryptoData(): List<Crypto> = dao.getDataCrypto()
    override suspend fun insertCryptoData(list: List<Crypto>) = dao.insertDataCrypto(list)
}