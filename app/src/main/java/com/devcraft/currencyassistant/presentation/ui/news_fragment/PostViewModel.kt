package com.devcraft.currencyassistant.presentation.ui.news_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcraft.currencyassistant.data.remote.dto.PostResponse
import com.devcraft.currencyassistant.data.remote.impl.PostServiceImpl
import com.devcraft.currencyassistant.data.remote.network.NetworkResult
import com.devcraft.currencyassistant.domain.use_case.PostUseCase
import com.devcraft.currencyassistant.entities.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val post: PostServiceImpl,
    private val postDatabase: PostUseCase
) : ViewModel() {

    private var _postLiveData = MutableLiveData<MutableList<Post>>(mutableListOf())
    var postLiveData: LiveData<MutableList<Post>> = _postLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            post.getPost().collect {
                checkResult(it)
            }
            getPost()
        }
    }

    private fun getPost() {
        viewModelScope.launch(Dispatchers.IO) {
            postDatabase.getPost().also { posts ->
                _postLiveData.postValue(posts as MutableList<Post>)
            }
        }
    }

    private suspend fun checkResult(result: NetworkResult<PostResponse>) {
        when (result) {
            is NetworkResult.ApiError -> {
                Log.d("API ERROR", result.message)
            }
            is NetworkResult.Success -> {
                insertPostData(result)
            }
            else -> { Log.d("CLEAR", "true") }
        }
    }

    private suspend fun insertPostData(response: NetworkResult.Success<PostResponse>) {
        postDatabase.insertPost(response.data.results.map { post ->
            Post(
                id = post.id,
                domain = post.domain,
                title = post.title,
                published_at = post.published_at,
                url = post.url
            )
        })
    }
}


