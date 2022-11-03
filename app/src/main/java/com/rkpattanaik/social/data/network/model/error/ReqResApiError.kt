package com.rkpattanaik.social.data.network.model.error


import com.google.gson.annotations.SerializedName
import com.rkpattanaik.social.core.retrofit.error.APIError

data class ReqResApiError(
    @SerializedName("error")
    val error: String = ""
): APIError()