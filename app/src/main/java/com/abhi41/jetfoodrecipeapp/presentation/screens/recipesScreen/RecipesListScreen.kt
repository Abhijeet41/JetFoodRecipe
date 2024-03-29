package com.abhi41.jetfoodrecipeapp.presentation.screens.recipesScreen


import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.data.repository.MealAndDietType
import com.abhi41.jetfoodrecipeapp.model.Result
import com.abhi41.jetfoodrecipeapp.presentation.common.RecipesListContent
import com.abhi41.jetfoodrecipeapp.presentation.common.chip.*
import com.abhi41.jetfoodrecipeapp.presentation.destinations.SearchScreenDestination
import com.abhi41.jetfoodrecipeapp.ui.theme.*
import com.abhi41.jetfoodrecipeapp.utils.AnimatedShimmer
import com.abhi41.jetfoodrecipeapp.utils.newtwork_status.ConnectivityObserver
import com.abhi41.jetfoodrecipeapp.utils.newtwork_status.NetworkConnectivityObserver
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.riegersan.composeexperiments.DietTypeChipGroup
import com.riegersan.composeexperiments.MealTypeChipGroup
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


private const val TAG = "RecipesListScreen"


@Destination
@Composable
fun RecipesScreen(
    navigator: DestinationsNavigator,
) {

    val viewModel: RecipesViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is RecipesViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }

    }
    RecipeDesign(navigator = navigator)
}

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipeDesign(
    navigator: DestinationsNavigator,
) {
    val coroutineScope = rememberCoroutineScope()

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = {
            it != ModalBottomSheetValue.HalfExpanded
        }
    )

    Scaffold(
        modifier = Modifier
            .padding(bottom = 20.dp),
        topBar = {
            RecipesTopBar {
                navigator.navigate(SearchScreenDestination())
            }
        },
        content = {
            BottomSheet(
                modalBottomSheetState,
                navigator,
            )
        },
        floatingActionButton = {
            if (!modalBottomSheetState.isVisible) {
                FloatingActionButton(
                    modifier = Modifier.offset(x = -10.dp, y = -60.dp),
                    onClick = {
                        coroutineScope.launch {
                            if (modalBottomSheetState.isVisible) {
                                modalBottomSheetState.hide()
                            } else {
                                modalBottomSheetState.show()
                            }
                        }
                    },
                    backgroundColor = colorResource(id = R.color.green),
                    contentColor = White
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_restaurant_24),
                        contentDescription = "restaurant icon "
                    )
                }
            }
        },
    )
}


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    bottomSheetScaffoldState: ModalBottomSheetState,
    navigator: DestinationsNavigator,
) {
    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheetScreen(
                bottomSheetScaffoldState = bottomSheetScaffoldState
            )
        },
        sheetState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        val viewModel: RecipesViewModel = hiltViewModel()
        val state = viewModel.recipesState.value

        val context = LocalContext.current
        connectivityObserver = NetworkConnectivityObserver(context)

        val status by connectivityObserver.observe().collectAsState(
            initial = null
        )
        LaunchedEffect(key1 = status) {
            if (status != null) {
                Toast.makeText(context, "Network Status: $status", Toast.LENGTH_SHORT).show()
            }
        }

        if (!state.isLoading) {
            RecipesListContent(
                foodRecipes = state.recipesItem,
                navigator = navigator
            )
        } else {
            AnimatedShimmer()
        }

    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomSheetScreen(
    bottomSheetScaffoldState: ModalBottomSheetState,
) {
    lateinit var mealAndDiet: MealAndDietType
    val coroutineScope = rememberCoroutineScope()
    val recipeViewmodel: RecipesViewModel = hiltViewModel()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(BOTTOMSHEET_HEIGHT)
            .padding(bottom = MEDIUM_PADDING)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = SMALL_PADDING),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.padding(
                    top = LARGE_PADDING,
                    start = LARGE_PADDING
                ),
                text = "Meal Type",
                color = White,
                fontSize = TXT_MEDIUM_SIZE,
                fontWeight = FontWeight.Bold
            )
            MealTypeChipGroup(
                meals = MealType.getMeals(),
                selectedMeal = recipeViewmodel.selectedMealType.value
            ) { changedSelection ->
                Log.d("mealChip", changedSelection)
                recipeViewmodel.selectedMealType.value = Meal(changedSelection)
            }

            Text(
                modifier = Modifier.padding(
                    top = LARGE_PADDING,
                    start = LARGE_PADDING
                ),
                text = "Diet Type",
                color = White,
                fontSize = TXT_MEDIUM_SIZE,
                fontWeight = FontWeight.Bold
            )

            DietTypeChipGroup(
                meals = DietType.getDiets(),
                selectedMeal = recipeViewmodel.selectedDietType.value
            ) { changeSelection ->
                Log.d("dietChip", changeSelection)
                recipeViewmodel.selectedDietType.value = Diet(changeSelection)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(BUTTON_HEIGHT)
                        .padding(MEDIUM_PADDING),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.buttonColor),
                    shape = RoundedCornerShape(50),
                    onClick = {

                        mealAndDiet = MealAndDietType(
                            recipeViewmodel.selectedMealType.value.meal,
                            recipeViewmodel.selectedDietType.value.diet
                        )
                        recipeViewmodel.getRecipes(
                            mealAndDiet.selectedMealType,
                            mealAndDiet.selectedDietType
                        )

                        coroutineScope.launch {
                            bottomSheetScaffoldState.hide()
                        }

                    }) {
                    Text(
                        text = "Apply",
                        color = White,
                        textAlign = TextAlign.Center
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun RecipeDesignPreview() {
    RecipesListContent(
        foodRecipes = emptyList(),
        navigator = EmptyDestinationsNavigator
    )
}


