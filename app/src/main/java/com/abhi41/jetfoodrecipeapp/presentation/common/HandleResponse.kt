package com.abhi41.jetfoodrecipeapp.presentation.common

import com.abhi41.foodrecipe.model.FoodRecipe
import com.abhi41.jetfoodrecipeapp.utils.Resource
import retrofit2.Response

class HandleResponse {

    companion object{
        /* we make this common because we use handleFoodRecipesResponse method in
         search screen  as well as in recipes screen
         */
         fun handleFoodRecipesResponse(response: Response<FoodRecipe>): Resource<FoodRecipe>? {
            when {
                response.message().contains("timeout") -> {
                    return Resource.Error("Connection TimeOut")
                }

                response.code() == 402 -> {
                    return Resource.Error("Api Key Limited.")
                }

                response.body()?.results.isNullOrEmpty() -> {
                    return Resource.Error("Recipes not found")
                }

                response.isSuccessful -> {
                    val foodRecipes = response.body()
                    return Resource.Success(foodRecipes!!)
                }

                else -> {
                    return Resource.Error(response.message())
                }

            }
        }
    }

}