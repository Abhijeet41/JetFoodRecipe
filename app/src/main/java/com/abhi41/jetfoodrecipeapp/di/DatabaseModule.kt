package com.abhi41.jetfoodrecipeapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abhi41.jetfoodrecipeapp.data.local.database.RecipesDatabase
import com.abhi41.jetfoodrecipeapp.utils.Constants
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


    @Singleton
    @Provides
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
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase) = database.recipeDao()

}