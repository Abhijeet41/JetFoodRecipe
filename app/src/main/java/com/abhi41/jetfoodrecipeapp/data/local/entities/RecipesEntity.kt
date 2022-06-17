package com.abhi41.jetfoodrecipeapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi41.foodrecipe.model.FoodRecipe
import com.abhi41.jetfoodrecipeapp.utils.Constants

//@Entity(tableName = Constants.RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
 //   @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}