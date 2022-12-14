package com.rkpattanaik.social.core.retrofit.error

open class APIError(
    var code: Int = 0,
    var rawError: String? = null,
    override var message: String? = null
): Throwable()