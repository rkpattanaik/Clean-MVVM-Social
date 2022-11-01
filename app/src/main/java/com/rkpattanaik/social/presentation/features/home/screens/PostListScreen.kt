package com.rkpattanaik.social.presentation.features.home.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rkpattanaik.social.domain.entity.PostEntity
import com.rkpattanaik.social.presentation.features.home.components.PostListItem
import com.rkpattanaik.social.presentation.features.home.viewmodels.HomeViewModel
import com.rkpattanaik.social.presentation.ui.common.SocialAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PostListScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.postListState.value

    Scaffold(
        topBar = { SocialAppBar(navController = navController, title = "Home", showBackIcon = false) },
        content = {
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
                            .padding(12.dp, 0.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.size(2.dp))
                        }

                        state.data?.let {
                            items(it) { item: PostEntity ->
                                PostListItem(item)
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.size(2.dp))
                        }
                    }
                }
            }
        }
    )
}