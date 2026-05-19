package com.schwagersoft.ngamingcase.viewmodel

sealed interface PostIntent {
    data object FetchPosts : PostIntent
    data class DeletePost(val id: Int) : PostIntent
    data class UpdatePost(val id: Int, val title: String, val body: String) : PostIntent
}
