package com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.tabs

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.abhi41.foodrecipe.model.Result
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.ui.theme.*

@Composable
fun OverViewScreen(selectedHero: Result?) {
    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.screenBackgroundColor)
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
            TitleAndCategoriesSection(selectedHero)
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            painter = recipeImg,
            contentDescription = "recipe_image",
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp).background(
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
private fun TitleAndCategoriesSection(selectedHero: Result?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.DarkGray)
            .padding(SMALL_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = selectedHero?.title ?: "Strawberry Cheesecake Chocolate",
            fontWeight = FontWeight.Bold,
            //color = MaterialTheme.colors.titleColor,
            color = Color.White,
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
    val color = if (isVegetarian) Color.Green else Color.White
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

    Box(
        modifier = Modifier
            .padding(
                bottom = SMALL_PADDING,
                start = SMALL_PADDING,
                end = SMALL_PADDING
            )
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = summary
                ?: "When it comes to laying out the child composables within a Column, we may want to control how much space each child is to occupy without having to define a fixed height. In these cases we can utilise the Weight modifier to build fluid and dynamic layouts – the Weight modifier is also a part of the Column Scope. The weight defines how much of the available height the composable should get – so let’s imagine that there are three composables within a container, two of which have a weight of 3 and the third has a weight of 2. This totals to 8 – meaning that the available height will be divided by 8 and distributed amongst the children for their corresponding weight.",
            color = MaterialTheme.colors.titleColor,
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