package com.abhi41.jetfoodrecipeapp.data.usecase

import com.abhi41.jetfoodrecipeapp.domain.RecipesRepository
import com.abhi41.jetfoodrecipeapp.model.FoodJoke
import com.abhi41.jetfoodrecipeapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FoodJokeUsecase(
    private val repository: RecipesRepository
) {
    operator fun invoke(): Flow<Resource<List<FoodJoke>>> {
        return repository.getFoodJokes()
    }
}