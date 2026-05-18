package com.schwagersoft.ngamingcase.data

import kotlinx.serialization.Serializable

@Serializable
data class PostItem (
    val userId : Int = 0,
    val id : Int = 0,
    val title : String = "",
    val body : String = ""
)