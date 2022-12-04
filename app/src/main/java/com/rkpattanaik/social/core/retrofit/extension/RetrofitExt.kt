package com.rkpattanaik.social.core.retrofit.extension

import com.rkpattanaik.social.core.retrofit.error.APIError
import kotlinx.coroutines.CancellableContinuation
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import kotlin.text.Charsets.UTF_8

internal fun Call<*>.onCancellation(continuation: CancellableContinuation<*>) {
    continuation.invokeOnCancellation {
        cancel()
    }
}

internal fun <T> Call<T>.executeAsync(
    success: (response: Response<T>) -> Unit,
    failure: (error: Throwable) -> Unit
) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            success(response)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            println("FlowAdapter : OnFailure : ${t.message}")
            failure(t)
        }
    })
}

fun ResponseBody.stringDuplicate(): String {
    val peekSource = source().peek()
    val charset = contentType()?.charset(UTF_8) ?: UTF_8
    return peekSource.readString(charset)
}

fun <T> Response<T>.asResult(
    errorConverter: Converter<ResponseBody, out APIError>
): Result<T> {
    return if (isSuccessful) {
        if (body() != null) Result.success(body()!!)
        else Result.failure(Throwable("${code()}: Empty Response Body"))
    } else {
        val errorBody = errorBody()
        val errorString = errorBody?.stringDuplicate()
        val errorMessage = message()
        val error = when {
            errorBody == null -> null
            errorBody.contentLength() == 0L -> null
            else -> try {
                errorConverter.convert(errorBody)
            } catch (ex: Exception) {
                println("FlowAdapter : Exception while converting Error : ${ex.message}")
                null
            }
        }

        println("FlowAdapter: ErrorResponse : $errorString")
        println("FlowAdapter: ErrorResponse Message : $errorMessage")

        val message = errorString ?: errorMessage ?: "Unknown Error"
        if (error != null) {
            error.code = code()
            error.rawError = errorString
            error.message = error.message ?: message
            Result.failure(error)
        } else {
            Result.failure(Throwable(message))
        }
    }
}