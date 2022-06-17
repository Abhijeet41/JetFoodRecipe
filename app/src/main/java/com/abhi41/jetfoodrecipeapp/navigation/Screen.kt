package com.abhi41.jetfoodrecipeapp.navigation

sealed class Screen(val route: String) {

    object Splash : Screen("splash_screen")
    object DetailPage : Screen("detail_screen/{recipeId}") {//we use required arguments
        fun passRecipeId(
            recipeId: Int
        ):String {
            return "detail_screen/$recipeId"
        }
    }

    object SearchPage : Screen("search_screen")
}
