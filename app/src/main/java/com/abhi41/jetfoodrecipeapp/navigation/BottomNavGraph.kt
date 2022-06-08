package com.abhi41.jetfoodrecipeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abhi41.jetfoodrecipeapp.BottomBarScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.favoriteScreen.FavoriteScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.foodJoke.FoodJokeScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.recipesScreen.RecipesScreen

@Composable
fun SetupBottomNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Recipes.route
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
    }

}