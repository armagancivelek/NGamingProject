package com.schwagersoft.ngamingcase.domain

import com.schwagersoft.ngamingcase.data.PostItem
import com.schwagersoft.ngamingcase.network.PostApiService
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val apiService: PostApiService
) : PostRepository {
    override suspend fun getPosts(): List<PostItem> {
        return apiService.getPosts()
    }
}