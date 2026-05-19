package com.schwagersoft.ngamingcase.viewmodel

sealed interface PostEvent {
    data class ShowError(val message: String) : PostEvent
}
