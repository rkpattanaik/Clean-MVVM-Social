package com.rkpattanaik.social.domain.usecase.user

import com.google.gson.annotations.SerializedName
import com.rkpattanaik.social.domain.repository.UserRepository
import com.rkpattanaik.social.core.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: UserRepository
): UseCase<LoginParams, Boolean> {
    override fun invoke(params: LoginParams): Flow<Result<Boolean>> {
        return repository.login(params)
    }
}

data class LoginParams(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)