package com.abhi41.jetfoodrecipeapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abhi41.jetfoodrecipeapp.data.local.dao.RecipesDao
import com.abhi41.jetfoodrecipeapp.data.local.entities.RecipesEntity
import com.abhi41.jetfoodrecipeapp.data.local.typeConverters.RecipesTypeConverter

@Database(
    entities = [RecipesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipesDao
}