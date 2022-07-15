package com.abhi41.jetfoodrecipeapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhi41.jetfoodrecipeapp.BottomBarScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen.SharedResultViewModel
import com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.DetailScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.favoriteScreen.FavoriteScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.foodJoke.FoodJokeScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.recipesScreen.RecipesScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.searchScreen.SearchScreen
import com.abhi41.jetfoodrecipeapp.utils.Constants.DETAILS_ARGUMENTS_KEY
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
        composable(route = BottomBarScreen.Favorite.route) {
            FavoriteScreen(navigator)
        }
        composable(route = BottomBarScreen.FoodJoke.route) {
            FoodJokeScreen()
        }
    }

}

