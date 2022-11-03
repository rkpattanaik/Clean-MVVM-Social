package com.rkpattanaik.social.domain.usecase.post

import app.cash.turbine.test
import com.rkpattanaik.social.core.UseCase
import com.rkpattanaik.social.domain.entity.PostEntity
import com.rkpattanaik.social.domain.repository.PostRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetAllPostsUseCaseTest {
    private lateinit var getAllPostsUseCase: GetAllPostsUseCase
    private val repository: PostRepository = mock()

    @Before
    fun setup() {
        getAllPostsUseCase = GetAllPostsUseCase(repository)
    }

    @Test
    fun testGetPostsSuccess() = runTest {
        val posts = listOf(
            PostEntity(id = 1),
            PostEntity(id = 2)
        )
        whenever(repository.getAllPosts()).thenReturn(
            flowOf(Result.success(posts))
        )

        val resultFlow = getAllPostsUseCase.invoke(UseCase.NoParams())

        resultFlow.test {
            with(awaitItem()) {
                assertTrue(isSuccess)
                assertNotNull(getOrNull())
                getOrNull()?.let {
                    it.forEachIndexed { index, postEntity ->
                        assertEquals(posts[index], postEntity)
                    }
                }
            }
            awaitComplete()
        }
    }

    @Test
    fun testGetPostsFailure() = runTest {
        whenever(repository.getAllPosts()).thenReturn(
            flowOf(Result.failure(Throwable("Fail")))
        )

        getAllPostsUseCase(UseCase.NoParams())
            .test {
                with(awaitItem()) {
                    assertTrue(isFailure)
                    assertNotNull(exceptionOrNull())
                    assertEquals("Fail", exceptionOrNull()?.message)
                }
                awaitComplete()
            }
    }
}