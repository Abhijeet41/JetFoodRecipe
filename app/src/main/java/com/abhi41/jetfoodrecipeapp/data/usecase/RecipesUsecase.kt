package com.abhi41.jetfoodrecipeapp.data.usecase

import androidx.annotation.Keep
import com.abhi41.jetfoodrecipeapp.domain.RecipesRepository
import com.abhi41.jetfoodrecipeapp.model.Result
import com.abhi41.jetfoodrecipeapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Keep
class RecipesUsecase(
    private val repository: RecipesRepository
) {
    operator fun invoke (queries: Map<String, String>): Flow<Resource<List<Result>>>{
        if (queries.isEmpty()){
            return flow {  }
        }
        return repository.getRecipes(queries = queries)
    }

}