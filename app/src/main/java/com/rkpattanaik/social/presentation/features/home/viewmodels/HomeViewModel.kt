package com.rkpattanaik.social.presentation.features.home.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkpattanaik.social.core.UseCase
import com.rkpattanaik.social.domain.entity.PostEntity
import com.rkpattanaik.social.domain.entity.UserEntity
import com.rkpattanaik.social.domain.usecase.post.GetAllPostsUseCase
import com.rkpattanaik.social.domain.usecase.user.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllPostsUseCase: GetAllPostsUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase
): ViewModel() {
    private val _postListState = mutableStateOf(PostListState())
    private val _userListState = mutableStateOf(UserListState())
    val postListState: State<PostListState> = _postListState
    val userListState: State<UserListState> = _userListState

    init {
        getPosts()
    }

    private fun getPosts() {
        _postListState.value = PostListState(isLoading = true)

        getAllPostsUseCase(UseCase.NoParams()).onEach {
            it.onSuccess { posts ->
                _postListState.value = PostListState(posts = posts)
            }.onFailure { err ->
                _postListState.value = PostListState(error = err.localizedMessage ?: "Something went wrong")
            }
        }.launchIn(viewModelScope)
    }

    private fun getUsers() {
        _userListState.value = UserListState(isLoading = true)
        getAllUsersUseCase(UseCase.NoParams()).onEach {
            it.onSuccess { users ->
                _userListState.value = UserListState(users = users)
            }.onFailure { err ->
                _userListState.value = UserListState(error = err.localizedMessage ?: "Something went wrong")
            }
        }.launchIn(viewModelScope)
    }
}

data class PostListState(
    val isLoading: Boolean = false,
    val posts: List<PostEntity> = emptyList(),
    val error: String = ""
)

data class UserListState(
    val isLoading: Boolean = false,
    val users: List<UserEntity> = emptyList(),
    val error: String = ""
)