package com.rkpattanaik.social.data.network.service

import com.rkpattanaik.social.data.network.model.response.GetPostsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface DummyJsonApi {
    @GET("posts")
    fun getPosts(): Flow<Result<GetPostsResponse>>
}