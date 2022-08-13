package com.abhi41.jetfoodrecipeapp.presentation.screens.recipesScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi41.jetfoodrecipeapp.data.repository.MealAndDietType
import com.abhi41.jetfoodrecipeapp.data.repository.MealDataStoreRepository
import com.abhi41.jetfoodrecipeapp.data.usecase.RecipesUsecase
import com.abhi41.jetfoodrecipeapp.presentation.common.chip.Diet
import com.abhi41.jetfoodrecipeapp.presentation.common.chip.DietType
import com.abhi41.jetfoodrecipeapp.presentation.common.chip.Meal
import com.abhi41.jetfoodrecipeapp.presentation.common.chip.MealType
import com.abhi41.jetfoodrecipeapp.utils.Constants
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
class RecipesViewModel @Inject constructor(
    private val getRecipesUsecase: RecipesUsecase,
    private val dataStoreRepository: MealDataStoreRepository,
) : ViewModel() {

    private lateinit var mealAndDiet: MealAndDietType

    //read data preferences from repository class
    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    var selectedMealType = mutableStateOf(MealType.getMeals().get(0))
    var selectedDietType = mutableStateOf(DietType.getDiets().get(0))

    private val _recipesState = mutableStateOf(RecipesState())
    val recipesState: State<RecipesState> = _recipesState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            readMealAndDietType.collect { state ->
                withContext(Dispatchers.Main) {
                    selectedMealType.value = Meal(state.selectedMealType)
                    selectedDietType.value = Diet(state.selectedDietType)

                    Log.d("selectedMealType", state.selectedMealType)
                    Log.d("selectedDietType", state.selectedDietType)
                }
            }
        }
        getRecipes()
    }

    //save meal data datastore preferences
    fun saveMealAndDietType(
        mealType: String,
        dietType: String,
    ) = viewModelScope.launch(Dispatchers.IO) {

        mealAndDiet = MealAndDietType(
            mealType,
            dietType
        )
        dataStoreRepository.saveMealAndDietType(mealAndDiet)
    }

    fun getRecipes(
        selectedMealType: String = Constants.DEFAULT_MEAL_TYPE,
        selectedDietType: String = Constants.DEFAULT_DIET_TYPE
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            getRecipesUsecase(
                queries = applyQuries(
                    mealType = selectedMealType,
                    dietType = selectedDietType
                )
            )
                .onEach { result ->

                    when (result) {
                        is Resource.Success -> {
                            _recipesState.value = _recipesState.value.copy(
                                recipesItem = result.data ?: emptyList(),
                                isLoading = false
                            )
                            saveMealAndDietType(
                                selectedMealType,
                                selectedDietType
                            )
                        }
                        is Resource.Error -> {
                            Log.d("errorMsg", result.message ?: "")
                            _recipesState.value = _recipesState.value.copy(
                                recipesItem = result.data ?: emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    result.message ?: "Unknown Error"
                                )
                            )
                        }
                        is Resource.Loading -> {
                            withContext(Dispatchers.Main) {
                                _recipesState.value = _recipesState.value.copy(
                                    recipesItem = result.data ?: emptyList(),
                                    isLoading = true
                                )
                            }

                        }
                    }

                }.launchIn(this)

        }

    }

    fun applyQuries(
        mealType: String,
        dietType: String,
    ): HashMap<String, String> {
        val quries: HashMap<String, String> = HashMap()

        quries[Constants.QUERY_NUMBER] = "50"
        quries[Constants.QUERY_API_KEY] = Constants.API_KEY
        quries[Constants.QUERY_TYPE] = mealType
        quries[Constants.QUERY_DIET] = dietType
        quries[Constants.QUERY_ADD_RECIPE_INFO] = "true"
        quries[Constants.QUERY_FILL_INGREDIENTS] = "true"
        Log.d("VM_mealType", mealType)
        Log.d("VM_dietType", dietType)

        return quries
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }

}