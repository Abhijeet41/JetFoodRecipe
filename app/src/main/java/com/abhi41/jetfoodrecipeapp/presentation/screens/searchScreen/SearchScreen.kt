package com.abhi41.jetfoodrecipeapp.presentation.screens.searchScreen

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhi41.jetfoodrecipeapp.presentation.common.RecipesListContent
import com.abhi41.jetfoodrecipeapp.utils.AnimatedShimmer
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

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
            )
        }else{
            AnimatedShimmer()
        }

    }

}