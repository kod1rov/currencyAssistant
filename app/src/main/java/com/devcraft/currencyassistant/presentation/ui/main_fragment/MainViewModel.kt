package com.devcraft.currencyassistant.presentation.ui.main_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcraft.currencyassistant.data.remote.dto.CurrencyResponse
import com.devcraft.currencyassistant.data.remote.impl.CurrencyServiceImpl
import com.devcraft.currencyassistant.data.remote.network.NetworkResult
import com.devcraft.currencyassistant.domain.use_case.CryptoUseCase
import com.devcraft.currencyassistant.entities.Crypto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currencyServiceImpl: CurrencyServiceImpl,
    private val cryptoUseCase: CryptoUseCase
) : ViewModel() {

    private var _currencyLiveData = MutableLiveData<MutableList<Crypto>>(mutableListOf())
    var currencyLiveData: LiveData<MutableList<Crypto>> = _currencyLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            currencyServiceImpl.getDataCurrency().collect {
                checkResult(it)
            }
            getCryptoDataFromDB()
        }
    }

     private fun getCryptoDataFromDB(){
         viewModelScope.launch {
             cryptoUseCase.getCryptoData().also { crypto ->
                 _currencyLiveData.postValue(crypto as MutableList<Crypto>)
             }
         }
    }


    private suspend fun checkResult(result: NetworkResult<CurrencyResponse>) {
        when (result) {
            is NetworkResult.ApiError -> {
                Log.d("ERROR", result.message)
            }
            is NetworkResult.Success -> {
                insertData(result)
            }
            else -> { Log.d("CLEAR", "true") }
        }
    }

    private suspend fun insertData(response: NetworkResult.Success<CurrencyResponse>) {
        cryptoUseCase.insertCrypto(response.data.data.map { crypto ->
            Crypto(
                id = crypto.id,
                rank = crypto.rank,
                name = crypto.name,
                symbol = crypto.symbol,
                priceUsd = crypto.priceUsd,
                changePercent24Hr = crypto.changePercent24Hr
            )
        })
    }
}