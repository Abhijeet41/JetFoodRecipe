package com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.abhi41.foodrecipe.model.Result
import com.abhi41.jetfoodrecipeapp.data.local.LocalDataSource
import com.abhi41.jetfoodrecipeapp.data.local.entities.FavoriteEntity
import com.abhi41.jetfoodrecipeapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "DetailViewModel"

@HiltViewModel
class DetailViewModel @Inject constructor(
    val localDataSource: LocalDataSource,
) : ViewModel() {


    //read from favorite database
    val readFavoriteRecipes = localDataSource.readFavoriteRecipes().asLiveData()

    //insert into favorite database
     fun insertFavoriteRecipe(favoriteEntity: FavoriteEntity){
         viewModelScope.launch {
             localDataSource.insertFavoriteRecipe(favoriteEntity)
         }
    }
    //delete into favorite database
    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity){
        localDataSource.deleteFavoriteRecipes(favoriteEntity = favoriteEntity)
    }
    suspend fun deleteAllFavoriteRecipes(){
        localDataSource.deleteAllFavoriteRecipes()
    }


}