package com.abhi41.jetfoodrecipeapp.presentation.screens.searchScreen

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.abhi41.jetfoodrecipeapp.presentation.common.RecipesListContent
import com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen.DashBoardViewModel
import com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen.SharedResultViewModel
import com.abhi41.jetfoodrecipeapp.utils.NetworkResult

@Composable
fun SearchScreen(
    navHostController: NavHostController,
    sharedResultViewModel: SharedResultViewModel,
    searchViewModel: SearchViewModel = hiltViewModel(),
    dashBoardViewModel: DashBoardViewModel = hiltViewModel()
) {
    val response by dashBoardViewModel.searchedRecipesResponse.observeAsState()
    val searchQuery by searchViewModel.searchQuery

    Scaffold(
        topBar = {
            SearchTopBar(
                text = searchQuery,
                onTextChange = {
                    searchViewModel.updateSearchQuery(query = it)
                },
                onSearchedClicked = {
                    dashBoardViewModel.searchRecipes(searchViewModel.applySearchQuery(it))
                },
                onClosedClicked = {
                    navHostController.popBackStack()

                },
                dashBoardViewModel
            )
        },
        content = {
            when (response) {

                is NetworkResult.Success -> {
                    val recipes = response?.data?.results
                    RecipesListContent(
                        foodRecipes = recipes,
                        navController = navHostController,
                        sharedResultViewModel = sharedResultViewModel
                    )
                }

                is NetworkResult.Error -> {
                    //error
                }

                is NetworkResult.Loading -> {
                    //loading
                }
            }

        }
    )

}