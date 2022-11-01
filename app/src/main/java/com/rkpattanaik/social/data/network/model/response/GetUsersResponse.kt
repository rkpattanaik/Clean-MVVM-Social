package com.rkpattanaik.social.data.network.model.response


import com.google.gson.annotations.SerializedName
import com.rkpattanaik.social.domain.entity.UserEntity

data class GetUsersResponse(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("per_page")
    val perPage: Int = 0,
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("data")
    val data: List<UserEntity> = listOf(),
    @SerializedName("support")
    val support: Support = Support()
) {
    data class Support(
        @SerializedName("url")
        val url: String = "",
        @SerializedName("text")
        val text: String = ""
    )
}