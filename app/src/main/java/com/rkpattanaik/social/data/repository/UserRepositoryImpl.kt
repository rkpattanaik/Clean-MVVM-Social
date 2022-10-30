package com.rkpattanaik.social.data.repository

import com.rkpattanaik.social.data.network.ReqResApi
import com.rkpattanaik.social.domain.entity.UserEntity
import com.rkpattanaik.social.domain.repository.UserRepository
import com.rkpattanaik.social.domain.usecase.user.LoginParams
import com.rkpattanaik.social.domain.usecase.user.SignupParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val reqResApi: ReqResApi
): UserRepository {
    override fun login(params: LoginParams): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun signup(params: SignupParams): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun getAllUsers(): Flow<Result<List<UserEntity>>> = reqResApi.getUsers()
        .map { result -> result.map { it.data } }

    override fun getUser(id: Int): Flow<Result<UserEntity>> {
        TODO("Not yet implemented")
    }
}