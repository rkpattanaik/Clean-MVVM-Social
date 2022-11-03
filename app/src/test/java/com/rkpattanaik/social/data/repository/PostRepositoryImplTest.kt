package com.rkpattanaik.social.data.repository

import app.cash.turbine.test
import com.rkpattanaik.social.data.network.model.response.GetPostsResponse
import com.rkpattanaik.social.data.network.service.DummyJsonApi
import com.rkpattanaik.social.domain.entity.PostEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PostRepositoryImplTest {
    private lateinit var postRepositoryImpl: PostRepositoryImpl
    private val dummyJsonApi: DummyJsonApi = mock()

    @Before
    fun setup() {
        postRepositoryImpl = PostRepositoryImpl(dummyJsonApi)
    }

    @Test
    fun testGetPostsSuccess() = runTest {
        val post1 = PostEntity(id = 1)
        val post2 = PostEntity(id = 2)
        whenever(dummyJsonApi.getPosts()).thenReturn(
            flow { emit(Result.success(GetPostsResponse(posts = listOf(post1, post2)))) }
        )

        val resultFlow = postRepositoryImpl.getAllPosts()

        resultFlow.test {
            with(awaitItem()) {
                assertTrue(isSuccess)
                assertNotNull(getOrNull())
                getOrNull()?.let {
                    assertEquals(post1, it[0])
                    assertEquals(post2, it[1])
                }
            }
            awaitComplete()
        }
    }

    @Test
    fun testGetPostsFailure() = runTest {
        whenever(dummyJsonApi.getPosts()).thenReturn(
            flowOf(Result.failure(Throwable("Fail")))
        )

        val resultFlow = postRepositoryImpl.getAllPosts()

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