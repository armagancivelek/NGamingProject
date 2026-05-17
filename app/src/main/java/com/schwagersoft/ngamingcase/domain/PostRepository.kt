package com.schwagersoft.ngamingcase.domain

import com.schwagersoft.ngamingcase.data.PostItem

interface PostRepository {
    suspend fun getPosts(): List<PostItem>
}