package com.schwagersoft.ngamingcase.util

import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.io.IOException

suspend fun <T> safeApiCall(call: suspend () -> T): Result<T, DataError.Remote> {
    return try {
        Result.Success(call())
    } catch (e: SocketTimeoutException) {
        Result.Error(DataError.Remote.RequestTimeout)
    } catch (e: IOException) {
        Result.Error(DataError.Remote.NoInternet)
    } catch (e: HttpException) {
        Result.Error(mapHttpError(e.code()))
    }
}

private fun mapHttpError(code: Int): DataError.Remote {
    return when (code) {
        400 -> DataError.Remote.Unknown("Geçersiz istek")
        401 -> DataError.Remote.Unauthorized
        403 -> DataError.Remote.Forbidden
        404 -> DataError.Remote.NotFound
        408 -> DataError.Remote.RequestTimeout
        429 -> DataError.Remote.TooManyRequests
        in 500..599 -> DataError.Remote.Server
        else -> DataError.Remote.Unknown()
    }
}
