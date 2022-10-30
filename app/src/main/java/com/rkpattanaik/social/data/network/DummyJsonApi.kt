package com.rkpattanaik.social.data.network

import com.rkpattanaik.social.data.network.model.GetPostsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface DummyJsonApi {
    @GET("posts")
    fun getPosts(): Flow<Result<GetPostsResponse>>
}