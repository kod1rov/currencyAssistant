package com.devcraft.currencyassistant.presentation.ui.chart_crypto

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcraft.currencyassistant.data.remote.dto.DataHistory
import com.devcraft.currencyassistant.data.remote.impl.CurrencyHistoryImpl
import com.devcraft.currencyassistant.data.remote.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val currencyHistoryImpl: CurrencyHistoryImpl
) : ViewModel() {

    private var _historyLiveData = MutableLiveData<MutableList<DataHistory>>(mutableListOf())
    var historyLiveData: LiveData<MutableList<DataHistory>> = _historyLiveData

    fun getHistoryCrypto(id: String, filter: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyHistoryImpl.getCurrencyHistory(id, filter).collect {
                when (it) {
                    is NetworkResult.ApiError -> {
                        Log.d("ERROR", it.message)
                    }
                    is NetworkResult.Success -> {
                        _historyLiveData.postValue(it.data.data as MutableList<DataHistory>)
                    }
                    else -> { Log.d("CLEAR", "MESS") }
                }
            }
        }
    }
}