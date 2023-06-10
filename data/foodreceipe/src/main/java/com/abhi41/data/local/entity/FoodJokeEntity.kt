package com.abhi41.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi41.ui.model.FoodJoke
import com.abhi41.ui.util.Constants

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
