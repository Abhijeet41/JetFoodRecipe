package com.abhi41.jetfoodrecipeapp.presentation.screens.recipesScreen


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.data.repository.MealAndDietType
import com.abhi41.jetfoodrecipeapp.presentation.common.chip.*
import com.abhi41.jetfoodrecipeapp.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.riegersan.composeexperiments.DietTypeChipGroup
import com.riegersan.composeexperiments.MealTypeChipGroup
import kotlinx.coroutines.launch


private const val TAG = "RecipesListScreen"


@Destination
@Composable
fun RecipesScreen(
    navigator: DestinationsNavigator,
) {


}

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
                selectedMeal = null
            ) { changedSelection ->
                Log.d("changedSelection", changedSelection)
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
                selectedMeal = null
            ) { changeSelection ->

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
    /*  RecipeDesign(
          navigator = DestinationsNavigator,
          recipesViewModel = hiltViewModel(),
          dashBoardViewModel = hiltViewModel(),
      )*/

}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun BottomSheetScreenPreview() {
    BottomSheetScreen(
        bottomSheetScaffoldState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    )
}
