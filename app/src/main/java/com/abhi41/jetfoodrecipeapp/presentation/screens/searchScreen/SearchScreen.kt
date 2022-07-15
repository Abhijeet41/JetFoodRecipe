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
import com.abhi41.jetfoodrecipeapp.utils.AnimatedShimmer
import com.abhi41.jetfoodrecipeapp.utils.Resource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
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
                    navigator.popBackStack()
                },
                dashBoardViewModel
            )
        },
        content = {
            when (response) {
                is Resource.Success -> {
                    val recipes = response?.data?.results
                    RecipesListContent(
                        foodRecipes = recipes,
                        navigator = navigator,
                    )
                }

                is Resource.Error -> {
                    //error
                }

                is Resource.Loading -> {
                    //loading
                    AnimatedShimmer()
                }
            }

        }
    )

}