package com.rkpattanaik.social.domain.repository

import com.rkpattanaik.social.domain.entity.PostEntity
import com.rkpattanaik.social.domain.usecase.post.CreatePostParams
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun createPost(params: CreatePostParams): Flow<Result<PostEntity>>
    fun deletePost(id: Int): Flow<Result<Boolean>>
    fun getAllPosts(): Flow<Result<List<PostEntity>>>
    fun getPost(id: Int): Flow<Result<PostEntity>>
}