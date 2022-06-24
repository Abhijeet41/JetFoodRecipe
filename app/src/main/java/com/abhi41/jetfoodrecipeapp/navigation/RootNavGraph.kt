package com.abhi41.jetfoodrecipeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen.DashBoardScreen


@Composable
fun RootNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION

    ) {

        AuthNavGraph(navController = navController)

        composable(route = Graph.DASHBOARD) {
            DashBoardScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val DASHBOARD = "dashboard_graph"
    const val DETAILS = "details_graph"
}