package com.rkpattanaik.social.core.adapter

import kotlinx.coroutines.CancellableContinuation
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response

internal fun Call<*>.onCancellation(continuation: CancellableContinuation<*>) {
    continuation.invokeOnCancellation {
        cancel()
    }
}

internal fun <T> Call<T>.onCallback(
    success: (response: Response<T>) -> Unit,
    failure: (error: Throwable) -> Unit
) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            success(response)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            failure(t)
        }
    })
}

fun <T> Response<T>.asResult(
    errorConverter: Converter<ResponseBody, out APIError>
): Result<T> {
    return if (isSuccessful) {
        if (body() != null) Result.success(body()!!)
        else Result.failure(Throwable("${code()}: Empty Response Body"))
    } else {
        val errorBody = errorBody()
        val error = when {
            errorBody == null -> null
            errorBody.contentLength() == 0L -> null
            else -> try {
                errorConverter.convert(errorBody)
            } catch (ex: Exception) {
                null
            }
        }

        val message = if (error != null && error.message().isNotEmpty()){
            error.message()
        } else {
            "${code()}: No Error Message"
        }

        Result.failure(Throwable(message))
    }
}