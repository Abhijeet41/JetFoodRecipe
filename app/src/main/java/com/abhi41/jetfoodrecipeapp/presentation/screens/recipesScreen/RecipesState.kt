package com.abhi41.jetfoodrecipeapp.presentation.screens.recipesScreen

import com.abhi41.jetfoodrecipeapp.model.Result

data class RecipesState(
    val recipesItem:List<Result> = emptyList(),
    val isLoading: Boolean = false
)
