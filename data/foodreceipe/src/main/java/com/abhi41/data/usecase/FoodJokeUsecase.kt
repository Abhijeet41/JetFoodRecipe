package com.abhi41.jetfoodrecipeapp.data.usecase

import com.abhi41.domain.RecipesRepository
import com.abhi41.ui.model.FoodJoke
import com.abhi41.util.Resource
import kotlinx.coroutines.flow.Flow

class FoodJokeUsecase(
    private val repository: RecipesRepository
) {
    operator fun invoke(): Flow<Resource<List<FoodJoke>>> {
        return repository.getFoodJokes()
    }
}