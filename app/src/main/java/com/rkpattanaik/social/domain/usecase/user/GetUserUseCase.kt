package com.rkpattanaik.social.domain.usecase.user

import com.rkpattanaik.social.domain.repository.UserRepository
import com.rkpattanaik.social.core.UseCase
import com.rkpattanaik.social.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class GetUserUseCase(
    private val repository: UserRepository
): UseCase<Int, UserEntity> {
    override fun invoke(params: Int): Flow<Result<UserEntity>> {
        return repository.getUser(params)
    }
}