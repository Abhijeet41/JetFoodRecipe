package com.abhi41.jetfoodrecipeapp.data.dto


import androidx.annotation.Keep
import com.abhi41.data.local.entity.FoodJokeEntity
import com.google.gson.annotations.SerializedName

@Keep
data class FoodJokeDto(
    @SerializedName("text")
    val text: String?
){
    fun toFoodJoke(): FoodJokeEntity {
        return FoodJokeEntity(
            text = text
        )
    }
}