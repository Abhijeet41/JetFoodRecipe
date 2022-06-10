package com.abhi41.jetfoodrecipeapp.data.network

import com.abhi41.foodrecipe.model.FoodJoke
import com.abhi41.foodrecipe.model.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi,
) {

    suspend fun getRecipes( quries: Map<String, String> ):Response<FoodRecipe> {
        return foodRecipesApi.getRecipies(queries = quries)
    }

    suspend fun searchRecipes( quries: Map<String, String>): Response<FoodRecipe>{
        return foodRecipesApi.searchRecipes(quries)
    }

    suspend fun getFoodJoke(apikey: String):Response<FoodJoke>{
        return foodRecipesApi.getFoodJoke(apikey)
    }

}