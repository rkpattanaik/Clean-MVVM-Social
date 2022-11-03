package com.rkpattanaik.social.core.retrofit.adapter

import com.rkpattanaik.social.core.retrofit.error.APIError
import com.rkpattanaik.social.core.retrofit.extension.asResult
import com.rkpattanaik.social.core.retrofit.extension.executeAsync
import com.rkpattanaik.social.core.retrofit.extension.onCancellation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class FlowResultCallAdapter<T>(
    private val responseType: Type,
    private val errorConverter: Converter<ResponseBody, out APIError>,
    private val dispatcher: CoroutineDispatcher,
    private val isAsync: Boolean
): CallAdapter<T, Flow<Result<T>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Flow<Result<T>> = if (isAsync) {
        flowAsync(call)
    } else {
        runBlocking(dispatcher) { flowSync(call) }
    }

    private fun flowAsync(call: Call<T>): Flow<Result<T>> = flow {
        emit(
            suspendCancellableCoroutine { continuation ->
                call.run {
                    onCancellation(continuation)

                    executeAsync(
                        { response ->
                            continuation.resumeWith(runCatching {
                                response.asResult(errorConverter)
                            })
                        },
                        { continuation.resumeWith(Result.failure(it)) }
                    )
                }
            }
        )
    }.flowOn(dispatcher)

    private fun flowSync(call: Call<T>): Flow<Result<T>> = flow {
        emit(
            try {
                call.execute().asResult(errorConverter)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }.flowOn(dispatcher)
}