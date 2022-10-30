package com.rkpattanaik.social.domain.usecase.user

import com.google.gson.annotations.SerializedName
import com.rkpattanaik.social.domain.repository.UserRepository
import com.rkpattanaik.social.core.UseCase
import kotlinx.coroutines.flow.Flow

class SignupUseCase(
    private val repository: UserRepository
): UseCase<SignupParams, Boolean> {
    override fun invoke(params: SignupParams): Flow<Result<Boolean>> {
        return repository.signup(params)
    }
}

data class SignupParams(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)