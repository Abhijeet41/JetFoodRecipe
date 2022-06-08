package com.abhi41.jetfoodrecipeapp


import androidx.annotation.DrawableRes

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int
){
    object Recipes: BottomBarScreen(
        route = "recipes",
        title = "Recipes",
        icon = R.drawable.ic_recipes
    )
    object Favorite: BottomBarScreen(
        route = "favorites",
        title = "Favorites",
        icon = R.drawable.ic_favorite
    )

    object FoodJoke: BottomBarScreen(
        route = "joke",
        title = "Jokes",
        icon = R.drawable.ic_joke
    )

}
