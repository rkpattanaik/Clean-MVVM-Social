package com.rkpattanaik.social.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.rkpattanaik.social.core.retrofit.error.APIError

data class LoginResponse(
    @SerializedName("token")
    val token: String = ""
)

data class LoginError(
    @SerializedName("error")
    val error: String = ""
): APIError()