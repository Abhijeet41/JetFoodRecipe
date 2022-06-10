package com.abhi41.jetfoodrecipeapp.presentation.common

import androidx.compose.foundation.Image
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
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.abhi41.foodrecipe.model.Result
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.ui.theme.*


@Composable
fun ListContent(
    foodRecipes: List<Result>?,
    navController: NavHostController
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
                FoodItem(food, navController)
            }
        }
    }

}

@Composable
fun FoodItem(food: Result, navController: NavHostController) {

    val painter = rememberImagePainter(data = "${food.image}") {
        placeholder(R.drawable.ic_placeholder)
        error(R.drawable.ic_placeholder)
    }

    Box(
        modifier = Modifier
            .height(FoodRecipe_ITEM_HEIGHT)
            .clickable { }
    ) {
        Surface(
            shape = RoundedCornerShape(
                size = LARGE_PADDING
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
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.padding(top = 20.dp),
                        text = food.summary,
                        style = MaterialTheme.typography.caption,
                        color = Color.White.copy(alpha = ContentAlpha.medium),
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = SMALL_PADDING),
        horizontalAlignment = Alignment.Start
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_heart),
                contentDescription = "heart icon",
                tint = Color.Red
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_clock),
                contentDescription = "heart icon",
                tint = Color.Yellow
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_leaf),
                contentDescription = "heart icon",
                tint = Color.Green
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${food.aggregateLikes}",
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                color = Color.Red
            )
            Text(
                text = "${food.readyInMinutes}",
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                color = Color.Yellow
            )
            Text(
                text = if (food.vegan) "Vegan" else "",
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                color = if (food.vegan) Color.Green else Color.Red
            )
        }

    }
}




