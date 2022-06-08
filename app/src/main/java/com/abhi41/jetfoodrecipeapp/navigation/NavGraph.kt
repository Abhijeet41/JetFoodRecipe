package com.abhi41.jetfoodrecipeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen.DashBoardScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.recipesScreen.RecipesScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.splashScreen.SplashScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ){
        composable(route = Screen.Splash.route){
            SplashScreen(navController = navController)
        }
        composable(route = Screen.DashBoard.route){
            DashBoardScreen()
        }

        composable(route = Screen.DetailPage.route){

        }
    }
}