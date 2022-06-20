package com.abhi41.jetfoodrecipeapp.presentation.screens.recipesScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi41.jetfoodrecipeapp.data.repository.MealAndDietType
import com.abhi41.jetfoodrecipeapp.data.repository.MealDataStoreRepository
import com.abhi41.jetfoodrecipeapp.presentation.common.chip.Diet
import com.abhi41.jetfoodrecipeapp.presentation.common.chip.Meal
import com.abhi41.jetfoodrecipeapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val dataStoreRepository: MealDataStoreRepository
) : ViewModel() {


    //read data preferences from repository class
    val readMealAndDietType = dataStoreRepository.readMealAndDietType


    //save meal data datastore preferences
    fun saveMealAndDietType(
        mealType: Meal,
        dietType: Diet,
        ) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveMealAndDietType(mealType.meal, dietType.diet)
    }


    //recipes queries
    fun applyQueries(mealType: String, dietType: String): HashMap<String, String> {
        val quries: HashMap<String, String> = HashMap()

        quries[Constants.QUERY_NUMBER] = "50"
        quries[Constants.QUERY_API_KEY] = Constants.API_KEY
        quries[Constants.QUERY_TYPE] = mealType
        quries[Constants.QUERY_DIET] = dietType
        quries[Constants.QUERY_ADD_RECIPE_INFO] = "true"
        quries[Constants.QUERY_FILL_INGREDIENTS] = "true"
        Log.d("Viewmodel mealType", mealType)
        Log.d("Viewmodel dietType", dietType)

        return quries

    }


}

