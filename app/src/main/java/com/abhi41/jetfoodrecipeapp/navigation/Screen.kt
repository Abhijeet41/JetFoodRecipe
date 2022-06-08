package com.abhi41.jetfoodrecipeapp.navigation

sealed class Screen(val route: String) {

    object Splash : Screen("splash_screen")
    object DashBoard: Screen("dashBoard_screen")
    object DetailPage: Screen("detail_screen")
}
