package com.rkpattanaik.social.presentation.navigation

sealed class Routes(val route: String) {
    object Login: Routes("login")
    object Home: Routes("home")
}
