package com.rkpattanaik.social.core.adapter

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class FlowResultCallAdapterFactory private constructor(
    private val dispatcher: CoroutineDispatcher,
    private val errorClass: Class<out APIError>
): CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Flow::class.java) return null

        if (returnType !is ParameterizedType) {
            throw IllegalStateException(
                "Flow return type must be parameterized as Flow<Foo>"
            )
        }

        val responseType = getParameterUpperBound(0, returnType)

        if (getRawType(responseType) != Result::class.java) return null

        if (responseType !is ParameterizedType) {
            throw IllegalStateException(
                "Result must be parameterized as Result<Foo>"
            )
        }

        val errorBodyConverter = retrofit.responseBodyConverter<APIError>(errorClass, annotations)

        return FlowResultCallAdapter<Any>(
            responseType = getParameterUpperBound(0, responseType),
            errorConverter = errorBodyConverter,
            dispatcher = dispatcher
        )
    }

    companion object {
        @JvmStatic
        fun create(
            dispatcher: CoroutineDispatcher = FlowCallAdapterSettings.dispatcher,
            apiErrorClass: Class<out APIError>
        ): FlowResultCallAdapterFactory {
            return FlowResultCallAdapterFactory(dispatcher, apiErrorClass)
        }
    }
}