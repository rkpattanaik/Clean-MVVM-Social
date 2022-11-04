package com.rkpattanaik.social.domain.usecase.user

import app.cash.turbine.test
import com.rkpattanaik.social.core.UseCase
import com.rkpattanaik.social.domain.entity.UserEntity
import com.rkpattanaik.social.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetAllUsersUseCaseTest {
    private lateinit var getAllUsersUseCase: GetAllUsersUseCase
    private val repository: UserRepository = mock()

    @Before
    fun setup() {
        getAllUsersUseCase = GetAllUsersUseCase(repository)
    }

    @Test
    fun testGetAllUsersSuccess() = runTest {
        val users = listOf(
            UserEntity(id = 1),
            UserEntity(id = 2)
        )
        whenever(repository.getAllUsers()).thenReturn(
            flowOf(Result.success(users))
        )

        getAllUsersUseCase(UseCase.NoParams()).test {
            with(awaitItem()) {
                assertTrue(isSuccess)
                assertNotNull(getOrNull())
                getOrNull()?.let {
                    assertEquals(users[0], it[0])
                    assertEquals(users[1], it[1])
                }
            }
            awaitComplete()
        }
    }

    @Test
    fun testGetAllUsersFailure() = runTest {
        whenever(repository.getAllUsers()).thenReturn(
            flowOf(Result.failure(Throwable("Fail")))
        )

        getAllUsersUseCase(UseCase.NoParams()).test {
            with(awaitItem()) {
                assertTrue(isFailure)
                assertNotNull(exceptionOrNull())
                assertEquals("Fail", exceptionOrNull()?.message)
            }
            awaitComplete()
        }
    }
}