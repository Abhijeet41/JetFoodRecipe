package com.abhi41.jetfoodrecipeapp.presentation.screens.foodJoke

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi41.jetfoodrecipeapp.data.usecase.FoodJokeUsecase
import com.abhi41.jetfoodrecipeapp.utils.Resource
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
class FoodJokeViewModel @Inject constructor(
    private val foodJokeUsecase: FoodJokeUsecase
) : ViewModel() {

    private val _foodJokeState = mutableStateOf(FoodJokeState())
    val foodJokeState: State<FoodJokeState> = _foodJokeState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun getFoodJoke() {
        viewModelScope.launch(Dispatchers.IO) {
            foodJokeUsecase()
                .onEach { result ->

                    when (result) {
                        is Resource.Success -> {
                            _foodJokeState.value = _foodJokeState.value.copy(
                                foodJoke = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _foodJokeState.value = _foodJokeState.value.copy(
                                foodJoke = result.data ?: emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(
                                FoodJokeViewModel.UiEvent.ShowSnackbar(
                                    result.message ?: "Unknown Error"
                                )
                            )
                        }
                        is Resource.Loading -> {
                            withContext(Dispatchers.Main) {
                                _foodJokeState.value = _foodJokeState.value.copy(
                                    foodJoke = result.data ?: emptyList(),
                                    isLoading = true
                                )
                            }
                        }
                    }

                }.launchIn(this)
        }
    }


    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }


}
