package com.abhi41.jetfoodrecipeapp.presentation.common

import com.abhi41.foodrecipe.model.FoodRecipe
import com.abhi41.jetfoodrecipeapp.utils.NetworkResult
import retrofit2.Response

class HandleResponse {

    companion object{
        /* we make this common because we use handleFoodRecipesResponse method in
         search screen  as well as in recipes screen
         */
         fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
            when {
                response.message().contains("timeout") -> {
                    return NetworkResult.Error("Connection TimeOut")
                }

                response.code() == 402 -> {
                    return NetworkResult.Error("Api Key Limited.")
                }

                response.body()?.results.isNullOrEmpty() -> {
                    return NetworkResult.Error("Recipes not found")
                }

                response.isSuccessful -> {
                    val foodRecipes = response.body()
                    return NetworkResult.Success(foodRecipes!!)
                }

                else -> {
                    return NetworkResult.Error(response.message())
                }

            }
        }
    }

}