package com.rkpattanaik.social.data.network.service

import com.rkpattanaik.social.core.retrofit.annotation.Async
import com.rkpattanaik.social.core.retrofit.annotation.ErrorResponse
import com.rkpattanaik.social.data.network.model.response.GetUsersResponse
import com.rkpattanaik.social.data.network.model.response.LoginError
import com.rkpattanaik.social.data.network.model.response.LoginResponse
import com.rkpattanaik.social.domain.usecase.user.LoginParams
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ReqResApi {
    @GET("users")
    fun getUsers(): Flow<Result<GetUsersResponse>>

    @POST("login")
    @ErrorResponse(LoginError::class)
    @Async(false)
    fun login(@Body body: LoginParams): Flow<Result<LoginResponse>>
}