package com.abhi41.jetfoodrecipeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abhi41.jetfoodrecipeapp.BottomBarScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.DetailScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.favoriteScreen.FavoriteScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.foodJoke.FoodJokeScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.recipesScreen.RecipesScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.searchScreen.SearchScreen
import com.abhi41.jetfoodrecipeapp.utils.Constants.BOTTOM_NAVIGATION_ROUTE
import com.abhi41.jetfoodrecipeapp.utils.Constants.DETAILS_ARGUMENTS_KEY

@Composable
fun SetupBottomNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        route = Graph.DASHBOARD,
        startDestination = BottomBarScreen.Recipes.route,

        ) {
        composable(route = BottomBarScreen.Recipes.route) {
            RecipesScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Favorite.route) {
            FavoriteScreen(navController = navController)
        }
        composable(route = BottomBarScreen.FoodJoke.route) {
            FoodJokeScreen()
        }
        recipeNavGraph(navController = navController)

    }

}

fun NavGraphBuilder.recipeNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.DETAILS,
        startDestination = Screen.DetailPage.route
    ) {
        composable(
            route = Screen.DetailPage.route,
            arguments = listOf(
                navArgument(DETAILS_ARGUMENTS_KEY){
                    type = NavType.IntType
                }
            )
        ) {
            DetailScreen(navController)
        }
        composable(route = Screen.SearchPage.route) {
            SearchScreen(navHostController = navController)
        }
    }

}
