package com.abhi41.jetfoodrecipeapp.di

import android.content.Context
import androidx.room.Room
import com.abhi41.data.local.Converters
import com.abhi41.data.local.database.RecipesDatabase
import com.abhi41.jetfoodrecipeapp.data.local.util.GsonParser

import com.abhi41.ui.util.Constants
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context, // dagger provide us context to use this for creation of builder

    ): RecipesDatabase {
        val supportFactory = SupportFactory(SQLiteDatabase.getBytes("password".toCharArray()))
        return Room.databaseBuilder(
            context,
            RecipesDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration()
         //   .openHelperFactory(supportFactory) In this way we can use sql cipher
            .addMigrations(RecipesDatabase.migration_1_2)
            .addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(database: RecipesDatabase) = database.recipeDao()


}