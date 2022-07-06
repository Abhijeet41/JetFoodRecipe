@file:OptIn(ExperimentalFoundationApi::class)

package com.abhi41.jetfoodrecipeapp.presentation.screens.favoriteScreen

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.abhi41.foodrecipe.model.Result
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.data.local.entities.FavoriteEntity
import com.abhi41.jetfoodrecipeapp.navigation.Screen
import com.abhi41.jetfoodrecipeapp.presentation.common.BackPressHandler
import com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen.SharedResultViewModel
import com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.DetailViewModel
import com.abhi41.jetfoodrecipeapp.ui.theme.*
import kotlinx.coroutines.launch

private const val TAG = "FavoriteScreen"

var selectedRecipes: MutableList<FavoriteEntity> = mutableListOf()



@Composable
fun FavoriteScreen(
    navController: NavHostController,
    sharedResultViewModel: SharedResultViewModel,
    detailViewModel: DetailViewModel = hiltViewModel(),
) {
    val favoriteRecipe by detailViewModel.readFavoriteRecipes.observeAsState()
    var state = remember {
        mutableStateOf(FavoriteState())
    }
    val coroutineScope = rememberCoroutineScope()


    val onBack = { //handle on back pressed
        //isContextual.value = false
        state.value = state.value.copy(isContextual = false, actionModeTitle = "")
        //actionModeTitle.value = ""
        selectedRecipes.clear()
    }
    if (state.value.isContextual) {
        BackPressHandler(onBackPressed = onBack)
    }

    Scaffold(
        modifier = Modifier.padding(bottom = MEDIUM_PADDING),
        topBar = {
            FavoriteScreenAppbar(
                detailViewModel = detailViewModel,
                state
            ) {
                //On delete Click
                state.value = state.value.copy(isContextual = false, actionModeTitle = "")
                selectedRecipes.forEach { favoriteEntity ->
                    coroutineScope.launch {
                        detailViewModel.deleteFavoriteRecipe(favoriteEntity)
                    }
                }
                selectedRecipes.clear()
            }
        }
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = EXTRA_LARGE_PADDING),
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            if (!favoriteRecipe.isNullOrEmpty()) {

                items(
                    items = favoriteRecipe!!,
                    key = { foodItem ->
                        foodItem.id
                    }
                ) { foodItem ->
                    FoodItem(
                        foodItem,
                        navController,
                        sharedResultViewModel,
                        state
                    )
                }

            }

        }

    }
}


@Composable
fun FoodItem(
    foodItemEntity: FavoriteEntity,
    navController: NavHostController,
    sharedResultViewModel: SharedResultViewModel,
    state: MutableState<FavoriteState>,
) {
    val foodItem = foodItemEntity.result

    var multiSelection by remember { mutableStateOf(false) }
    var selectedItem = remember { mutableStateOf(false) }

    val painter = rememberImagePainter(data = "${foodItem.image}") {
        placeholder(R.drawable.ic_placeholder)
        // crossfade(600)
        error(R.drawable.ic_placeholder)
    }
    //we need to save state because this is a recyclerview it will recycle last selected recipe
    saveItemState(foodItemEntity, selectedItem)
    if (state.value.isContextual) //clear multiple selection on switching screen
    {
        multiSelection = true
    } else {
        multiSelection = false
        selectedRecipes.clear()
    }
    Box(
        modifier = Modifier
            .height(FoodRecipe_ITEM_HEIGHT)
            .border(
                1.dp, if (selectedItem.value) MaterialTheme.colors.strokeBorderColor
                else MaterialTheme.colors.cardStrokeBorder,
                shape = RoundedCornerShape(
                    size = MEDIUM_PADDING
                )
            )
            .combinedClickable(
                onClick = {

                    if (state.value.isContextual || multiSelection) {
                        applicationSelection(
                            currentRecipe = foodItemEntity,
                            selectedItem = selectedItem,
                            state
                        )
                    } else {
                        sharedResultViewModel.addResult(foodItem)
                        navController.navigate(Screen.DetailPage.passRecipeId(recipeId = foodItem.recipeId))
                    }
                },
                onLongClick = {
                    Log.d(TAG, "On long Click")

                    if (!multiSelection) {
                        multiSelection = true
                        state.value = state.value.copy(
                            isContextual = true,
                        )
                        selectedItem.value = true

                        applicationSelection(
                            currentRecipe = foodItemEntity,
                            selectedItem = selectedItem,
                            state
                        )
                        true
                    } else {
                        //multiSelection = false
                        false
                    }

                },
            )

    ) {
        Surface(
            shape = RoundedCornerShape(
                size = MEDIUM_PADDING
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(),
                    painter = painter,
                    contentDescription = "Food Image",
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(EXTRA_SMALL_PADDING)
                ) {
                    Text(
                        text = foodItem.title,
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.titleColor,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.padding(top = 20.dp),
                        text = foodItem.summary,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.descriptionColor,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                    RowLikesAndCategory(foodItem)
                }

            }
        }
    }

}

@Composable
fun RowLikesAndCategory(foodItem: Result) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = SMALL_PADDING),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        InfoColumn(
            R.drawable.ic_heart,
            "${foodItem.aggregateLikes}",
            Color.Red
        )
        InfoColumn(
            R.drawable.ic_clock,
            "${foodItem.readyInMinutes}",
            MaterialTheme.colors.readyInMinute
        )
        InfoColumn(
            R.drawable.ic_leaf,
            if (foodItem.vegan) "Vegan" else "Non vegan",
            Color.Green
        )
    }
}

@Composable
fun InfoColumn(
    @DrawableRes intResource: Int,
    text: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = intResource),
            contentDescription = "heart icon",
            tint = if (text.equals("Non vegan")) Color.Red else color
        )
        Text(
            text = "$text",
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            color = if (text.equals("Non vegan")) Color.Red else color
        )

    }
}


fun applicationSelection(
    currentRecipe: FavoriteEntity,
    selectedItem: MutableState<Boolean>,
    state: MutableState<FavoriteState>,
) {

    if (selectedRecipes.contains(currentRecipe)) {
        selectedRecipes.remove(currentRecipe)
        selectedItem.value = false
        applyActionModeTitle(state)
    } else {
        selectedRecipes.add(currentRecipe)
        selectedItem.value = true
        applyActionModeTitle(state)
    }

}

fun applyActionModeTitle(state: MutableState<FavoriteState>) {
    when (selectedRecipes.size) {
        0 -> {
            state.value = state.value.copy(
                isContextual = false,
                actionModeTitle = ""
            )
        }
        1 -> {
            state.value =
                state.value.copy(actionModeTitle = "${selectedRecipes.size} item selected")
        }
        else -> {
            state.value =
                state.value.copy(actionModeTitle = "${selectedRecipes.size} items selected")
        }
    }
}

private fun saveItemState(
    foodItem: FavoriteEntity,
    selectedItem: MutableState<Boolean>,
) {

    if (selectedRecipes.contains(foodItem)) {
        selectedItem.value = true
    } else {
        selectedItem.value = false
    }


}




