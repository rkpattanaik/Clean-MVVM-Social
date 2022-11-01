package com.rkpattanaik.social.data.repository

import com.rkpattanaik.social.data.network.service.DummyJsonApi
import com.rkpattanaik.social.domain.entity.PostEntity
import com.rkpattanaik.social.domain.repository.PostRepository
import com.rkpattanaik.social.domain.usecase.post.CreatePostParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val dummyJsonApi: DummyJsonApi
): PostRepository {
    override fun createPost(params: CreatePostParams): Flow<Result<PostEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllPosts(): Flow<Result<List<PostEntity>>> = dummyJsonApi.getPosts()
        .map { result -> result.map { it.posts } }
}