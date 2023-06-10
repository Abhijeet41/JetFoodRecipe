package com.abhi41.jetfoodrecipeapp.presentation.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.abhi41.data.local.dao.RecipesDao
import com.abhi41.data.local.entity.FavoriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val dao: RecipesDao
):ViewModel() {

    //read from favorite database
    val readFavoriteRecipes = dao.readFavoriteRecipes().asLiveData()

    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity){
        dao.deleteFavoriteRecipe(favoriteEntity = favoriteEntity)
    }

    suspend fun deleteAllFavoriteRecipes(){
        dao.deleteAllFavoriteRecipes()
    }
}