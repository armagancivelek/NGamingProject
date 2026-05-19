package com.schwagersoft.ngamingcase.util

sealed interface DataError : Error {
    sealed class Remote : DataError {
        data object RequestTimeout : Remote()
        data object NoInternet : Remote()
        data object Unauthorized : Remote()
        data object Forbidden : Remote()
        data object NotFound : Remote()
        data object TooManyRequests : Remote()
        data object Server : Remote()
        data object Serialization : Remote()
        data class Unknown(val message: String = "") : Remote()
    }
}

fun DataError.asUiMessage(): String = when (this) {
    is DataError.Remote.RequestTimeout -> "İstek zaman aşımına uğradı"
    is DataError.Remote.NoInternet -> "Bağlantı hatası. İnternet bağlantınızı kontrol edin."
    is DataError.Remote.Unauthorized -> "Oturum süresi doldu, tekrar giriş yapın"
    is DataError.Remote.Forbidden -> "Bu işlem için yetkiniz yok"
    is DataError.Remote.NotFound -> "İçerik bulunamadı"
    is DataError.Remote.TooManyRequests -> "Çok fazla istek gönderildi, lütfen bekleyin"
    is DataError.Remote.Server -> "Sunucu hatası, lütfen daha sonra tekrar deneyin"
    is DataError.Remote.Serialization -> "Veri işleme hatası"
    is DataError.Remote.Unknown -> message.ifEmpty { "Beklenmeyen bir hata oluştu" }
}
