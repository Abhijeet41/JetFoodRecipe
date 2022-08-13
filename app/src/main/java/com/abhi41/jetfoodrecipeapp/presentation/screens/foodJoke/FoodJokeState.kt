package com.abhi41.jetfoodrecipeapp.presentation.screens.foodJoke

import com.abhi41.jetfoodrecipeapp.model.FoodJoke

data class FoodJokeState(
    val foodJoke:List<FoodJoke> = emptyList(),
    val isLoading:Boolean = false
)
