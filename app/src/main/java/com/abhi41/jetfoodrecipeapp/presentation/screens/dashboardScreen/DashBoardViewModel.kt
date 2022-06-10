package com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen

import android.app.Application
import androidx.lifecycle.*
import com.abhi41.foodrecipe.model.FoodRecipe
import com.abhi41.jetfoodrecipeapp.data.local.entities.RecipesEntity
import com.abhi41.jetfoodrecipeapp.data.repository.RecipesRepository
import com.abhi41.jetfoodrecipeapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val repository: RecipesRepository,
    application: Application,
) : ViewModel() {

    var _recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    val recipesResponse = _recipesResponse
    //read data from database
    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()


    fun getRecipes(quries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(quries)
    }

    private suspend fun getRecipesSafeCall(quries: Map<String, String>) {

        _recipesResponse.value = NetworkResult.Loading()//for show progressbar

        try {
            val response = repository.remote.getRecipes(quries = quries)
            _recipesResponse.value = handleFoodRecipesResponse(response)

            /*---------- Caching FoodRecipes------------- */

            val foodRecipe = _recipesResponse.value?.data
            if (foodRecipe != null){
                offlineCacheRecipes(foodRecipe)
            }

        } catch (e: Exception) {
            _recipesResponse.value = NetworkResult.Error("Recipes not found")
        }

    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe = foodRecipe)
        insertRecipes(recipesEntity)
    }

    private fun insertRecipes(recipesEntity: RecipesEntity) {
        viewModelScope.launch (Dispatchers.IO){
            repository.local.insertRecipes(recipesEntity = recipesEntity)
        }
    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when {
            response.message().contains("timeout") -> {
                return NetworkResult.Error("Connection TimeOut")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("Api Key Limited.")
            }

            response.body()?.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found")
            }

            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }

        }
    }

}