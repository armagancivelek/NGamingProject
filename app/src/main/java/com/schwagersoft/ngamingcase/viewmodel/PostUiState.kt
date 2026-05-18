package com.schwagersoft.ngamingcase.viewmodel

import com.schwagersoft.ngamingcase.data.PostItem

data class PostUiState(
    val isLoading: Boolean = false,
    val posts: List<PostItem> = emptyList()
)
