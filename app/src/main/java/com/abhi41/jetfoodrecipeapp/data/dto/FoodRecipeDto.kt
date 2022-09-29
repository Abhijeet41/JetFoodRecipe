package com.abhi41.jetfoodrecipeapp.data.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class FoodRecipeDto(
    @SerializedName("results")
    val results: List<ResultDto>
)
