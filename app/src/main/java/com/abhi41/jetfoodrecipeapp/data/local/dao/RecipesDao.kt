package com.abhi41.jetfoodrecipeapp.data.local.dao

import androidx.room.*
import com.abhi41.jetfoodrecipeapp.data.local.entity.FavoriteEntity
import com.abhi41.jetfoodrecipeapp.data.local.entity.FoodJokeEntity
import com.abhi41.jetfoodrecipeapp.data.local.entity.ResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    //---------------------queries for recipes-------------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: List<ResultEntity>)

    @Query("SELECT * FROM recipes_table ORDER BY recipeId ASC")
    fun readRecipes(): List<ResultEntity>

    @Query("SELECT * FROM recipes_table WHERE recipeId = :recipeId")
    fun getSelectedRecipe(recipeId: Int): ResultEntity

    @Query("DELETE FROM recipes_table")
    suspend fun deleteAllRecipes()

    //----------------queries for FavoriteRecipes-----------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite_recipes_table ORDER BY id ASC")
    fun readFavoriteRecipes(): Flow<List<FavoriteEntity>>

    @Delete
    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM FAVORITE_RECIPES_TABLE")
    suspend fun deleteAllFavoriteRecipes()

    //------------queries for food joke------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)

    @Query("SELECT * FROM food_joke_table ORDER BY id ASC")
    fun readFoodJoke(): List<FoodJokeEntity>

    @Query("DELETE FROM food_joke_table")
    suspend fun deleteAllFoodJoke()

}