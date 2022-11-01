package com.rkpattanaik.social.data.network.model.error


import com.google.gson.annotations.SerializedName
import com.rkpattanaik.social.core.adapter.APIError

data class ReqResApiError(
    @SerializedName("error")
    val error: String = ""
): APIError {
    override fun message(): String = error
}