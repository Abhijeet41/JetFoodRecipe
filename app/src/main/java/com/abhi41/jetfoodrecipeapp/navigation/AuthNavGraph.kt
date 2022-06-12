package com.abhi41.jetfoodrecipeapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.abhi41.jetfoodrecipeapp.presentation.screens.splashScreen.SplashScreen


fun NavGraphBuilder.AuthNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = Screen.Splash.route
    ){
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
    }
}