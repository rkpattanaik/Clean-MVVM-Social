package com.rkpattanaik.social.presentation.features.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkpattanaik.social.core.UseCase
import com.rkpattanaik.social.data.network.model.error.DummyJsonApiError
import com.rkpattanaik.social.domain.entity.PostEntity
import com.rkpattanaik.social.domain.entity.UserEntity
import com.rkpattanaik.social.domain.usecase.post.GetAllPostsUseCase
import com.rkpattanaik.social.domain.usecase.user.GetAllUsersUseCase
import com.rkpattanaik.social.presentation.ui.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllPostsUseCase: GetAllPostsUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase
): ViewModel() {
    private val _postListState = mutableStateOf(UIState<List<PostEntity>>())
    private val _userListState = mutableStateOf(UIState<List<UserEntity>>())
    val postListState: State<UIState<List<PostEntity>>> = _postListState
    val userListState: State<UIState<List<UserEntity>>> = _userListState

    init {
        getUsers()
        getPosts()
    }

    private fun getUsers() {
        _userListState.value = UIState(isLoading = true)
        getAllUsersUseCase(UseCase.NoParams()).onEach { result ->
            println("Thread[Users Flow Collect] ${Thread.currentThread().name}")
            result.onSuccess { users ->
                _userListState.value = UIState(data = users)
            }.onFailure { err ->
                _userListState.value = UIState(error = err.message ?: "Something went wrong")
            }
        }.launchIn(viewModelScope)
    }

    private fun getPosts() {
        _postListState.value = UIState(isLoading = true)

        getAllPostsUseCase(UseCase.NoParams()).onEach { result ->
            println("Thread[Posts Flow Collect] ${Thread.currentThread().name}")
            result.onSuccess { posts ->
                _postListState.value = UIState(data = posts)
            }.onFailure { err ->
                val message = if (err is DummyJsonApiError) {
                    println(err.rawError)
                    err.message
                } else {
                    err.message
                }
                _postListState.value = UIState(error = message ?: "Something went wrong")
            }
        }.launchIn(viewModelScope)
    }
}