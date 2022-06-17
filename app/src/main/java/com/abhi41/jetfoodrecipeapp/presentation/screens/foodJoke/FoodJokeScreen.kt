package com.abhi41.jetfoodrecipeapp.presentation.screens.foodJoke

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.abhi41.foodrecipe.model.FoodJoke
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.data.local.entities.FoodJokeEntity
import com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.tabs.overview.AppBarIcon
import com.abhi41.jetfoodrecipeapp.ui.theme.EXTRA_LARGE_PADDING
import com.abhi41.jetfoodrecipeapp.ui.theme.MEDIUM_PADDING
import com.abhi41.jetfoodrecipeapp.ui.theme.SMALL_PADDING
import com.abhi41.jetfoodrecipeapp.ui.theme.TXT_LARGE_SIZE
import com.abhi41.jetfoodrecipeapp.utils.Constants

private const val TAG = "FoodJokeScreen"

@Composable
fun FoodJokeScreen(
    foodJokeViewModel: FoodJokeViewModel = hiltViewModel()
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Food Joke", fontWeight = FontWeight.Bold)
                },
                backgroundColor = Color.Black
            )
        },
        content = {
            val jokes by foodJokeViewModel.readFoodJoke.observeAsState()

            if (jokes.isNullOrEmpty()) {
                foodJokeViewModel.requestFoodJoke(queryApi = Constants.API_KEY)
            } else {
                foodJokeDesign("${jokes?.get(0)?.foodjoke?.text}")
                Log.d(TAG, "FoodJokeScreen: ${jokes?.get(0)?.foodjoke?.text}")
            }
        }
    )

}

@Composable
fun foodJokeDesign(jokes: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(bottom = EXTRA_LARGE_PADDING)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_food_joke_background_dark),
            contentScale = ContentScale.Crop,
            contentDescription = "food joke background"
        )
        Card(
            modifier = Modifier
                .padding(EXTRA_LARGE_PADDING)
                .background(Color.Transparent)
                .border(
                    1.dp, Color.White, shape = RoundedCornerShape(
                        size = MEDIUM_PADDING
                    )
                ),
            elevation = 4.dp,
        ) {
            Box(
                modifier = Modifier
                    .padding(MEDIUM_PADDING)
                    .verticalScroll(rememberScrollState(), enabled = true),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = jokes,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    fontSize = TXT_LARGE_SIZE
                )
            }
        }
    }
}

@Preview
@Composable
fun foodJokeDesignPreview() {
    foodJokeDesign(stringResource(R.string.food_joke_txt))
}
