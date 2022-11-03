package com.rkpattanaik.social.core.retrofit.annotation

import com.rkpattanaik.social.core.retrofit.error.APIError
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ErrorResponse(val value: KClass<out APIError>)