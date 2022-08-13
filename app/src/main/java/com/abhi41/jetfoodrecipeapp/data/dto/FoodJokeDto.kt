package com.abhi41.jetfoodrecipeapp.data.dto


import com.abhi41.jetfoodrecipeapp.data.local.entity.FoodJokeEntity
import com.abhi41.jetfoodrecipeapp.model.FoodJoke
import com.google.gson.annotations.SerializedName

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