package com.abhi41.jetfoodrecipeapp.data.local.typeConverters

import androidx.room.TypeConverter
import com.abhi41.foodrecipe.model.ExtendedIngredient
import com.abhi41.foodrecipe.model.FoodRecipe
import com.abhi41.foodrecipe.model.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {

    private var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe): String {
        return  gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodRecipe{
        val listType = object : TypeToken<FoodRecipe>(){}.type
        return gson.fromJson(data,listType)
    }

    @TypeConverter
    fun resultToString(result: Result): String{
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToResult(data: String): Result{
        val listType = object : TypeToken<Result>(){}.type
        return gson.fromJson(data,listType)
    }
    @TypeConverter
    fun resultToIngredient(result: List<ExtendedIngredient>): String {
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToIngredient(data: String): List<ExtendedIngredient> {
        val listType = object : TypeToken<List<ExtendedIngredient>>() {}.type
        return gson.fromJson(data, listType)
    }
}