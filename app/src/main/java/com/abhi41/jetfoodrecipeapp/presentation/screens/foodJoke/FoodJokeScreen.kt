package com.abhi41.jetfoodrecipeapp.presentation.screens.foodJoke

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.ui.theme.EXTRA_LARGE_PADDING
import com.abhi41.jetfoodrecipeapp.ui.theme.MEDIUM_PADDING
import com.abhi41.jetfoodrecipeapp.ui.theme.TXT_LARGE_SIZE
import com.abhi41.jetfoodrecipeapp.utils.Constants
import com.ramcosta.composedestinations.annotation.Destination

private const val TAG = "FoodJokeScreen"
@Destination
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
        }
    ) {

        val state = foodJokeViewModel.foodJokeState.value

        if (state.foodJoke.isEmpty()) {
            foodJokeViewModel.getFoodJoke()
        } else {
            foodJokeDesign("${state.foodJoke?.get(0)?.text}")
            Log.d(TAG, "FoodJokeScreen: ${state.foodJoke?.get(0)?.text}")
        }

    }
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
                    .verticalScroll(rememberScrollState()),
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