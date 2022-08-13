package com.abhi41.jetfoodrecipeapp.presentation.screens.searchScreen

import com.abhi41.jetfoodrecipeapp.model.Result

data class SearchState(
    val result: List<Result> = emptyList(),
    val isLoading: Boolean = false
)
