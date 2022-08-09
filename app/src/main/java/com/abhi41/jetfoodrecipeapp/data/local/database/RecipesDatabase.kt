package com.abhi41.jetfoodrecipeapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.abhi41.foodrecipe.model.Result
import com.abhi41.jetfoodrecipeapp.data.local.dao.RecipesDao


abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipesDao

}