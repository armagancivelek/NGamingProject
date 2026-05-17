package com.schwagersoft.ngamingcase.network

import com.schwagersoft.ngamingcase.data.PostItem
import retrofit2.http.GET

interface PostApiService {
    @GET("posts")
    suspend fun getPosts(): List<PostItem>
}
