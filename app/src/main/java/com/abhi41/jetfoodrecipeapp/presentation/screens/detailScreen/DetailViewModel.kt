package com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.abhi41.jetfoodrecipeapp.data.local.dao.RecipesDao
import com.abhi41.jetfoodrecipeapp.data.local.entity.FavoriteEntity
import com.abhi41.jetfoodrecipeapp.data.local.entity.ResultEntity
import com.abhi41.jetfoodrecipeapp.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
     val recipesDao: RecipesDao
):ViewModel() {

    val readFavoriteRecipes = recipesDao.readFavoriteRecipes().asLiveData()

    fun insertFavoriteRecipes(recipes: FavoriteEntity){
        viewModelScope.launch {
            recipesDao.insertFavoriteRecipe(favoriteEntity = recipes)
        }
    }

    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity) {
        return recipesDao.deleteFavoriteRecipe(favoriteEntity = favoriteEntity)
    }

    suspend fun deleteAllFavoriteRecipes() {
        return recipesDao.deleteAllFavoriteRecipes()
    }



}