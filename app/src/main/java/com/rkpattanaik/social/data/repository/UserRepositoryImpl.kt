package com.rkpattanaik.social.data.repository

import com.rkpattanaik.social.data.network.service.ReqResApi
import com.rkpattanaik.social.domain.entity.UserEntity
import com.rkpattanaik.social.domain.repository.UserRepository
import com.rkpattanaik.social.domain.usecase.user.LoginParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val reqResApi: ReqResApi
): UserRepository {
    override fun login(params: LoginParams): Flow<Result<Boolean>> = reqResApi.login(params)
        .map { result -> result.map { it.token.isNotEmpty() } }

    override fun getAllUsers(): Flow<Result<List<UserEntity>>> = reqResApi.getUsers()
        .map { result -> result.map { it.data } }
}