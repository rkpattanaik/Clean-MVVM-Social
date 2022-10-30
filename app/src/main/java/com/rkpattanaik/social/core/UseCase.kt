package com.rkpattanaik.social.core

import kotlinx.coroutines.flow.Flow

interface UseCase<in Params: Any, out Type: Any> {
    operator fun invoke(params: Params): Flow<Result<Type>>

    class NoParams
}