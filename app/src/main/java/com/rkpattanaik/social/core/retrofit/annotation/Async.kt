package com.rkpattanaik.social.core.retrofit.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Async(val value: Boolean)