package com.rkpattanaik.social.presentation.features.home

import com.rkpattanaik.social.data.network.model.error.DummyJsonApiError
import com.rkpattanaik.social.domain.entity.PostEntity
import com.rkpattanaik.social.domain.entity.UserEntity
import com.rkpattanaik.social.domain.repository.PostRepository
import com.rkpattanaik.social.domain.repository.UserRepository
import com.rkpattanaik.social.domain.usecase.post.GetAllPostsUseCase
import com.rkpattanaik.social.domain.usecase.user.GetAllUsersUseCase
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
    fun testLoadingStateOnInit() = runTest {
        whenever(postRepository.getAllPosts()).thenReturn(
            flow {
                delay(1000)
                emit(Result.success(emptyList()))
            }
        )

        whenever(userRepository.getAllUsers()).thenReturn(
            flow {
                delay(1000)
                emit(Result.success(emptyList()))
            }
        )

        viewModel = HomeViewModel(getAllPostsUseCase, getAllUsersUseCase)

        assertTrue(viewModel.postListState.value.isLoading)
        assertTrue(viewModel.userListState.value.isLoading)
    }

    @Test
    fun testGetPostsSuccess() = runTest {
        val post1 = PostEntity(id = 1)
        val post2 = PostEntity(id = 2)

        whenever(postRepository.getAllPosts()).thenReturn(
            flowOf(Result.success(listOf(post1, post2)))
        )
        whenever(userRepository.getAllUsers()).thenReturn(
            flowOf(Result.success(emptyList()))
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
    fun testGetPostsFailureWithDummyJsonApiErrorResponse() = runTest {
        whenever(postRepository.getAllPosts()).thenReturn(
            flowOf(Result.failure(DummyJsonApiError("Fail")))
        )
        whenever(userRepository.getAllUsers()).thenReturn(
            flowOf(Result.success(emptyList()))
        )

        viewModel = HomeViewModel(getAllPostsUseCase, getAllUsersUseCase)

        with(viewModel.postListState.value.error) {
            assertTrue(isNotEmpty())
            assertEquals("Fail", this)
        }
    }

    @Test
    fun testGetPostsFailureWithThrowable() = runTest {
        whenever(postRepository.getAllPosts()).thenReturn(
            flowOf(Result.failure(Throwable("Fail")))
        )
        whenever(userRepository.getAllUsers()).thenReturn(
            flowOf(Result.success(emptyList()))
        )

        viewModel = HomeViewModel(getAllPostsUseCase, getAllUsersUseCase)

        with(viewModel.postListState.value.error) {
            assertTrue(isNotEmpty())
            assertEquals("Fail", this)
        }
    }

    @Test
    fun testGetUsersSuccess() = runTest {
        val users = listOf(
            UserEntity(id = 1),
            UserEntity(id = 2)
        )

        whenever(userRepository.getAllUsers()).thenReturn(
            flowOf(Result.success(users))
        )
        whenever(postRepository.getAllPosts()).thenReturn(
            flowOf(Result.success(emptyList()))
        )

        viewModel = HomeViewModel(getAllPostsUseCase, getAllUsersUseCase)

        val data = viewModel.userListState.value.data

        assertNotNull(data)
        data?.let {
            assertEquals(users[0], it[0])
            assertEquals(users[1], it[1])
        }
    }

    @Test
    fun testGetUsersFailure() = runTest {
        whenever(userRepository.getAllUsers()).thenReturn(
            flowOf(Result.failure(Throwable("Fail")))
        )
        whenever(postRepository.getAllPosts()).thenReturn(
            flowOf(Result.success(emptyList()))
        )

        viewModel = HomeViewModel(getAllPostsUseCase, getAllUsersUseCase)

        with(viewModel.userListState.value.error) {
            assertTrue(isNotEmpty())
            assertEquals("Fail", this)
        }
    }
}