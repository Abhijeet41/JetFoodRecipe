package com.abhi41.jetfoodrecipeapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.abhi41.jetfoodrecipeapp.data.local.Converters
import com.abhi41.jetfoodrecipeapp.data.local.dao.RecipesDao
import com.abhi41.jetfoodrecipeapp.data.local.entity.FavoriteEntity
import com.abhi41.jetfoodrecipeapp.data.local.entity.FoodJokeEntity
import com.abhi41.jetfoodrecipeapp.data.local.entity.ResultEntity

@Database(
    entities = [ResultEntity::class, FavoriteEntity::class, FoodJokeEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipesDao

    companion object {
        val migration_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE food_joke_table (id INTEGER, text TEXT, PRIMARY KEY (id)) ")
            }

        }
    }

}