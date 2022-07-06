package com.devcraft.currencyassistant.presentation.ui.newsFragment

import androidx.lifecycle.*
import com.devcraft.currencyassistant.data.remote.PostServiceImpl
import com.devcraft.currencyassistant.data.remote.dto.PostResponse
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
                _postLiveData.postValue(
                    posts as MutableList<Post>) }
        }
    }

    private suspend fun checkResult(it: NetworkResult<PostResponse>) {
        when (it) {
            is NetworkResult.ApiError -> {
                println("ERROR - ${it.message}")
            }
            is NetworkResult.Success -> {
                postDatabase.insertPost(it.data.results.map { post ->
                    Post(
                        id = post.id,
                        domain = post.domain,
                        title = post.title,
                        published_at = post.published_at,
                        url = post.url
                    )
                })
            }
            else -> { println("CLEAR") }
        }
    }
}


