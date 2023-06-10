package com.abhi41.data.dto

import androidx.annotation.Keep
import com.abhi41.data.dto.ResultDto
import com.google.gson.annotations.SerializedName

@Keep
data class FoodRecipeDto(
    @SerializedName("results")
    val results: List<ResultDto>
)
