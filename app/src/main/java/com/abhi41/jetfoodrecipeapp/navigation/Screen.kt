package com.abhi41.jetfoodrecipeapp.navigation

sealed class Screen(val route: String) {

    object Splash : Screen("splash_screen")
    object DetailPage: Screen("detail_screen")
    object SearchPage: Screen("search_screen")
}
