package com.rkpattanaik.social.data.network

import com.rkpattanaik.social.data.network.model.GetUsersResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ReqResApi {
    @GET("users")
    fun getUsers(): Flow<Result<GetUsersResponse>>
}