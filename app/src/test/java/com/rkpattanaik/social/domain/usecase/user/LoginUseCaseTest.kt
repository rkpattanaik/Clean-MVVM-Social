package com.rkpattanaik.social.domain.usecase.user

import app.cash.turbine.test
import com.rkpattanaik.social.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginUseCaseTest {
    private lateinit var loginUseCase: LoginUseCase
    private val repository: UserRepository = mock()

    @Before
    fun setup() {
        loginUseCase = LoginUseCase(repository)
    }

    @Test
    fun testLoginSuccess() = runTest {
        whenever(repository.login(any())).thenReturn(
            flow { emit(Result.success(true)) }
        )

        val resultFlow = loginUseCase.invoke(LoginParams("", ""))

        val result = resultFlow.first()

        with(result) {
            assertTrue(isSuccess)
            assertNotNull(getOrNull())
            assertEquals(true, getOrNull())
        }
    }

    @Test
    fun testLoginSuccessWithTurbine() = runTest {
        whenever(repository.login(any())).thenReturn(
            flow { emit(Result.success(true)) }
        )

        val resultFlow = loginUseCase.invoke(LoginParams("", ""))

        resultFlow.test {
            with(awaitItem()) {
                assertTrue(isSuccess)
                assertNotNull(getOrNull())
                assertEquals(true, getOrNull())
            }
            awaitComplete()
        }
    }

    @Test
    fun testLoginErrorWithTurbine() = runTest {
        whenever(repository.login(any())).thenReturn(
            flow { emit(Result.failure(Throwable("Fail"))) }
        )

        val resultFlow = loginUseCase.invoke(LoginParams("", ""))

        resultFlow.test {
            with(awaitItem()) {
                assertTrue(isFailure)
                assertNotNull(exceptionOrNull())
                assertEquals("Fail", exceptionOrNull()?.message)
            }
            awaitComplete()
        }
    }
}