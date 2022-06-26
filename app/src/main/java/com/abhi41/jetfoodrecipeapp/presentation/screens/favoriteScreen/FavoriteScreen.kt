@file:OptIn(ExperimentalFoundationApi::class)

package com.abhi41.jetfoodrecipeapp.presentation.screens.favoriteScreen

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
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
import com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.DetailViewModel
import com.abhi41.jetfoodrecipeapp.ui.theme.*
import kotlinx.coroutines.launch

private const val TAG = "FavoriteScreen"

var selectedRecipes: MutableList<FavoriteEntity> = mutableListOf()

@Composable
fun FavoriteScreen(
    navController: NavHostController,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val favoriteRecipe by detailViewModel.readFavoriteRecipes.observeAsState()
    var isContextual = remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    var actionModeTitle = remember { mutableStateOf("") }

    val onBack = { //handle on back pressed
        isContextual.value = false
        actionModeTitle.value = ""
        selectedRecipes.clear()
    }
    if (isContextual.value) {
        BackPressHandler(onBackPressed = onBack)
    }


    Scaffold(
        modifier = Modifier.padding(bottom = MEDIUM_PADDING),
        topBar = {
            FavoriteScreenAppbar(
                detailViewModel = detailViewModel,
                isContextual = isContextual,
                actionModeTitle = actionModeTitle
            ) {
                //On delete Click
                isContextual.value = false
                selectedRecipes.forEach { favoriteEntity ->
                    coroutineScope.launch {
                        detailViewModel.deleteFavoriteRecipe(favoriteEntity)
                    }
                }
                actionModeTitle.value = ""
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
                    FoodItem(foodItem, navController, isContextual, actionModeTitle)
                }
            }

        }
    }
}


@Composable
fun FoodItem(
    foodItemEntity: FavoriteEntity,
    navController: NavHostController,
    isContextual: MutableState<Boolean>,
    actionModeTitle: MutableState<String>,
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
    if (isContextual.value) //clear multiple selection on backpress
    {
        multiSelection = true
    } else {
        multiSelection = false
    }
    Box(
        modifier = Modifier
            .height(FoodRecipe_ITEM_HEIGHT)
            .border(
                1.dp, if (selectedItem.value) Color.Green else Color.White,
                shape = RoundedCornerShape(
                    size = MEDIUM_PADDING
                )
            )
            .combinedClickable(
                onClick = {

                    if (isContextual.value) {
                        applicationSelection(
                            currentRecipe = foodItemEntity,
                            selectedItem = selectedItem,
                            actionModeTitle = actionModeTitle,
                            isContextual = isContextual,
                        )
                    } else {
                        navController.navigate(Screen.DetailPage.passRecipeId(recipeId = foodItem.recipeId))
                    }
                },
                onLongClick = {
                    Log.d(TAG, "On long Click")

                    if (!multiSelection) {
                        multiSelection = true
                        isContextual.value = true
                        selectedItem.value = true

                        applicationSelection(
                            currentRecipe = foodItemEntity,
                            selectedItem = selectedItem,
                            actionModeTitle = actionModeTitle,
                            isContextual = isContextual,
                        )

                        true
                    } else {
                        multiSelection = false
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
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.padding(top = 20.dp),
                        text = foodItem.summary,
                        style = MaterialTheme.typography.caption,
                        color = Color.White.copy(alpha = ContentAlpha.medium),
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
            Color.Yellow
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
    actionModeTitle: MutableState<String>,
    isContextual: MutableState<Boolean>,
) {

    if (selectedRecipes.contains(currentRecipe)) {
        selectedRecipes.remove(currentRecipe)
        selectedItem.value = false
        applyActionModeTitle(actionModeTitle, isContextual)
    } else {
        selectedRecipes.add(currentRecipe)
        selectedItem.value = true
        applyActionModeTitle(actionModeTitle, isContextual)
    }

}

fun applyActionModeTitle(
    actionModeTitle: MutableState<String>,
    isContextual: MutableState<Boolean>
) {
    when (selectedRecipes.size) {
        0 -> {
            isContextual.value = false
            actionModeTitle.value = ""
        }
        1 -> {
            actionModeTitle.value = "${selectedRecipes.size} item selected"
        }
        else -> {
            actionModeTitle.value = "${selectedRecipes.size} items selected"
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




