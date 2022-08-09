package com.abhi41.jetfoodrecipeapp.data.local.dao

import androidx.room.*
import com.abhi41.foodrecipe.model.Result
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

    @Query("DELETE FROM recipes_table")
    suspend fun deleteAllRecipes()

    //----------------queries for FavoriteRecipes-----------------------------


}