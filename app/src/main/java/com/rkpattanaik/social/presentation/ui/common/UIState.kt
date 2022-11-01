package com.rkpattanaik.social.presentation.ui.common

data class UIState<T: Any>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: String = ""
)
