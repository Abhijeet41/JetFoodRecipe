package com.abhi41.jetfoodrecipeapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi41.jetfoodrecipeapp.model.FoodJoke
import com.abhi41.jetfoodrecipeapp.utils.Constants

@Entity(tableName = Constants.FOOD_JOKE_TABLE)
data class FoodJokeEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val text: String?
){
    fun toFoodJoke(): FoodJoke {
        return FoodJoke(
            text = text
        )
    }
}
