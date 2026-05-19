package com.schwagersoft.ngamingcase.viewmodel

import androidx.lifecycle.viewModelScope
import com.schwagersoft.ngamingcase.domain.PostRepository
import com.schwagersoft.ngamingcase.util.asUiMessage
import com.schwagersoft.ngamingcase.util.onError
import com.schwagersoft.ngamingcase.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: PostRepository
) : BaseViewModel<PostUiState, PostIntent, PostEvent>(PostUiState()) {

    init {
        onIntent(PostIntent.FetchPosts)
    }

    override fun onIntent(intent: PostIntent) {
        when (intent) {
            is PostIntent.FetchPosts -> fetchPosts()
            is PostIntent.DeletePost -> deletePost(intent.id)
            is PostIntent.UpdatePost -> updatePost(intent.id, intent.title, intent.body)
        }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            repository.getPosts()
                .onSuccess { posts -> updateState { copy(posts = posts) } }
                .onError { error -> sendEvent(PostEvent.ShowError(error.asUiMessage())) }
            updateState { copy(isLoading = false) }
        }
    }

    private fun deletePost(id: Int) {
        updateState { copy(posts = posts.filter { it.id != id }) }
    }

    private fun updatePost(id: Int, title: String, body: String) {
        updateState {
            copy(posts = posts.map { post ->
                if (post.id == id) post.copy(title = title, body = body) else post
            })
        }
    }
}
