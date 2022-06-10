package com.abhi41.jetfoodrecipeapp.data.local

import com.abhi41.jetfoodrecipeapp.data.local.dao.RecipesDao
import com.abhi41.jetfoodrecipeapp.data.local.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
   val recipesDao: RecipesDao
) {
    //inserting recipes
    suspend fun insertRecipes( recipesEntity: RecipesEntity ){
        recipesDao.insertRecipes(recipesEntity = recipesEntity)
    }

     fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

}