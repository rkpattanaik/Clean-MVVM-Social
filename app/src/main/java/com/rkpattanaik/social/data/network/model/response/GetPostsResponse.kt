package com.rkpattanaik.social.data.network.model.response


import com.google.gson.annotations.SerializedName
import com.rkpattanaik.social.domain.entity.PostEntity

data class GetPostsResponse(
    @SerializedName("posts")
    val posts: List<PostEntity> = listOf(),
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("skip")
    val skip: Int = 0,
    @SerializedName("limit")
    val limit: Int = 0
)