package com.rkpattanaik.social.data.repository

import app.cash.turbine.test
import com.rkpattanaik.social.data.network.model.response.LoginError
import com.rkpattanaik.social.data.network.model.response.LoginResponse
import com.rkpattanaik.social.data.network.service.ReqResApi
import com.rkpattanaik.social.domain.usecase.user.LoginParams
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class UserRepositoryImplTest {
    private lateinit var userRepositoryImpl: UserRepositoryImpl

    private val reqResApi: ReqResApi = mock()

    @Before
    fun setup() {
        userRepositoryImpl = UserRepositoryImpl(reqResApi)
    }

    @Test
    fun testLoginSuccessWhenTokenAvailable() = runTest {
        whenever(reqResApi.login(any())).thenReturn(
            flow { emit(Result.success(LoginResponse(token = "xyz"))) }
        )

        val resultFlow = userRepositoryImpl.login(LoginParams("", ""))

        resultFlow.test {
            with(awaitItem()) {
                assertTrue(isSuccess)
                assertEquals(true, getOrNull())
            }
            awaitComplete()
        }
    }

    @Test
    fun testLoginSuccessWhenTokenIsEmpty() = runTest {
        whenever(reqResApi.login(any())).thenReturn(
            flow { emit(Result.success(LoginResponse())) }
        )

        val resultFlow = userRepositoryImpl.login(LoginParams("", ""))

        resultFlow.test {
            with(awaitItem()) {
                assertTrue(isSuccess)
                assertEquals(false, getOrNull())
            }
            awaitComplete()
        }
    }

    @Test
    fun testLoginFailureWhenLoginErrorThrowable() = runTest {
        whenever(reqResApi.login(any())).thenReturn(
            flow { emit(Result.failure(LoginError(error = "Fail"))) }
        )

        val resultFlow = userRepositoryImpl.login(LoginParams("", ""))

        resultFlow.test {
            with(awaitItem()) {
                assertTrue(isFailure)
                assertNotNull(exceptionOrNull())
                exceptionOrNull()?.let {
                    assertTrue(it is LoginError)
                    assertEquals("Fail", (it as LoginError).error)
                }
            }
            awaitComplete()
        }
    }

    @Test
    fun testLoginFailureWhenThrowable() = runTest {
        whenever(reqResApi.login(any())).thenReturn(
            flow { emit(Result.failure(Throwable("Fail"))) }
        )

        val resultFlow = userRepositoryImpl.login(LoginParams("", ""))

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