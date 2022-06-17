package com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi41.foodrecipe.model.Result
import com.abhi41.jetfoodrecipeapp.data.local.LocalDataSource
import com.abhi41.jetfoodrecipeapp.data.local.entities.RecipesEntity
import com.abhi41.jetfoodrecipeapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "DetailViewModel"

@HiltViewModel
class DetailViewModel @Inject constructor(
    val localDataSource: LocalDataSource,
    savedStateHandle: SavedStateHandle //savedStateHandle allow us to retrieve heroId from DetailScreen

) : ViewModel() {

    private val _selectRecipe: MutableStateFlow<Result?> = MutableStateFlow(null)
    val selectedRecipe: StateFlow<Result?> = _selectRecipe

    init {
        viewModelScope.launch(Dispatchers.IO) {
            //Instead of getting id in detail page screen we get in ViewModel to fetch details against id

            /* so with the help of savedStateHandle
              we get recipeId which is passed through RecipesScreen or SearchScreen*/
            val recipeId = savedStateHandle.get<Int>(Constants.DETAILS_ARGUMENTS_KEY)
            _selectRecipe.value = recipeId?.let { localDataSource.getSelectedRecipe(it) }
            _selectRecipe.value?.let {  //log purpose only
                 Log.d(TAG, "${it.image}")
             }

        /*    StateFlow is a type of interface, which is only a read-only and always returns the
            updated value. And to receive the updated value we just collect the value from
            the implemented Flow.*/

        }
    }

}