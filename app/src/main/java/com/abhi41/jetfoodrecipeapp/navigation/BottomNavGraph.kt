package com.abhi41.jetfoodrecipeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.abhi41.jetfoodrecipeapp.BottomBarScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.favoriteScreen.FavoriteScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.foodJoke.FoodJokeScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.recipesScreen.RecipesScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.searchScreen.SearchScreen
import com.abhi41.jetfoodrecipeapp.utils.Constants.BOTTOM_NAVIGATION_ROUTE

@Composable
fun SetupBottomNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Recipes.route,
        route = BOTTOM_NAVIGATION_ROUTE
    ){
        composable(route = BottomBarScreen.Recipes.route){
            RecipesScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Favorite.route){
            FoodJokeScreen(navController = navController)
        }
        composable(route = BottomBarScreen.FoodJoke.route){
            FavoriteScreen(navController = navController)
        }
        composable(route = Screen.SearchPage.route) {
            SearchScreen(navHostController = navController)
        }
    }

}