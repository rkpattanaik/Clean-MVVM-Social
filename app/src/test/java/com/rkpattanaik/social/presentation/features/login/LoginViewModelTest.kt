package com.rkpattanaik.social.presentation.features.login

import com.rkpattanaik.social.data.network.model.response.LoginError
import com.rkpattanaik.social.domain.repository.UserRepository
import com.rkpattanaik.social.domain.usecase.user.LoginUseCase
import com.rkpattanaik.social.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
class LoginViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: LoginViewModel

    private val repository: UserRepository = mock()
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setup() {
        loginUseCase = LoginUseCase(repository)
        viewModel = LoginViewModel(loginUseCase)
    }

    @Test
    fun testLoadingState() = runTest {
        whenever(repository.login(any())).thenReturn(
            flow {
                delay(1000)
                emit(Result.success(true))
            }
        )

        viewModel.login("", "") {}

        assertTrue(viewModel.loginState.value.isLoading)
    }

    @Test
    fun testLoginSuccess() = runTest {
        whenever(repository.login(any())).thenReturn(
            flowOf(Result.success(true))
        )

        val callback: () -> Unit = mock()
        viewModel.login("", "", callback)

        assertNotNull(viewModel.loginState.value.data)
        viewModel.loginState.value.data?.let { assertTrue(it) }
        verify(callback).invoke()
    }

    @Test
    fun testLoginFailureWithLoginErrorResponse() = runTest {
        whenever(repository.login(any())).thenReturn(
            flowOf(Result.failure(LoginError(error = "Fail")))
        )

        val callback: () -> Unit = mock()
        viewModel.login("", "", callback)

        with(viewModel.loginState.value.error) {
            assertTrue(isNotEmpty())
            assertEquals("Fail", this)
        }
        verify(callback, never()).invoke()
    }

    @Test
    fun testLoginFailureWithThrowable() = runTest {
        whenever(repository.login(any())).thenReturn(
            flowOf(Result.failure(Throwable("Fail")))
        )

        val callback: () -> Unit = mock()
        viewModel.login("", "", callback)

        with(viewModel.loginState.value.error) {
            assertTrue(isNotEmpty())
            assertEquals("Fail", this)
        }
        verify(callback, never()).invoke()
    }
}