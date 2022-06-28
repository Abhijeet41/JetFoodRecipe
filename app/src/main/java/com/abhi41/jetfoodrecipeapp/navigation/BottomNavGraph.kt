package com.abhi41.jetfoodrecipeapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abhi41.jetfoodrecipeapp.BottomBarScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen.SharedResultViewModel
import com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.DetailScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.favoriteScreen.FavoriteScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.foodJoke.FoodJokeScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.recipesScreen.RecipesScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.searchScreen.SearchScreen
import com.abhi41.jetfoodrecipeapp.utils.Constants.DETAILS_ARGUMENTS_KEY

@Composable
fun SetupBottomNavGraph(
    navController: NavHostController
) {
    val sharedResultViewModel: SharedResultViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        route = Graph.DASHBOARD,
        startDestination = BottomBarScreen.Recipes.route,

        ) {
        composable(route = BottomBarScreen.Recipes.route) {
            RecipesScreen(navController = navController,sharedResultViewModel)
        }
        composable(route = BottomBarScreen.Favorite.route) {
            FavoriteScreen(navController = navController,sharedResultViewModel)
        }
        composable(route = BottomBarScreen.FoodJoke.route) {
            FoodJokeScreen()
        }
        recipeNavGraph(navController = navController,sharedResultViewModel)

    }

}

fun NavGraphBuilder.recipeNavGraph(
    navController: NavHostController,
    sharedResultViewModel: SharedResultViewModel
) {

    navigation(
        route = Graph.DETAILS,
        startDestination = Screen.DetailPage.route
    ) {
        composable(
            route = Screen.DetailPage.route,
            arguments = listOf(
                navArgument(DETAILS_ARGUMENTS_KEY) {
                    type = NavType.IntType
                }
            )
        ) {

            DetailScreen(navController,sharedResultViewModel)
        }
        composable(route = Screen.SearchPage.route) {
            SearchScreen(navHostController = navController,sharedResultViewModel)
        }
    }

}
