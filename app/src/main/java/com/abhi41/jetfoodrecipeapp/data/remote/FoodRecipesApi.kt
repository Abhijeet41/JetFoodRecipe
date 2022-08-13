package com.abhi41.jetfoodrecipeapp.data.remote

import com.abhi41.jetfoodrecipeapp.data.dto.FoodJokeDto
import com.abhi41.jetfoodrecipeapp.data.dto.FoodRecipeDto
import com.abhi41.jetfoodrecipeapp.model.FoodJoke
import com.abhi41.jetfoodrecipeapp.utils.Resource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipies(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipeDto>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery: Map<String, String>
    ): Response<FoodRecipeDto>

    @GET("/food/jokes/random")
    suspend fun getFoodJoke(
        @Query("apiKey") apiKey:String
    ): Response<FoodJokeDto>

}