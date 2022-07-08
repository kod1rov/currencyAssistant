package com.devcraft.currencyassistant.presentation.ui.main_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcraft.currencyassistant.data.remote.impl.CurrencyServiceImpl
import com.devcraft.currencyassistant.data.remote.dto.DataCurrency
import com.devcraft.currencyassistant.data.remote.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currencyServiceImpl: CurrencyServiceImpl
) : ViewModel() {

    private var _currencyLiveData = MutableLiveData<MutableList<DataCurrency>>(mutableListOf())
    var currencyLiveData: LiveData<MutableList<DataCurrency>>? = _currencyLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            currencyServiceImpl.getDataCurrency().collect {
                when (it) {
                    is NetworkResult.ApiError -> {
                        println("ERROR - ${it.message}")
                    }
                    is NetworkResult.Success -> {
                        _currencyLiveData.postValue(it.data.data as MutableList<DataCurrency>)
                    }
                    else -> { println("CLEAR") }
                }
            }
        }
    }

}