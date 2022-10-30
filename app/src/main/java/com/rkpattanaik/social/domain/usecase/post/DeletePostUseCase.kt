package com.rkpattanaik.social.domain.usecase.post

import com.rkpattanaik.social.core.UseCase
import com.rkpattanaik.social.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class DeletePostUseCase(
    private val repository: PostRepository
): UseCase<Int, Boolean> {
    override fun invoke(params: Int): Flow<Result<Boolean>> {
        return repository.deletePost(params)
    }
}