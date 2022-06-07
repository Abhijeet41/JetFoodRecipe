package com.abhi41.jetfoodrecipeapp.navigation

sealed class Screen(val route: String) {

    object Splash : Screen("splash_screen")
    object Recipes: Screen("recipes_screen")
    object FavoriteRecipes: Screen("recipes_screen")
    object FoodJoke: Screen("food_joke _screen")
    object DetailPage: Screen("detail_screen")
}
