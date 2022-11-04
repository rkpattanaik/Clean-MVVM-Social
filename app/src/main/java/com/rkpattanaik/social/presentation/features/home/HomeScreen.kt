package com.rkpattanaik.social.presentation.features.home.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rkpattanaik.social.presentation.features.home.HomeViewModel
import com.rkpattanaik.social.presentation.features.home.components.PostsSection
import com.rkpattanaik.social.presentation.features.home.components.UsersSection
import com.rkpattanaik.social.presentation.ui.common.SocialAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val postListState = viewModel.postListState.value
    val userListState = viewModel.userListState.value

    Scaffold(
        topBar = { SocialAppBar(navController = navController, title = "Home", showBackIcon = false) },
        content = {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(12.dp, 0.dp)
            ) {
                UsersSection(state = userListState)
                Spacer(modifier = Modifier.size(12.dp))
                PostsSection(state = postListState)
            }
        }
    )
}