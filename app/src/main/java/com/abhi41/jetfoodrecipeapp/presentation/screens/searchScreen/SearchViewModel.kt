package com.abhi41.jetfoodrecipeapp.presentation.screens.searchScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi41.jetfoodrecipeapp.data.usecase.SearchResultUseCase
import com.abhi41.util.Constants
import com.abhi41.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchResultUseCase: SearchResultUseCase
) : ViewModel() {

    private val _searchState = mutableStateOf(SearchState())
    val searchState: State<SearchState> = _searchState

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getSearchRecipes(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchResultUseCase(queries = applySearchQuery(searchQuery = searchQuery))
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _searchState.value = _searchState.value.copy(
                                result = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            Log.d("SearchError",result.message.toString())

                            _searchState.value = _searchState.value.copy(
                                result = result.data ?: emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(
                                withContext(Dispatchers.Main){
                                    UiEvent.ShowSnackbar(
                                        result.message ?: "Unknown Error"
                                    )
                                }
                            )
                        }
                        is Resource.Loading -> {
                            _searchState.value = _searchState.value.copy(
                                result = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }
    }


    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[Constants.QUERY_SEARCH] = searchQuery
        queries[Constants.QUERY_NUMBER] = Constants.DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_ADD_RECIPE_INFO] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun cleareSearchList() { //clear data when clicked on search trailing close button
        _searchState.value = _searchState.value.copy(
            result = emptyList()
        )
    }
    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }

}