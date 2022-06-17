package com.abhi41.jetfoodrecipeapp.presentation.screens.foodJoke

import android.util.Log
import androidx.lifecycle.*
import com.abhi41.foodrecipe.model.FoodJoke
import com.abhi41.jetfoodrecipeapp.data.local.LocalDataSource
import com.abhi41.jetfoodrecipeapp.data.local.entities.FoodJokeEntity
import com.abhi41.jetfoodrecipeapp.data.network.RemoteDataSource
import com.abhi41.jetfoodrecipeapp.utils.Constants
import com.abhi41.jetfoodrecipeapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FoodJokeViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ViewModel() {

    val foodJokeResponse: MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()

    val readFoodJoke: LiveData<List<FoodJokeEntity>> = localDataSource.readFoodJoke().asLiveData()


    fun requestFoodJoke(queryApi: String) = viewModelScope.launch {
        getFoodJokeSafeCall(queryApi)
    }


    private suspend fun getFoodJokeSafeCall(apiKey: String) {
        foodJokeResponse.value = NetworkResult.Loading()

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
            foodJokeResponse.value = NetworkResult.Error("No Joke Found")
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
    ): NetworkResult<FoodJoke> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Connection Timeout!")
            }
            response.code() == 420 -> {
                return NetworkResult.Error("Api Key Limited.")
            }
            response.isSuccessful -> {
                val foodJoke = response.body()
                return NetworkResult.Success(foodJoke!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

}