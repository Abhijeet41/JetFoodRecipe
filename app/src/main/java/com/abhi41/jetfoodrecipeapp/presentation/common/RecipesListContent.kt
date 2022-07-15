@file:OptIn(ExperimentalCoilApi::class)

package com.abhi41.jetfoodrecipeapp.presentation.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.abhi41.foodrecipe.model.Result
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.navigation.Screen
import com.abhi41.jetfoodrecipeapp.presentation.destinations.DetailScreenDestination
import com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen.DashBoardViewModel
import com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen.SharedResultViewModel
import com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.DetailScreen
import com.abhi41.jetfoodrecipeapp.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.jsoup.Jsoup

@Destination
@Composable
fun RecipesListContent(
    foodRecipes: List<Result>?,
    navigator: DestinationsNavigator,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = EXTRA_LARGE_PADDING),
        contentPadding = PaddingValues(all = SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING) //its like space between item list
    ) {
        items(
            items = foodRecipes!!,
            key = { food ->
                food.recipeId
            }
        ) { food ->
            food.let {
                FoodItem(food,navigator)
            }
        }
    }

}


@Composable
fun FoodItem(
    food: Result,
    navigator: DestinationsNavigator,
) {

    val painter = rememberImagePainter(data = "${food.image}") {
        placeholder(R.drawable.ic_placeholder)
        // crossfade(600)
        error(R.drawable.ic_placeholder)
    }

    Box(
        modifier = Modifier
            .border(
                1.dp, MaterialTheme.colors.cardStrokeBorder, shape = RoundedCornerShape(
                    size = MEDIUM_PADDING
                )
            )
            .height(FoodRecipe_ITEM_HEIGHT)
            .clickable {
                navigator.navigate(DetailScreenDestination(food))
                /* sharedResultViewModel.addResult(food)
                navController.navigate(Screen.DetailPage.passRecipeId(recipeId = food.recipeId))*/
            }
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
                // verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(),
                    painter = painter,
                    contentDescription = "food_image",
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(EXTRA_SMALL_PADDING)
                ) {
                    Text(
                        text = food.title,
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.titleColor,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.padding(top = 20.dp),
                        text = Jsoup.parse(food.summary).text(),
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.descriptionColor,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                    RowLikesAndCategory(food)
                }

            }
        }
    }

}

@Composable
fun RowLikesAndCategory(food: Result) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = SMALL_PADDING),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {

        InfoColumn(
            R.drawable.ic_heart,
            "${food.aggregateLikes}",
            Color.Red
        )
        InfoColumn(
            R.drawable.ic_clock,
            "${food.readyInMinutes}",
            MaterialTheme.colors.readyInMinute
        )
        InfoColumn(
            R.drawable.ic_leaf,
            if (food.vegan) "Vegan" else "Non vegan",
            Color.Green
        )

    }
}

@Composable
private fun InfoColumn(
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




