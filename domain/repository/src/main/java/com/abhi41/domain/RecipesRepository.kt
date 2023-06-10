package com.abhi41.domain

import androidx.annotation.Keep
import com.abhi41.ui.model.FoodJoke
import com.abhi41.util.Resource
import kotlinx.coroutines.flow.Flow
import com.abhi41.ui.model.Result

@Keep
interface RecipesRepository {
    fun getRecipes(queries: Map<String, String>): Flow<Resource<List<Result>>>
    fun getFoodJokes(): Flow<Resource<List<FoodJoke>>>
    fun getSearchRecipes(queries: Map<String, String>): Flow<Resource<List<Result>>>
}