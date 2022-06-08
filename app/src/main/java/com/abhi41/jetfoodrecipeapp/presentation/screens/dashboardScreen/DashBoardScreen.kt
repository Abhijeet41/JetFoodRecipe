package com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.abhi41.jetfoodrecipeapp.BottomBarScreen
import com.abhi41.jetfoodrecipeapp.navigation.SetupBottomNavGraph

@Composable
fun DashBoardScreen() {
    val navController = rememberNavController()

    Scaffold(bottomBar = {
        BottomBar(navController = navController)
    }) {
        SetupBottomNavGraph(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Recipes,
        BottomBarScreen.Favorite,
        BottomBarScreen.FoodJoke,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "Bottom Nav Icon",
            )
        },
        selected = currentDestination?.hierarchy?.any { current ->
            current.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        selectedContentColor = Color.White,
        onClick = {

            navController.navigate(route = screen.route){
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true //to avoid creating copy of multiple start destination
            }
        }
    )
}