package com.abhi41.jetfoodrecipeapp.domain

import androidx.annotation.Keep
import com.abhi41.jetfoodrecipeapp.model.FoodJoke
import com.abhi41.jetfoodrecipeapp.model.Result
import com.abhi41.jetfoodrecipeapp.utils.Resource
import kotlinx.coroutines.flow.Flow

@Keep
interface RecipesRepository {
    fun getRecipes(queries: Map<String, String>): Flow<Resource<List<Result>>>
    fun getFoodJokes(): Flow<Resource<List<FoodJoke>>>
    fun getSearchRecipes(queries: Map<String, String>): Flow<Resource<List<Result>>>
}