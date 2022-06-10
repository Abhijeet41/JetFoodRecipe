package com.abhi41.jetfoodrecipeapp.presentation.screens.recipesScreen

import androidx.lifecycle.ViewModel
import com.abhi41.jetfoodrecipeapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor() : ViewModel() {

    fun applyQueries(): HashMap<String, String> {

        val quries: HashMap<String, String> = HashMap()

        quries[Constants.QUERY_NUMBER] = "50"
        quries[Constants.QUERY_API_KEY] = Constants.API_KEY
        quries[Constants.QUERY_TYPE] = "snack"
        quries[Constants.QUERY_DIET] = "vegan"
        quries[Constants.QUERY_ADD_RECIPE_INFO] = "true"
        quries[Constants.QUERY_FILL_INGREDIENTS] = "true"

        return quries
    }

}