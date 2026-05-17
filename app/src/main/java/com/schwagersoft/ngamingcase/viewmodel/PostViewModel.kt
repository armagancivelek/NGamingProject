package com.schwagersoft.ngamingcase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schwagersoft.ngamingcase.data.PostItem
import com.schwagersoft.ngamingcase.domain.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val _posts = MutableStateFlow<List<PostItem>>(emptyList())
    val posts: StateFlow<List<PostItem>> = _posts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = repository.getPosts()
                _posts.value = result
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deletePost(id: Int) {
        _posts.value = _posts.value.filter { it.id != id }
    }

    fun updatePost(id: Int, title: String, body: String) {
        _posts.value = _posts.value.map { post ->
            if (post.id == id) post.copy(title = title, body = body) else post
        }
    }

    fun getPostById(id: Int): PostItem? {
        return _posts.value.find { it.id == id }
    }
}
