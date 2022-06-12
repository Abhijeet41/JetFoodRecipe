package com.abhi41.jetfoodrecipeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen.DashBoardScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.searchScreen.SearchScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.splashScreen.SplashScreen
import com.abhi41.jetfoodrecipeapp.utils.Constants.ROOT_ROUTE

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        route = ROOT_ROUTE
    ) {

        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.DashBoard.route) {
            DashBoardScreen()
        }
        composable(route = Screen.SearchPage.route) {
            SearchScreen(navHostController = navController)
        }
        composable(route = Screen.DetailPage.route) {
            //yet to impl
        }
    }
}