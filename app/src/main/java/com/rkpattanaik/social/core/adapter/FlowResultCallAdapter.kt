package com.rkpattanaik.social.core.adapter

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class FlowResultCallAdapter<T>(
    private val responseType: Type,
    private val errorConverter: Converter<ResponseBody, out APIError>,
    private val dispatcher: CoroutineDispatcher
): CallAdapter<T, Flow<Result<T>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Flow<Result<T>> = flow {
        emit(
            suspendCancellableCoroutine { continuation ->
                call.run {
                    onCancellation(continuation)

                    onCallback(
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
}