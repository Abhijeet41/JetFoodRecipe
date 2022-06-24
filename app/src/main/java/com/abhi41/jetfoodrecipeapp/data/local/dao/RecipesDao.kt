package com.abhi41.jetfoodrecipeapp.data.local.dao

import androidx.room.*
import com.abhi41.foodrecipe.model.Result
import com.abhi41.jetfoodrecipeapp.data.local.entities.FoodJokeEntity
import com.abhi41.jetfoodrecipeapp.data.local.entities.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {
    //---------------------queries for recipes-------------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: List<Result>)

    @Query("SELECT * FROM recipes_table ORDER BY recipeId ASC")
    fun readRecipes(): Flow<List<Result>>

    @Query("SELECT * FROM recipes_table WHERE recipeId = :recipeId")
    fun getSelectedRecipe(recipeId: Int): Result

    //----------------queries for FavoriteRecipes-----------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite_recipes_table ORDER BY id ASC")
    fun readFavoriteRecipes(): Flow<List<FavoriteEntity>>

    @Delete
   suspend fun deleteFavoriteRecipes(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM FAVORITE_RECIPES_TABLE")
    suspend fun deleteAllFavoriteRecipes()

    //------------queries for food joke------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)

    @Query("SELECT * FROM food_joke_table ORDER BY id ASC")
    fun readFoodJoke(): Flow<List<FoodJokeEntity>>

}