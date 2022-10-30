package com.rkpattanaik.social.domain.repository

import com.rkpattanaik.social.domain.entity.UserEntity
import com.rkpattanaik.social.domain.usecase.user.LoginParams
import com.rkpattanaik.social.domain.usecase.user.SignupParams
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun login(params: LoginParams): Flow<Result<Boolean>>
    fun signup(params: SignupParams): Flow<Result<Boolean>>
    fun getAllUsers(): Flow<Result<List<UserEntity>>>
    fun getUser(id: Int): Flow<Result<UserEntity>>
}