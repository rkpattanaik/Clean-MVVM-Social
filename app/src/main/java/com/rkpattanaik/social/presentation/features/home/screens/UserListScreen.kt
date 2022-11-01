package com.rkpattanaik.social.presentation.features.home.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rkpattanaik.social.domain.entity.UserEntity
import com.rkpattanaik.social.presentation.features.home.components.UserListItem
import com.rkpattanaik.social.presentation.features.home.viewmodels.HomeViewModel

@Composable
fun UserListScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.userListState.value
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if(state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            if(state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                state.data?.let {
                    items(it) { item: UserEntity ->
                        UserListItem(item)
                    }
                }
            }
        }
    }
}