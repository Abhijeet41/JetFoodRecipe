package com.abhi41.jetfoodrecipeapp.presentation.screens.foodJoke

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.abhi41.foodrecipe.model.FoodJoke
import com.abhi41.jetfoodrecipeapp.data.local.LocalDataSource
import com.abhi41.jetfoodrecipeapp.data.local.database.RecipesDatabase
import com.abhi41.jetfoodrecipeapp.data.local.entities.FoodJokeEntity
import com.abhi41.jetfoodrecipeapp.data.network.RemoteDataSource
import com.abhi41.jetfoodrecipeapp.utils.Constants
import com.abhi41.jetfoodrecipeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class FoodJokeViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    @ApplicationContext context: Context
) : ViewModel() {

    val foodJokeResponse: MutableLiveData<Resource<FoodJoke>> = MutableLiveData()
    val path = context.getDatabasePath(Constants.DATABASE_NAME)

    val readFoodJoke: LiveData<List<FoodJokeEntity>> = localDataSource.readFoodJoke().asLiveData()

    fun requestFoodJoke(queryApi: String) {

        viewModelScope.launch {
            getFoodJokeSafeCall(queryApi)
        }
    }


    private suspend fun getFoodJokeSafeCall(apiKey: String) {
        foodJokeResponse.value = Resource.Loading()

        try {
            val response = remoteDataSource.getFoodJoke(apiKey)
            Log.d("foodJokeResp",response.toString())
            foodJokeResponse.value = handleFoodJokeResponse(response = response)

            //caching for foodjoke
            val foodJoke = foodJokeResponse.value?.data
            if (foodJoke != null) {
                offlineCacheFoodJoke(foodJoke)
            }
        } catch (e: Exception) {
            foodJokeResponse.value = Resource.Error("No Joke Found")
        }

    }

    private fun offlineCacheFoodJoke(foodJoke: FoodJoke) {
        val foodJokeEntity = FoodJokeEntity(foodJoke)
        insertFoodJoke(foodJokeEntity)
    }

    private fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            localDataSource.insertFoodJoke(foodJokeEntity)
        }
    }

    private fun handleFoodJokeResponse(
        response: Response<FoodJoke>
    ): Resource<FoodJoke> {
        when {
            response.message().toString().contains("timeout") -> {
                return Resource.Error("Connection Timeout!")
            }
            response.code() == 420 -> {
                return Resource.Error("Api Key Limited.")
            }
            response.isSuccessful -> {
                val foodJoke = response.body()
                return Resource.Success(foodJoke!!)
            }

            else -> {
                return Resource.Error(response.message())
            }
        }
    }

}