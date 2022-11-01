package com.rkpattanaik.social.domain.usecase.post

import com.rkpattanaik.social.core.UseCase
import com.rkpattanaik.social.domain.entity.PostEntity
import com.rkpattanaik.social.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val repository: PostRepository
): UseCase<CreatePostParams, PostEntity> {
    override fun invoke(params: CreatePostParams): Flow<Result<PostEntity>> {
        return repository.createPost(params)
    }
}

data class CreatePostParams(
    val title: String,
    val body: String
)