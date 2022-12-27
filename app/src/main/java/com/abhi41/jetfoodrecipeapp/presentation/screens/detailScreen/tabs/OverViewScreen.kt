@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMotionApi::class)

package com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.tabs

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.model.Result
import com.abhi41.jetfoodrecipeapp.ui.theme.*
import org.jsoup.Jsoup

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OverViewScreen(selectedHero: Result?) {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.overview_motion_img)
            .readBytes()
            .decodeToString()
    }


    Scaffold() {
        var animateButton by remember { mutableStateOf(false) }
        val buttonAnimationProgress by animateFloatAsState(
            targetValue = if (animateButton) 1f else 0f,
            animationSpec = tween(1000)
        )

        MotionLayout(
            motionScene = MotionScene(content = motionScene),
            progress = buttonAnimationProgress,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colors.motionLayoutBg)
        ) {
            val imageUrl = selectedHero?.image
            val recipeImg = rememberImagePainter(imageUrl) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }

            ImageSection(
                recipeImg,
                selectedHero?.aggregateLikes,
                selectedHero?.readyInMinutes
            )
            TitleAndCategoriesSection(selectedHero) {
                animateButton = !animateButton
            }
            DescriptionSection(selectedHero?.summary)
        }


    }
}


@Composable
private fun ImageSection(
    recipeImg: ImagePainter,
    aggregateLikes: Int? = 1225,
    readyInMinutes: Int? = 40
) {
    //val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxWidth()
            .layoutId("imgFood"),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                /*     .graphicsLayer {
                    scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                    translationY = scrolledY * 0.5f
                    previousOffset = lazyListState.firstVisibleItemScrollOffset
                }*/
                .height(250.dp),
            painter = recipeImg,
            contentDescription = "recipe_image",
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0x80000000),
                        )
                    )
                )
        )
        Row(
            modifier = Modifier
                .padding(SMALL_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RowLikesAndTimeInfo(aggregateLikes.toString(), R.drawable.ic_heart)
            Spacer(modifier = Modifier.width(SMALL_PADDING))
            RowLikesAndTimeInfo(readyInMinutes.toString(), R.drawable.ic_clock)
        }

    }
}

@Composable
fun RowLikesAndTimeInfo(text: String, icon: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Icon",
            tint = Color.White
        )
        Text(
            text = text,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun TitleAndCategoriesSection(selectedHero: Result?, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .background(color = MaterialTheme.colors.categoriesBackgroundColor)
            .layoutId("title_and_category")
            .padding(SMALL_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = selectedHero?.title ?: "Strawberry Cheesecake Chocolate",
            fontWeight = FontWeight.Bold,
            //color = MaterialTheme.colors.titleColor,
            color = MaterialTheme.colors.titleColor,
            fontSize = TXT_EXTRA_LARGE_SIZE,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            ColumnCategory(selectedHero)
        }


    }
}

@Composable
private fun ColumnCategory(selectedHero: Result?) {
    val isVegan = selectedHero?.vegan ?: false
    val isVegetarian = selectedHero?.vegetarian ?: false
    val isGlutenFree = selectedHero?.glutenFree ?: false
    val isDairyFree = selectedHero?.dairyFree ?: false
    val isVeryHealthy = selectedHero?.veryHealthy ?: false
    val isCheap = selectedHero?.cheap ?: false

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        RowCategories(R.drawable.ic_checkmark, "Vegitarian", isVegetarian)
        RowCategories(R.drawable.ic_checkmark, "Vegan", isVegan)
        //RowCategories(R.drawable.ic_checkmark, "Healthy")
    }
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        RowCategories(R.drawable.ic_checkmark, "Gluten Free", isGlutenFree)
        RowCategories(R.drawable.ic_checkmark, "Diary Free", isDairyFree)

    }
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        RowCategories(R.drawable.ic_checkmark, "Healthy", isVeryHealthy)
        RowCategories(R.drawable.ic_checkmark, "Cheap", isCheap)
    }
}

@Composable
fun RowCategories(icon: Int, text: String, isVegetarian: Boolean) {
    val color = if (isVegetarian) MaterialTheme.colors.categoriesSelectedIconColor
    else MaterialTheme.colors.categoriesIconColor
    Row(
        modifier = Modifier.padding(top = SMALL_PADDING),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Icon",
            tint = color,
        )
        Spacer(modifier = Modifier.width(EXTRA_SMALL_PADDING))
        Text(
            text = text,
            color = color,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun DescriptionSection(summary: String?) {
    val scroll = rememberScrollState(0)
    val summary = Jsoup.parse(summary).text()
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.motionLayoutBg)
            // .wrapContentSize(unbounded = true)
            .verticalScroll(rememberScrollState(1))
            .padding(
                bottom = SMALL_PADDING,
                start = SMALL_PADDING,
                end = SMALL_PADDING
            )
            .layoutId("description")
    ) {

        Text(
            text = summary
                ?: stringResource(R.string.descriptionDemo),
            color = MaterialTheme.colors.descriptionColor,
            fontSize = TXT_MEDIUM_SIZE,
        )
    }


}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun OverViewScreenPreview() {
    OverViewScreen(
        Result(
            recipeId = 62155,
            title = "",
            image = "",
            aggregateLikes = 1223,
            cheap = true,
            dairyFree = false,
            extendedIngredients = emptyList(),
            glutenFree = true,
            readyInMinutes = 10,
            sourceName = "",
            sourceUrl = ",",
            summary = "sadsa",
            vegan = true,
            vegetarian = false,
            veryHealthy = true
        )
    )
}