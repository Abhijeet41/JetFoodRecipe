package com.abhi41.jetfoodrecipeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ){
        composable(route = Screen.Splash.route){

        }
        composable(route = Screen.Recipes.route){

        }
        composable(route = Screen.FavoriteRecipes.route){

        }
        composable(route = Screen.FoodJoke.route){

        }
        composable(route = Screen.DetailPage.route){

        }
    }
}