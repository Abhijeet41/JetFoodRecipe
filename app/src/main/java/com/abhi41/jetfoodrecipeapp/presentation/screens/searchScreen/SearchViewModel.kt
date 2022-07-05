package com.abhi41.jetfoodrecipeapp.presentation.screens.searchScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.abhi41.jetfoodrecipeapp.utils.Constants
import com.abhi41.jetfoodrecipeapp.utils.Constants.DEFAULT_RECIPES_NUMBER
import javax.inject.Inject

class SearchViewModel @Inject constructor(

): ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery


    fun updateSearchQuery( query: String ){
        _searchQuery.value = query
    }

    //search quries
    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[Constants.QUERY_SEARCH] = searchQuery
        queries[Constants.QUERY_NUMBER] = Constants.DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_ADD_RECIPE_INFO] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }


}