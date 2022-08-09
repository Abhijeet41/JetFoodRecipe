package com.abhi41.jetfoodrecipeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abhi41.jetfoodrecipeapp.BottomBarScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.recipesScreen.RecipesScreen
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun SetupBottomNavGraph(
    navController: NavHostController,
    navigator: DestinationsNavigator
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Recipes.route,

        ) {
        composable(route = BottomBarScreen.Recipes.route) {
            RecipesScreen(navigator)
        }
    }

}

