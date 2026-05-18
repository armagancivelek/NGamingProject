package com.schwagersoft.ngamingcase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schwagersoft.ngamingcase.data.PostItem
import com.schwagersoft.ngamingcase.domain.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostUiState())
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    private val _errorEvent = Channel<String>()
    val errorEvent = _errorEvent.receiveAsFlow()

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val result = repository.getPosts()
                _uiState.update { it.copy(isLoading = false, posts = result) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                _errorEvent.send(e.message ?: "Unknown error")
            }
        }
    }

    fun deletePost(id: Int) {
        _uiState.update { state ->
            state.copy(posts = state.posts.filter { it.id != id })
        }
    }

    fun updatePost(id: Int, title: String, body: String) {
        _uiState.update { state ->
            state.copy(posts = state.posts.map { post ->
                if (post.id == id) post.copy(title = title, body = body) else post
            })
        }
    }

    fun getPostById(id: Int): PostItem? {
        return _uiState.value.posts.find { it.id == id }
    }
}
