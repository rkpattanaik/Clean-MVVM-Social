package com.rkpattanaik.social.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rkpattanaik.social.presentation.features.home.screens.HomeScreen
import com.rkpattanaik.social.presentation.features.login.LoginScreen

@Composable
fun NavGraphHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {
        composable(Routes.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(Routes.Home.route) {
            HomeScreen(navController = navController)
        }
    }
}