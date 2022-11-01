package com.rkpattanaik.social.data.network.model.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token")
    val token: String = ""
)