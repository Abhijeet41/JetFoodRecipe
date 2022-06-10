package com.abhi41.jetfoodrecipeapp.presentation.screens.recipesScreen


import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.abhi41.jetfoodrecipeapp.presentation.common.ListContent
import com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen.DashBoardViewModel
import com.abhi41.jetfoodrecipeapp.utils.NetworkResult
import kotlinx.coroutines.launch


private const val TAG = "RecipesListScreen"

@Composable
fun RecipesScreen(
    navController: NavHostController,
    recipesViewModel: RecipesViewModel = hiltViewModel(),
    dashBoardViewModel: DashBoardViewModel = hiltViewModel()
) {
    readData(
        navController = navController,
        recipesViewModel = recipesViewModel,
        dashBoardViewModel = dashBoardViewModel
    )
    observers(dashBoardViewModel, navController)
}


@Composable
private fun readData(
    navController: NavHostController,
    dashBoardViewModel: DashBoardViewModel,
    recipesViewModel: RecipesViewModel
) {
    val readRecipes by dashBoardViewModel.readRecipes.observeAsState()

    if (!readRecipes.isNullOrEmpty()) {
        ListContent(readRecipes?.get(0)!!.foodRecipe.results, navController)
    } else {
        requestApiData(
            recipesViewModel = recipesViewModel,
            dashBoardViewModel = dashBoardViewModel
        )
    }

}

fun requestApiData(
    recipesViewModel: RecipesViewModel,
    dashBoardViewModel: DashBoardViewModel
) {
    dashBoardViewModel.getRecipes(recipesViewModel.applyQueries())
}

@Composable
fun observers(dashBoardViewModel: DashBoardViewModel, navController: NavHostController) {
    val response by dashBoardViewModel.recipesResponse.observeAsState()
    val context = LocalContext.current

    when (response) {
        is NetworkResult.Success -> {
            //   hideShimmerEffect()
            val foodRecipe = response?.data?.results
            Surface(modifier = Modifier.padding(bottom = 20.dp)) {
                ListContent(foodRecipe, navController)
            }
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


