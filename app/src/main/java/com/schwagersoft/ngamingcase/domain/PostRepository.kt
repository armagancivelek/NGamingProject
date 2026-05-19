package com.schwagersoft.ngamingcase.domain

import com.schwagersoft.ngamingcase.data.PostItem
import com.schwagersoft.ngamingcase.util.DataError
import com.schwagersoft.ngamingcase.util.Result

interface PostRepository {
    suspend fun getPosts(): Result<List<PostItem>, DataError.Remote>
}