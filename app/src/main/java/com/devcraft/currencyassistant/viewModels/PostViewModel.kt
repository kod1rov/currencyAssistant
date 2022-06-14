package com.devcraft.currencyassistant.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcraft.currencyassistant.data.remote.PostServiceImpl
import com.devcraft.currencyassistant.data.remote.dto.PostResponse
import com.devcraft.currencyassistant.data.remote.dto.Results
import com.devcraft.currencyassistant.data.remote.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val post: PostServiceImpl
) : ViewModel() {

    private var _state = MutableStateFlow<NetworkResult<PostResponse>>(NetworkResult.Success(PostResponse()))
    val state : StateFlow<NetworkResult<PostResponse>> = _state

    init{
        viewModelScope.launch(Dispatchers.IO) {
            println("f${post.getPost()}")
            post.getPost().collect {
                _state.value = it
            }
        }
    }
}

