package com.schwagersoft.ngamingcase.domain

import com.schwagersoft.ngamingcase.data.PostItem
import com.schwagersoft.ngamingcase.network.PostApiService
import com.schwagersoft.ngamingcase.util.DataError
import com.schwagersoft.ngamingcase.util.Result
import com.schwagersoft.ngamingcase.util.safeApiCall
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val apiService: PostApiService
) : PostRepository {
    override suspend fun getPosts(): Result<List<PostItem>, DataError.Remote> {
        return safeApiCall { apiService.getPosts() }
    }
}