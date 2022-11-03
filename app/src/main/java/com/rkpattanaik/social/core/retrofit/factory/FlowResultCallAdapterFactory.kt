package com.rkpattanaik.social.core.retrofit.factory

import com.rkpattanaik.social.core.retrofit.error.APIError
import com.rkpattanaik.social.core.retrofit.adapter.FlowResultCallAdapter
import com.rkpattanaik.social.core.retrofit.annotation.Async
import com.rkpattanaik.social.core.retrofit.annotation.ErrorResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass

class FlowResultCallAdapterFactory private constructor(
    private val dispatcher: CoroutineDispatcher,
    private var errorClass: KClass<out APIError>,
    private var isAsync: Boolean
): CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Flow::class.java) return null

        val responseType = getParameterUpperBound(0, returnType as ParameterizedType)
        if (getRawType(responseType) != Result::class.java) return null

        annotations.forEach {
            if (it is Async) isAsync = it.value
            if (it is ErrorResponse) errorClass = it.value
        }

        val errorBodyConverter = retrofit.responseBodyConverter<APIError>(errorClass.java, annotations)

        return FlowResultCallAdapter<Any>(
            responseType = getParameterUpperBound(0, responseType as ParameterizedType),
            errorConverter = errorBodyConverter,
            dispatcher = dispatcher,
            isAsync = isAsync
        )
    }

    companion object {
        @JvmStatic
        fun create(
            dispatcher: CoroutineDispatcher = FlowAdapterFactorySettings.dispatcher,
            defaultApiErrorClass: KClass<out APIError>,
            isAsyncByDefault: Boolean = FlowAdapterFactorySettings.isAsyncByDefault
        ): FlowResultCallAdapterFactory {
            return FlowResultCallAdapterFactory(dispatcher, defaultApiErrorClass, isAsyncByDefault)
        }
    }
}