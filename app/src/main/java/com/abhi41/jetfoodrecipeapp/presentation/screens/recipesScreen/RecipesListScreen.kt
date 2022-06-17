package com.abhi41.jetfoodrecipeapp.presentation.screens.recipesScreen


import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.abhi41.foodrecipe.model.Result
import com.abhi41.jetfoodrecipeapp.navigation.Graph
import com.abhi41.jetfoodrecipeapp.navigation.Screen
import com.abhi41.jetfoodrecipeapp.presentation.common.ListContent
import com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen.DashBoardViewModel
import com.abhi41.jetfoodrecipeapp.utils.NetworkResult


private const val TAG = "RecipesListScreen"
var foodRecipes = emptyList<Result>()


@Composable
fun RecipesScreen(
    navController: NavHostController,
    recipesViewModel: RecipesViewModel = hiltViewModel(),
    dashBoardViewModel: DashBoardViewModel = hiltViewModel()
) {

    val readRecipes by dashBoardViewModel.readRecipes.observeAsState()

    if (!readRecipes.isNullOrEmpty()) {
        foodRecipes = readRecipes as List<Result>

        RecipeDesign(
            foodRecipes = foodRecipes,
            navController = navController
        )
    } else {
        requestApiData(
            recipesViewModel = recipesViewModel,
            dashBoardViewModel = dashBoardViewModel
        )
    }

    observers(dashBoardViewModel)

}

@Composable
fun RecipeDesign(
    foodRecipes: List<Result>,
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier
            .padding(bottom = 20.dp),
        topBar = {
            RecipesTopBar {
                //onSearch icon clicked navigate to search screen
                navController.navigate(Screen.SearchPage.route)
            }
        }
    ) {
        if(!foodRecipes.isNullOrEmpty())
        {
            ListContent(foodRecipes, navController)
        }else{
            //show error screen
        }

    }
}


fun requestApiData(
    recipesViewModel: RecipesViewModel,
    dashBoardViewModel: DashBoardViewModel
) {
    dashBoardViewModel.getRecipes(recipesViewModel.applyQueries())
}

@Composable
fun observers(
    dashBoardViewModel: DashBoardViewModel,
) {
    val response by dashBoardViewModel.recipesResponse.observeAsState()
    val context = LocalContext.current

    when (response) {
        is NetworkResult.Success -> {
            //   hideShimmerEffect()
            foodRecipes = response?.data?.results!!
        }

        is NetworkResult.Error -> {
            //   hideShimmerEffect()
            ///  loadDataFromCache()
            Toast.makeText(
                context,
                response?.message.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

        is NetworkResult.Loading -> {
            //   showShimmerEffect()
        }
    }


}


