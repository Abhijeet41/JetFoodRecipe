package com.abhi41.jetfoodrecipeapp.presentation.screens.recipesScreen


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asLiveData
import androidx.navigation.NavHostController
import com.abhi41.foodrecipe.model.Result
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.data.repository.MealAndDietType
import com.abhi41.jetfoodrecipeapp.navigation.Screen
import com.abhi41.jetfoodrecipeapp.presentation.common.RecipesListContent
import com.abhi41.jetfoodrecipeapp.presentation.common.chip.*
import com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen.DashBoardViewModel
import com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen.SharedResultViewModel
import com.abhi41.jetfoodrecipeapp.ui.theme.*
import com.abhi41.jetfoodrecipeapp.utils.AnimatedShimmer
import com.abhi41.jetfoodrecipeapp.utils.Constants
import com.abhi41.jetfoodrecipeapp.utils.Resource
import com.riegersan.composeexperiments.DietTypeChipGroup
import com.riegersan.composeexperiments.MealTypeChipGroup
import kotlinx.coroutines.launch


private const val TAG = "RecipesListScreen"
private var foodRecipes = emptyList<Result>()

@Composable
fun RecipesScreen(
    navController: NavHostController,
    sharedResultViewModel: SharedResultViewModel,
    recipesViewModel: RecipesViewModel = hiltViewModel(),
    dashBoardViewModel: DashBoardViewModel = hiltViewModel()
) {

    val readRecipes by dashBoardViewModel.readRecipes.observeAsState()

    if (!readRecipes.isNullOrEmpty()) {
        foodRecipes = readRecipes as List<Result>

        RecipeDesign(
            navController = navController,
            recipesViewModel = recipesViewModel,
            dashBoardViewModel = dashBoardViewModel,
            sharedResultViewModel = sharedResultViewModel
        )
    } else {
        requestApiData(
            recipesViewModel = recipesViewModel,
            dashBoardViewModel = dashBoardViewModel
        )
    }

    observers(dashBoardViewModel)

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipeDesign(
    navController: NavHostController,
    recipesViewModel: RecipesViewModel,
    dashBoardViewModel: DashBoardViewModel,
    sharedResultViewModel: SharedResultViewModel,
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
                //onSearch icon clicked navigate to search screen
                navController.navigate(Screen.SearchPage.route)
            }
        },
        content = {
            BottomSheet(
                modalBottomSheetState,
                navController,
                recipesViewModel,
                dashBoardViewModel,
                sharedResultViewModel
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


fun requestApiData(
    recipesViewModel: RecipesViewModel,
    dashBoardViewModel: DashBoardViewModel
) {
    dashBoardViewModel.getRecipes(
        recipesViewModel.applyQueries(
            Constants.DEFAULT_MEAL_TYPE,
            Constants.DEFAULT_DIET_TYPE
        )
    )
}

@Composable
fun observers(
    dashBoardViewModel: DashBoardViewModel,
) {
    val response by dashBoardViewModel.recipesResponse.observeAsState()
    val context = LocalContext.current

    when (response) {
        is Resource.Success -> {
            //   hideShimmerEffect()
            foodRecipes = response?.data?.results!!
        }

        is Resource.Error -> {
            //   hideShimmerEffect()
            ///  loadDataFromCache()
            Toast.makeText(
                context,
                response?.message.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

        is Resource.Loading -> {
            AnimatedShimmer()
        }
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    bottomSheetScaffoldState: ModalBottomSheetState,
    navController: NavHostController,
    recipesViewModel: RecipesViewModel,
    dashBoardViewModel: DashBoardViewModel,
    sharedResultViewModel: SharedResultViewModel,
) {
    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheetScreen(
                recipesViewModel = recipesViewModel,
                dashBoardViewModel = dashBoardViewModel,
                bottomSheetScaffoldState = bottomSheetScaffoldState
            )
        },
        sheetState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {

        if (!foodRecipes.isNullOrEmpty()) {
            RecipesListContent(foodRecipes, navController,sharedResultViewModel)
        } else {
            //show error screen
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomSheetScreen(
    recipesViewModel: RecipesViewModel,
    dashBoardViewModel: DashBoardViewModel,
    bottomSheetScaffoldState: ModalBottomSheetState,
) {
    //read data preference from repository class
    var readMealAndDietType = recipesViewModel.readMealAndDietType.asLiveData()
    lateinit var mealAndDiet: MealAndDietType

    Log.d("selectedMealType", readMealAndDietType.value?.selectedMealType ?: "null")
    Log.d("selectedDietType", readMealAndDietType.value?.selectedDietType ?: "null")
    val coroutineScope = rememberCoroutineScope()

    var selectedMealType = remember {
        mutableStateOf(MealType.getMeals().get(0))
    }
    var selectedDietType = remember {
        mutableStateOf(DietType.getDiets().get(0))
    }

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
                selectedMeal = selectedMealType.value
            ) { changedSelection ->
                Log.d("changedSelection", changedSelection)
                selectedMealType.value = Meal(changedSelection)
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
                selectedMeal = selectedDietType.value,
            ) { changeSelection ->
                selectedDietType.value = Diet(changeSelection)
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
                        selectedMealType.value.meal,
                        selectedDietType.value.diet
                        )
                    /*    Log.d("selectedMealType", "${selectedMealType.value}")
                        Log.d("selectedDietType", "${selectedDietType.value}")*/
                        recipesViewModel.saveMealAndDietType( //save to datastore
                            mealAndDiet.selectedMealType,
                            mealAndDiet.selectedMealType
                        )

                        dashBoardViewModel.getRecipes(
                            recipesViewModel.applyQueries(
                                mealAndDiet.selectedMealType,
                                mealAndDiet.selectedMealType
                            )
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
    RecipeDesign(
        navController = NavHostController(LocalContext.current),
        recipesViewModel = hiltViewModel(),
        dashBoardViewModel = hiltViewModel(),
        sharedResultViewModel = SharedResultViewModel()
    )

}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun BottomSheetScreenPreview() {
    BottomSheetScreen(
        recipesViewModel = hiltViewModel(),
        dashBoardViewModel = hiltViewModel(),
        bottomSheetScaffoldState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    )
}
