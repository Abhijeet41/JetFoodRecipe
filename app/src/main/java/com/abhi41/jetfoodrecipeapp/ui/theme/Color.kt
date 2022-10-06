package com.abhi41.jetfoodrecipeapp.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import com.abhi41.jetfoodrecipeapp.utils.HexToJetpackColor

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val darkYello = "FAC213"
val green = "00C980"


val Colors.titleColor
    @Composable
    get() = if (isLight) DarkGray else LightGray

val Colors.descriptionColor
    @Composable
    get() = if (isLight) Black.copy(alpha = 0.5f)
    else LightGray.copy(alpha = 0.5f)

val Colors.buttonColor
    @Composable
    get() = if (isLight) Purple700 else Black

val Colors.readyInMinute
    @Composable
    get() = if (isLight) Black else Color.Yellow

val Colors.topAppBarContentColor: Color
    @Composable
    get() = if (isLight) Color.White else LightGray

val Colors.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isLight) Purple500 else Black

val Colors.screenBackgroundColor: Color
    @Composable
    get() = if (isLight) Color.White else DarkGray

val Colors.strokeBorderColor: Color
    @Composable
    get() = if (isLight) Purple500 else Green

val Colors.cardStrokeBorder:Color
    @Composable
    get() = if (isLight) Black.copy(alpha = 0.3f) else White

val Colors.tabBackgroundColor:Color
    @Composable
    get() = if (isLight) Purple500 else Black

val Colors.categoriesBackgroundColor:Color
    @Composable
    get() = if (isLight) White else Black

val Colors.categoriesIconColor:Color
    @Composable
    get() = if (isLight) Black else White

val Colors.categoriesSelectedIconColor:Color
    @Composable
    get() = if (isLight) HexToJetpackColor.getColor(green) else Green

val Colors.txtFoodJoke:Color
    @Composable
    get() = if (isLight) Black else White
