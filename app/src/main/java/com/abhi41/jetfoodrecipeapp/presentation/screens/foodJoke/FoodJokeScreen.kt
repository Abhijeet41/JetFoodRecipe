package com.abhi41.jetfoodrecipeapp.presentation.screens.foodJoke

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.ui.theme.*
import com.abhi41.util.ShowFeedbackDialog
import com.ramcosta.composedestinations.annotation.Destination

private const val TAG = "FoodJokeScreen"

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
                backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
            )
        }
    ) {
        val context = LocalContext.current
        val activity = LocalContext.current as Activity

        val state = foodJokeViewModel.foodJokeState.value
        ShowFeedbackDialog(context = context, activity = activity)

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

    val painter = if (isSystemInDarkTheme())
        painterResource(id = R.drawable.ic_food_joke_background_dark)
    else painterResource(id = R.drawable.ic_food_joke_background)


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(bottom = EXTRA_LARGE_PADDING)
    ) {
        Image(
            painter = painter,
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
                    color = MaterialTheme.colors.txtFoodJoke,
                    fontFamily = FontFamily.Monospace,
                    fontSize = TXT_LARGE_SIZE
                )
            }
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    group = "devices",
    device = "spec:shape=Normal,width=360, height=640,unit=dp,dpi=480"
)
@Composable
fun foodJokeDesignPreview() {
    foodJokeDesign(stringResource(R.string.food_joke_txt))
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    group = "devices",
    device = "spec:shape=Normal,width=360, height=640,unit=dp,dpi=480"
)
@Composable
fun foodJokeDesignDarkPreview() {
    foodJokeDesign(stringResource(R.string.food_joke_txt))
}