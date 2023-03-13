package com.abhi41.jetfoodrecipeapp.presentation.screens.favorite

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
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
import coil.compose.rememberImagePainter
import com.abhi41.jetfoodrecipeapp.R.drawable
import com.abhi41.jetfoodrecipeapp.data.local.entity.FavoriteEntity
import com.abhi41.jetfoodrecipeapp.model.Result
import com.abhi41.jetfoodrecipeapp.presentation.common.BackPressHandler
import com.abhi41.jetfoodrecipeapp.presentation.destinations.DetailScreenDestination
import com.abhi41.jetfoodrecipeapp.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

private const val TAG = "FavoriteScreen"
var selectedRecipes: MutableList<FavoriteEntity> = mutableListOf()

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun FavoriteScreen(
    navigator: DestinationsNavigator,
    viewModel: FavoriteViewModel = hiltViewModel(),
) {

    val favoriteRecipe by viewModel.readFavoriteRecipes.observeAsState()
    var state = remember { mutableStateOf(FavoriteState()) }
    val coroutineScope = rememberCoroutineScope()

    val onBack = { //handle on back pressed
        state.value = state.value.copy(
            isContextual = false,
            actionModeTitle = ""
        )
        selectedRecipes.clear()
    }
    if (state.value.isContextual) {
        BackPressHandler(onBackPressed = onBack)
    }

    Scaffold(modifier = Modifier.padding(bottom = MEDIUM_PADDING),
        topBar = {
            FavoriteScreenAppbar(
                viewModel = viewModel,
                state = state
            ) { //On delete Click
                state.value = state.value.copy(
                    isContextual = false,
                    actionModeTitle = ""
                )
                selectedRecipes.forEach { favoriteEntity ->
                    coroutineScope.launch {
                        viewModel.deleteFavoriteRecipe(favoriteEntity)
                    }
                }
                selectedRecipes.clear()
            }
        }) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = EXTRA_LARGE_PADDING),
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            if (!favoriteRecipe.isNullOrEmpty()) {
                items(items = favoriteRecipe!!,
                    key = { foodItem ->
                        foodItem.id
                    }) { foodItem ->
                    FoodItem(
                        foodItem, navigator, state
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoodItem(
    foodItemEntity: FavoriteEntity,
    navigator: DestinationsNavigator,
    state: MutableState<FavoriteState>,
) {
    val foodItem = foodItemEntity.result

    val painter = rememberImagePainter(data = "${foodItem.image}") {
        placeholder(drawable.ic_placeholder)
        // crossfade(600)
        error(drawable.ic_placeholder)
    }
    //we need to save state because this is a recyclerview it will recycle last selected recipe
    saveItemState(
        foodItemEntity,
        state
    )
    if (state.value.isContextual) //clear multiple selection on switching screen
    {
        state.value = state.value.copy(multiSelection = true)
    } else {
        state.value = state.value.copy(multiSelection = false)
        selectedRecipes.clear()
    }
    Box(
        modifier = Modifier
            .height(FoodRecipe_ITEM_HEIGHT)
            .border(
                width = 1.dp,
                color = if (state.value.selectedItem) MaterialTheme.colors.strokeBorderColor
                        else MaterialTheme.colors.cardStrokeBorder,
                shape = RoundedCornerShape(size = MEDIUM_PADDING)
            )
            .combinedClickable(
                onClick = {
                    if (state.value.isContextual || state.value.multiSelection) {
                        applicationSelection(
                            currentRecipe = foodItemEntity,
                            state
                        )
                    } else {
                        //  sharedResultViewModel.addResult(foodItem)
                        navigator.navigate(DetailScreenDestination(result = foodItem))
                        //navController.navigate(Screen.DetailPage.passRecipeId(recipeId = foodItem.recipeId))
                    }
                },
                onLongClick = {
                    Log.d(
                        TAG,
                        "On long Click"
                    )

                    if (!state.value.multiSelection) {
                        state.value = state.value.copy(
                            isContextual = true,
                            multiSelection = true
                        )
                        applicationSelection(
                            currentRecipe = foodItemEntity,
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
        Surface(shape = RoundedCornerShape(size = MEDIUM_PADDING)) {
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

                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "test",
                        color = Color.White
                    )
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
            drawable.ic_heart,
            "${foodItem.aggregateLikes}",
            Color.Red
        )
        InfoColumn(
            drawable.ic_clock,
            "${foodItem.readyInMinutes}",
            MaterialTheme.colors.readyInMinute
        )
        InfoColumn(
            drawable.ic_leaf,
            if (foodItem.vegan) "Vegan" else "Non vegan",
            Color.Green
        )
    }
}

@Composable
fun InfoColumn(
    @DrawableRes intResource: Int,
    text: String,
    color: Color,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
    state: MutableState<FavoriteState>,
) {
    if (selectedRecipes.contains(currentRecipe)) {
        selectedRecipes.remove(currentRecipe)
        state.value = state.value.copy(selectedItem = false)
        applyActionModeTitle(state)
    } else {
        selectedRecipes.add(currentRecipe)
        state.value = state.value.copy(selectedItem = true)
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
    state: MutableState<FavoriteState>,
) {
    if (selectedRecipes.contains(foodItem)) {
        state.value = state.value.copy(selectedItem = true)
    } else {
        state.value = state.value.copy(selectedItem = false)
    }
}

