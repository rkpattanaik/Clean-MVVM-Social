package com.rkpattanaik.social.domain.repository

import com.rkpattanaik.social.domain.entity.PostEntity
import com.rkpattanaik.social.domain.usecase.post.CreatePostParams
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun createPost(params: CreatePostParams): Flow<Result<PostEntity>>
    fun getAllPosts(): Flow<Result<List<PostEntity>>>
}