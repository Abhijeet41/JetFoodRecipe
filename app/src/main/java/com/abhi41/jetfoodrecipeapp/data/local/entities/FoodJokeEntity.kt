package com.abhi41.jetfoodrecipeapp.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi41.foodrecipe.model.FoodJoke
import com.abhi41.jetfoodrecipeapp.utils.Constants

@Entity(tableName = Constants.FOOD_JOKE_TABLE)
data class FoodJokeEntity(
    @Embedded  //with this annotation we can inspect food joke model class
    var foodjoke: FoodJoke
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
