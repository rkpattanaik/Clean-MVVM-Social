package com.rkpattanaik.social.core.adapter

import kotlinx.coroutines.CancellableContinuation
import retrofit2.Call
import retrofit2.Callback
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