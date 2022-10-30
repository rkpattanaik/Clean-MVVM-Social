package com.rkpattanaik.social.domain.usecase.post

import com.rkpattanaik.social.core.UseCase
import com.rkpattanaik.social.domain.entity.PostEntity
import com.rkpattanaik.social.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostUseCase(
    private val repository: PostRepository
): UseCase<Int, PostEntity> {
    override fun invoke(params: Int): Flow<Result<PostEntity>> {
        return repository.getPost(params)
    }
}