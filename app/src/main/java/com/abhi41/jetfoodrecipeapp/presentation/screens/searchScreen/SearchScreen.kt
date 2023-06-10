package com.abhi41.jetfoodrecipeapp.presentation.screens.searchScreen

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhi41.jetfoodrecipeapp.presentation.destinations.DetailScreenDestination
import com.abhi41.ui.common.RecipesListContent
import com.abhi41.ui.common.AnimatedShimmer
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val state = searchViewModel.searchState.value
    val searchRecipes = state.result
    val searchQuery by searchViewModel.searchQuery

    Scaffold(
        topBar = {
            SearchTopBar(
                text = searchQuery,
                onTextChange = {
                    searchViewModel.updateSearchQuery(query = it)
                },
                onSearchedClicked = { searchQuery->
                    searchViewModel.getSearchRecipes(searchQuery)
                },
                onClosedClicked = {
                    navigator.popBackStack()
                },
                searchViewModel
            )
        }
    ) {
        if (!searchViewModel.searchState.value.isLoading){
            RecipesListContent(
                foodRecipes = searchRecipes,
                navigator = navigator,
                itemClick = {food->
                    navigator.navigate(DetailScreenDestination(food))
                }
            )
        }else{
            AnimatedShimmer()
        }

    }

}