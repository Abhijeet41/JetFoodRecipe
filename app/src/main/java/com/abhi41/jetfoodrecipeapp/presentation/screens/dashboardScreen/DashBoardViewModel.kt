package com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen

import android.app.Application
import androidx.lifecycle.*
import com.abhi41.foodrecipe.model.FoodRecipe
import com.abhi41.jetfoodrecipeapp.data.local.entities.RecipesEntity
import com.abhi41.jetfoodrecipeapp.data.repository.RecipesRepository
import com.abhi41.jetfoodrecipeapp.presentation.common.HandleResponse
import com.abhi41.jetfoodrecipeapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val repository: RecipesRepository,
) : ViewModel() {
    //read data from remote
    var _recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    val recipesResponse = _recipesResponse

    var _searchedRecipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    val searchedRecipesResponse = _searchedRecipesResponse

    //read data from database
    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()


    fun getRecipes(quries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(quries)
    }

    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }

    //for recipes screen
    private suspend fun getRecipesSafeCall(quries: Map<String, String>) {

        _recipesResponse.value = NetworkResult.Loading()//for show progressbar

        try {
            val response = repository.remote.getRecipes(quries = quries)
            _recipesResponse.value = HandleResponse.handleFoodRecipesResponse(response)

            /*---------- Caching FoodRecipes------------- */

            val foodRecipe = _recipesResponse.value?.data
            if (foodRecipe != null) {
                offlineCacheRecipes(foodRecipe)
            }

        } catch (e: Exception) {
            _recipesResponse.value = NetworkResult.Error("Recipes not found")
        }

    }

    //for search screen
    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        _searchedRecipesResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.searchRecipes(quries = searchQuery)
            _searchedRecipesResponse.value = HandleResponse.handleFoodRecipesResponse(response)
        } catch (e: Exception) {
            _searchedRecipesResponse.value = NetworkResult.Error("recipes not found")
        }

    }

     fun cleareSearchList(){ //clear data when clicked on search trailing close button
        _searchedRecipesResponse.value = null
    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe = foodRecipe)
        insertRecipes(recipesEntity)
    }

    private fun insertRecipes(recipesEntity: RecipesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity = recipesEntity)
        }
    }



}