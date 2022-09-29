package com.abhi41.jetfoodrecipeapp.utils

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhi41.jetfoodrecipeapp.ui.theme.EXTRA_SMALL_PADDING
import com.abhi41.jetfoodrecipeapp.ui.theme.FoodRecipe_ITEM_HEIGHT
import com.abhi41.jetfoodrecipeapp.ui.theme.MEDIUM_PADDING
import com.abhi41.jetfoodrecipeapp.ui.theme.cardStrokeBorder

@Composable
fun AnimatedShimmer() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, translateAnimation.value)
    )
    Column() {
        repeat(6) {
            ShimmerListItem(brush = brush)
        }
    }
}

@Composable
fun ShimmerListItem(brush: Brush) {
    Box(
        modifier = Modifier
            .border(
                1.dp, MaterialTheme.colors.cardStrokeBorder, shape = RoundedCornerShape(
                    size = MEDIUM_PADDING
                )
            )
            .height(FoodRecipe_ITEM_HEIGHT)

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

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(EXTRA_SMALL_PADDING)
                        .background(brush)
                        .fillMaxHeight(),
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(EXTRA_SMALL_PADDING)
                ) {
                    Spacer(
                        modifier = Modifier
                            .background(brush)
                            .height(30.dp)
                            .padding(EXTRA_SMALL_PADDING)
                            .fillMaxWidth(),
                    )
                    Spacer(Modifier.height(20.dp))
                    Spacer(
                        modifier = Modifier
                            .background(brush)
                            .height(55.dp)
                            .padding(EXTRA_SMALL_PADDING)
                            .fillMaxWidth(),
                    )

                    Row(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        repeat(3) {
                            Spacer(
                                modifier = Modifier
                                    .size(30.dp)
                                    .background(brush)
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        repeat(3) {
                            Spacer(
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(20.dp)
                                    .background(brush)
                            )
                        }

                    }

                }

            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun ShimmerListPreview() {
    AnimatedShimmer()
}

