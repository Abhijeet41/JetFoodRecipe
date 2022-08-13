package com.abhi41.jetfoodrecipeapp.data.dto

import com.google.gson.annotations.SerializedName

data class FoodRecipeDto(
    @SerializedName("results")
    val results: List<ResultDto>
)
