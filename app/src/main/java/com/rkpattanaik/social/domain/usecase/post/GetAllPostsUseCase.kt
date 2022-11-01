package com.rkpattanaik.social.domain.usecase.post

import com.rkpattanaik.social.core.UseCase
import com.rkpattanaik.social.domain.entity.PostEntity
import com.rkpattanaik.social.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPostsUseCase @Inject constructor(
    private val repository: PostRepository
): UseCase<UseCase.NoParams, List<PostEntity>> {
    override fun invoke(params: UseCase.NoParams): Flow<Result<List<PostEntity>>> {
        return repository.getAllPosts()
    }
}