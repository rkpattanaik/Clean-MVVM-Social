package com.rkpattanaik.social.di

import com.rkpattanaik.social.data.repository.PostRepositoryImpl
import com.rkpattanaik.social.data.repository.UserRepositoryImpl
import com.rkpattanaik.social.domain.repository.PostRepository
import com.rkpattanaik.social.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindPostRepository(postRepository: PostRepositoryImpl): PostRepository

    @Singleton
    @Binds
    abstract fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository
}