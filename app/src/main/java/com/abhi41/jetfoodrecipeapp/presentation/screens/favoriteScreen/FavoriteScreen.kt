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
import org.jsoup.Jsoup

private const val TAG = "FavoriteScreen"

var selectedRecipes: MutableList<FavoriteEntity> = mutableListOf()
var state: MutableState<FavoriteState> = mutableStateOf(FavoriteState())

@Composable
fun FavoriteScreen(
    navController: NavHostController,
    sharedResultViewModel: SharedResultViewModel,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val favoriteRecipe by detailViewModel.readFavoriteRecipes.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    val favoriteState = state.value

    val onBack = { //handle on back pressed

        state.value = favoriteState.copy(
            isContextual = false,
            actionModeTitle = ""
        )
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
                state = state
            ) {
                //On delete Click

                selectedRecipes.forEach { favoriteEntity ->
                    coroutineScope.launch {
                        detailViewModel.deleteFavoriteRecipe(favoriteEntity)
                    }
                }
                state.value = state.value.copy(
                    isContextual = false,
                    actionModeTitle = ""
                )
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
                        foodItemEntity = foodItem,
                        navController = navController,
                        sharedResultViewModel = sharedResultViewModel,
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
) {
    val foodItem = foodItemEntity.result
    val favoriteState = state.value

    val painter = rememberImagePainter(data = "${foodItem.image}") {
        placeholder(R.drawable.ic_placeholder)
        // crossfade(600)
        error(R.drawable.ic_placeholder)
    }
    //we need to save state because this is a recyclerview it will recycle last selected recipe
    saveItemState(foodItemEntity)
    if (favoriteState.isContextual) //clear multiple selection on backpress
    {
        state.value = favoriteState.copy(multiSelection = true)
    } else {
        state.value = favoriteState.copy(multiSelection = false)
    }
    Box(
        modifier = Modifier
            .height(FoodRecipe_ITEM_HEIGHT)
            .border(
                1.dp, if (state.value.selectedItem) MaterialTheme.colors.strokeBorderColor
                else MaterialTheme.colors.cardStrokeBorder,
                shape = RoundedCornerShape(
                    size = MEDIUM_PADDING
                )
            )
            .combinedClickable(
                onClick = {
                    if (state.value.isContextual || favoriteState.multiSelection) {
                        applicationSelection(
                            currentRecipe = foodItemEntity,
                        )
                    } else {
                        sharedResultViewModel.addResult(foodItem)
                        navController.navigate(Screen.DetailPage.passRecipeId(recipeId = foodItem.recipeId))
                    }
                },
                onLongClick = {
                    Log.d(TAG, "On long Click")

                    if (!favoriteState.multiSelection) {
                        state.value = favoriteState.copy(
                            multiSelection = true,
                            isContextual = true,
                            selectedItem = true
                        )
                        applicationSelection(
                            currentRecipe = foodItemEntity,
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
                        text = Jsoup.parse(foodItem.summary).text(),
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
) {

    if (selectedRecipes.contains(currentRecipe)) {
        selectedRecipes.remove(currentRecipe)
        state.value = state.value.copy(selectedItem = false)
        applyActionModeTitle()
    } else {
        selectedRecipes.add(currentRecipe)
        state.value = state.value.copy(selectedItem = true)
        applyActionModeTitle()
    }

}

fun applyActionModeTitle() {
    when (selectedRecipes.size) {
        0 -> {
            state.value = state.value.copy(isContextual = false)
            state.value = state.value.copy(actionModeTitle = "")
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
) {
    if (selectedRecipes.contains(foodItem)) {
        state.value = state.value.copy(selectedItem = true)
    } else {
        state.value = state.value.copy(selectedItem = false)
    }
}




