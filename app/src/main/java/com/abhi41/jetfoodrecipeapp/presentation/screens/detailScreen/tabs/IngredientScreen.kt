package com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.abhi41.foodrecipe.model.ExtendedIngredient
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.ui.theme.*
import com.abhi41.jetfoodrecipeapp.utils.Constants
import kotlinx.parcelize.RawValue

@Composable
fun IngredientScreen(extendedIngredients: @RawValue List<ExtendedIngredient>?) {

    Scaffold {
        IngredientDesign(extendedIngredients)
    }

}

@Composable
private fun IngredientDesign(extendedIngredients: @RawValue List<ExtendedIngredient>?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = SMALL_PADDING),
        contentPadding = PaddingValues(all = SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {
        items(
            items = extendedIngredients!!,
        ) { extendedIngredient ->
            extendedIngredient.let {
                IngredientItem(extendedIngredient)
            }
        }
    }
}

@Composable
fun IngredientItem(extendedIngredient: ExtendedIngredient) {

    val painter =
        rememberImagePainter(data = "${Constants.BASE_IMAGE_URL}${extendedIngredient.image}") {
            placeholder(R.drawable.ic_transperent)
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }

    Box(
        modifier = Modifier

            .border(
                1.dp, MaterialTheme.colors.cardStrokeBorder, shape = RoundedCornerShape(
                    size = MEDIUM_PADDING
                )
            )
            .height(INGREDIENT_ITEM_HEIGHT)
            .clickable {},
        ) {
        Surface(
            modifier = Modifier.padding(MEDIUM_PADDING),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .background(Color.Transparent)

                        .fillMaxHeight(),
                    painter = painter,
                    contentDescription = "Ingredient Image",
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SMALL_PADDING),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = extendedIngredient.name,
                        style = MaterialTheme.typography.h4,
                        fontSize = TXT_TITLE_TEXT,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.titleColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        modifier = Modifier.padding(
                            top = SMALL_PADDING,
                        ),
                        text = extendedIngredient.amount.toString(),
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.titleColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.padding(
                            top = SMALL_PADDING,
                        ),
                        text = extendedIngredient.consistency,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.titleColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.padding(
                            top = SMALL_PADDING,
                        ),
                        text = extendedIngredient.original,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.titleColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun IngredientDesignPvreview() {
    IngredientItem(
        ExtendedIngredient(
            image = "${Constants.BASE_IMAGE_URL}salt-and-pepper.jpg",
            name = "Ground Beef",
            amount = 20.0,
            consistency = "",
            original = "Solid",
            unit = "1"
        )
    )
}