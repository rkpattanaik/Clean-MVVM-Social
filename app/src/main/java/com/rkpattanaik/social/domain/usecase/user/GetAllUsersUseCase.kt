package com.rkpattanaik.social.domain.usecase.user

import com.rkpattanaik.social.domain.repository.UserRepository
import com.rkpattanaik.social.core.UseCase
import com.rkpattanaik.social.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val repository: UserRepository
): UseCase<UseCase.NoParams, List<UserEntity>> {
    override fun invoke(params: UseCase.NoParams): Flow<Result<List<UserEntity>>> {
        return repository.getAllUsers()
    }
}