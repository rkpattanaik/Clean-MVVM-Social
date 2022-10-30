package com.rkpattanaik.social.domain.entity


import com.google.gson.annotations.SerializedName

data class PostEntity(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("body")
    val body: String = "",
    @SerializedName("userId")
    val userId: Int = 0,
    @SerializedName("tags")
    val tags: List<String> = listOf(),
    @SerializedName("reactions")
    val reactions: Int = 0
)