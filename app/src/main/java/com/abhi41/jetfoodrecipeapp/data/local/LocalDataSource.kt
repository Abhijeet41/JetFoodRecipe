package com.abhi41.jetfoodrecipeapp.data.local

import com.abhi41.foodrecipe.model.Result
import com.abhi41.jetfoodrecipeapp.data.local.dao.RecipesDao
import com.abhi41.jetfoodrecipeapp.data.local.entities.FoodJokeEntity
import com.abhi41.jetfoodrecipeapp.data.local.entities.FavoriteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
   val recipesDao: RecipesDao
) {
    //Queries for  recipes
    suspend fun insertRecipes( recipesEntity: List<Result>){
        recipesDao.insertRecipes(recipesEntity = recipesEntity)
    }

     fun readRecipes(): Flow<List<Result>> {
        return recipesDao.readRecipes()
    }

     fun getSelectedRecipe(recipeId: Int):Result{
        return recipesDao.getSelectedRecipe(recipeId = recipeId)
    }

    //Queries for Favorite Recipe

    suspend fun insertFavoriteRecipe(favoriteEntity: FavoriteEntity){
        return recipesDao.insertFavoriteRecipe(favoriteEntity)
    }

    fun readFavoriteRecipes(): Flow<List<FavoriteEntity>>{
        return recipesDao.readFavoriteRecipes()
    }

    suspend fun deleteFavoriteRecipes(favoriteEntity: FavoriteEntity){
        return recipesDao.deleteFavoriteRecipes(favoriteEntity = favoriteEntity)
    }

    suspend fun deleteAllFavoriteRecipes(){
        return recipesDao.deleteAllFavoriteRecipes()
    }

    //Queries for FoodJoke

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity){
        return recipesDao.insertFoodJoke(foodJokeEntity)
    }

     fun readFoodJoke(): Flow<List<FoodJokeEntity>>{
        return recipesDao.readFoodJoke()
    }

}