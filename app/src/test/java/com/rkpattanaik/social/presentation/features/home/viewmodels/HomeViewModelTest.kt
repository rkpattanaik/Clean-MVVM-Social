package com.rkpattanaik.social.presentation.features.home.viewmodels

import com.rkpattanaik.social.data.network.model.error.DummyJsonApiError
import com.rkpattanaik.social.domain.entity.PostEntity
import com.rkpattanaik.social.domain.repository.PostRepository
import com.rkpattanaik.social.domain.repository.UserRepository
import com.rkpattanaik.social.domain.usecase.post.GetAllPostsUseCase
import com.rkpattanaik.social.domain.usecase.user.GetAllUsersUseCase
import com.rkpattanaik.social.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HomeViewModel

    private val userRepository: UserRepository = mock()
    private val postRepository: PostRepository = mock()
    private lateinit var getAllUsersUseCase: GetAllUsersUseCase
    private lateinit var getAllPostsUseCase: GetAllPostsUseCase

    @Before
    fun setup() {
        getAllUsersUseCase = GetAllUsersUseCase(userRepository)
        getAllPostsUseCase = GetAllPostsUseCase(postRepository)
    }

    @Test
    fun testLoadingStateOnInit() {
        whenever(postRepository.getAllPosts()).thenReturn(
            flow {
                delay(1000)
                emit(Result.success(emptyList()))
            }
        )

        viewModel = HomeViewModel(getAllPostsUseCase, getAllUsersUseCase)

        assertTrue(viewModel.postListState.value.isLoading)
    }

    @Test
    fun testGetPostsSuccess() {
        val post1 = PostEntity(id = 1)
        val post2 = PostEntity(id = 2)

        whenever(postRepository.getAllPosts()).thenReturn(
            flow { emit(Result.success(listOf(post1, post2))) }
        )

        viewModel = HomeViewModel(getAllPostsUseCase, getAllUsersUseCase)

        val data = viewModel.postListState.value.data

        assertNotNull(data)
        data?.let {
            assertEquals(post1, it[0])
            assertEquals(post2, it[1])
        }
    }

    @Test
    fun testGetPostsFailureWithDummyJsonApiErrorResponse() {
        whenever(postRepository.getAllPosts()).thenReturn(
            flow { emit(Result.failure(DummyJsonApiError("Fail"))) }
        )

        viewModel = HomeViewModel(getAllPostsUseCase, getAllUsersUseCase)

        with(viewModel.postListState.value.error) {
            assertTrue(isNotEmpty())
            assertEquals("Fail", this)
        }
    }

    @Test
    fun testGetPostsFailureWithThrowable() {
        whenever(postRepository.getAllPosts()).thenReturn(
            flow { emit(Result.failure(Throwable("Fail"))) }
        )

        viewModel = HomeViewModel(getAllPostsUseCase, getAllUsersUseCase)

        with(viewModel.postListState.value.error) {
            assertTrue(isNotEmpty())
            assertEquals("Fail", this)
        }
    }
}