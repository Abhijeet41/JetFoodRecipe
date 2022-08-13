package com.abhi41.jetfoodrecipeapp.data.remote

import android.content.Context
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.data.dto.FoodJokeDto
import com.abhi41.jetfoodrecipeapp.data.dto.FoodRecipeDto
import com.abhi41.jetfoodrecipeapp.data.dto.ResultDto
import com.abhi41.jetfoodrecipeapp.data.local.dao.RecipesDao
import com.abhi41.jetfoodrecipeapp.domain.RecipesRepository
import com.abhi41.jetfoodrecipeapp.model.FoodJoke
import com.abhi41.jetfoodrecipeapp.model.Result
import com.abhi41.jetfoodrecipeapp.utils.Constants
import com.abhi41.jetfoodrecipeapp.utils.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RecipesRepositoryImpl (
    private val api: FoodRecipesApi,
    private val dao: RecipesDao,
    private val context: Context
) : RecipesRepository {

    override fun getRecipes(queries: Map<String, String>): Flow<Resource<List<Result>>> = flow {

        emit(Resource.Loading())
        val recipes: List<Result> = dao.readRecipes().map { it.toRecipes() }
        emit(Resource.Loading(data = recipes))

        //make api request and insert api response data into the database
        try {
            val response = api.getRecipies(queries).body()
            val code = api.getRecipies(queries).code()
            val message = api.getRecipies(queries).message()
            if (code == 200) {
                val remoteRecipes: List<ResultDto> = response?.results ?: emptyList()
                dao.deleteAllRecipes()
                dao.insertRecipes(remoteRecipes.map { it.toResult() })
            } else if (code == 402) {
                emit(Resource.Error(message = context.getString(R.string.api_key_limited), data = recipes))
            } else if (message.contains("timeout")) {
                emit(Resource.Error(message = context.getString(R.string.connection_timeout), data = recipes))
            }

        } catch (e: HttpException) {
            emit(Resource.Error(message = context.getString(R.string.something_wrong), data = recipes))
        } catch (e: IOException) {
            emit(Resource.Error(message = context.getString(R.string.check_internet_connection),
                data = recipes))
        }

        //emit data to ui ()
        val newRecipes = dao.readRecipes().map { it.toRecipes() }
        emit(Resource.Success(newRecipes))
    }

    override fun getFoodJokes(): Flow<Resource<List<FoodJoke>>> = flow {

        emit(Resource.Loading())
        val foodJokes: List<FoodJoke> = dao.readFoodJoke().map { it.toFoodJoke() }
        emit(Resource.Loading(data = foodJokes))

        //make api request
        try {
            val remoteFoodJokes: FoodJokeDto? = api.getFoodJoke(apiKey = Constants.API_KEY).body()
            if (remoteFoodJokes != null) {
                dao.deleteAllFoodJoke()
                dao.insertFoodJoke(remoteFoodJokes.toFoodJoke())
            }
        } catch (e: HttpException) {
            emit(Resource.Error(message = context.getString(R.string.something_wrong), data = foodJokes))
        } catch (e: IOException) {
            emit(Resource.Error(message = context.getString(R.string.check_internet_connection),
                data = foodJokes))
        }
        //emit data to the ui
        val newJoke = dao.readFoodJoke().map { it.toFoodJoke() }
        emit(Resource.Success(newJoke))
    }

    override fun getSearchRecipes(queries: Map<String, String>)
            : Flow<Resource<List<Result>>> = flow {

        emit(Resource.Loading())
        try {
            //here we don't need database caching
            val response = api.searchRecipes(queries).body()
            val remoteRecipes: List<ResultDto> = response?.results ?: emptyList()
            val recipes: List<Result> = remoteRecipes.map { it.toSearchResult() }
            //hence emit data directly to ui
            emit(Resource.Loading(data = recipes))
            emit(Resource.Success(recipes))
        } catch (e: HttpException) {
            emit(Resource.Error(message = context.getString(R.string.something_wrong), data = emptyList()))
        } catch (e: IOException) {
            emit(Resource.Error(message = context.getString(R.string.check_internet_connection),
                data = emptyList()))
        }

    }

}