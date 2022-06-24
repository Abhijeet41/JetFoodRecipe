package com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen

import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.abhi41.foodrecipe.model.Result
import com.abhi41.jetfoodrecipeapp.data.local.entities.FavoriteEntity
import com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.tabs.IngredientScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.tabs.InstructionScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.tabs.OverViewScreen
import com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.tabs.overview.DetailScreenAppbar
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val selectedHero by detailViewModel.selectedRecipe.collectAsState()

    val tabItems = listOf("Overview", "Ingredients", "Instruction")
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val favoriteRecipe = detailViewModel.readFavoriteRecipes.observeAsState()
    var isRecipeSaved by remember {
        mutableStateOf(false)
    }
    var savedRecipeId by remember { mutableStateOf(0)}

    Scaffold(
        topBar = {
            TopAppBar() {
                DetailScreenAppbar(//we passing list and selected list to change color of appbar
                    navController = navController,
                    favoriteRecipe = favoriteRecipe,
                    selectedHero = selectedHero
                ) { //favorite click listener

                    var selectedId = detailViewModel.selectedRecipe.value?.recipeId
                        //check whether favorite recipe saved or not
                        for (favorite in favoriteRecipe.value!!) {
                            if (favorite.result.recipeId == selectedId) {
                                isRecipeSaved = true
                                savedRecipeId = favorite.id
                                break
                            }
                        }

                        if (!isRecipeSaved) { //if its not already saved then insert
                            detailViewModel.insertFavoriteRecipe(
                                FavoriteEntity(0, selectedHero!!)
                            )
                            isRecipeSaved = true
                        } else { //if its already saved then delete
                            var favoriteEntity = FavoriteEntity(
                                savedRecipeId,
                                selectedHero!!
                            )
                            coroutineScope.launch {
                                detailViewModel.deleteFavoriteRecipe(favoriteEntity)
                            }

                        }

                    }

                }

        },

        content = {
            Column(modifier = Modifier.fillMaxWidth()) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    backgroundColor = Color.Transparent,
                    contentColor = Color.Black,
                    divider = {
                        TabRowDefaults.Divider(
                            thickness = 1.dp,
                            color = Color.Black
                        )
                    },
                    indicator = { tabPosition ->
                        TabRowDefaults.Indicator(
                            Modifier
                                .pagerTabIndicatorOffset(
                                    pagerState = pagerState,
                                    tabPositions = tabPosition
                                )
                                .fillMaxWidth(),
                            color = Color.White
                        )
                    }
                ) {
                    tabItems.forEachIndexed { index, _ ->
                        val selected = pagerState.currentPage == index
                        val color = remember {
                            Animatable(Color.Black)
                        }

                        Tab(
                            modifier = Modifier.background(
                                color = color.value,
                            ),
                            text = {
                                Text(
                                    text = tabItems[index],
                                    style = TextStyle(
                                        color = if (selected) Color.White else Color.White.copy(
                                            alpha = ContentAlpha.disabled
                                        ),
                                        fontSize = 15.sp
                                    )
                                )
                            },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            })
                    }
                }
                horizontalPager(tabItems, pagerState, selectedHero)
            }
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun horizontalPager(
    tabItems: List<String>,
    pagerState: PagerState,
    selectedHero: Result?,
) {
    HorizontalPager(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        count = tabItems.size,
        state = pagerState
    ) { page ->
        when (page) {
            0 -> {
                OverViewScreen(selectedHero)
            }
            1 -> {
                IngredientScreen(selectedHero?.extendedIngredients)
            }
            2 -> {
                InstructionScreen(selectedHero?.sourceUrl)
            }
        }
    }
}