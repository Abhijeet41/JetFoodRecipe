package com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.abhi41.jetfoodrecipeapp.BottomBarScreen
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.navigation.SetupBottomNavGraph
import com.abhi41.jetfoodrecipeapp.ui.theme.BOTTOMSHEET_HEIGHT
import com.abhi41.jetfoodrecipeapp.ui.theme.TXT_MEDIUM_SIZE
import kotlinx.coroutines.launch



@Composable
fun DashBoardScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        },
        content = {
            SetupBottomNavGraph(navController = navController)
        }
    )
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

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }

    if (bottomBarDestination) {
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

            navController.navigate(route = screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true //to avoid creating copy of multiple start destination
            }
        }
    )
}


