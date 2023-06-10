package com.abhi41.jetfoodrecipeapp.presentation.common

import com.abhi41.data.dto.FoodRecipeDto
import com.abhi41.ui.util.Resource
import retrofit2.Response

class HandleResponse {

    companion object{
        /* we make this common because we use handleFoodRecipesResponse method in
         search screen  as well as in recipes screen
         */
         fun handleFoodRecipesResponse(response: Response<FoodRecipeDto>): Resource<FoodRecipeDto>? {
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