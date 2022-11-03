package com.rkpattanaik.social.presentation.features.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkpattanaik.social.data.network.model.response.LoginError
import com.rkpattanaik.social.domain.usecase.user.LoginParams
import com.rkpattanaik.social.domain.usecase.user.LoginUseCase
import com.rkpattanaik.social.presentation.ui.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    private val _loginState = mutableStateOf(UIState<Boolean>())
    val loginState: State<UIState<Boolean>> = _loginState

    fun login(
        email: String,
        password: String,
        onLoginSuccess: () -> Unit = {}
    ) {
        _loginState.value = UIState(isLoading = true)

        loginUseCase(LoginParams(email, password)).onEach { result ->
            result.onSuccess { isSuccess ->
                if (isSuccess) {
                    _loginState.value = UIState(data = true)
                    onLoginSuccess()
                }
                else _loginState.value = UIState(error = "Login Failed")
            }.onFailure { err ->
                val message = if (err is LoginError) {
                    println("Login Error Code: ${err.code}")
                    println("Raw Login Error: ${err.rawError}")
                    err.error
                } else {
                    err.message ?: "Something went wrong!"
                }
                println("Login Error: $message")
                _loginState.value = UIState(error = message)
            }
        }.launchIn(viewModelScope)
    }
}