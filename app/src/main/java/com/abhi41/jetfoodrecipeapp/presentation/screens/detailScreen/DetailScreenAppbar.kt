package com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.tabs.overview

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.abhi41.jetfoodrecipeapp.R

@Composable
fun DetailScreenAppbar(navController: NavHostController, onFavoriteClick: () -> Unit) {

    TopAppBar(
        title = {
            Text(text = "Detail", fontWeight = FontWeight.Bold)
        },
        navigationIcon = {
            AppBarIcon(R.drawable.ic_arrow_back) {
                navController.popBackStack()
            }
        },
        actions = {
            AppBarIcon(icon = R.drawable.ic_star) {
                onFavoriteClick()
            }
        },
        backgroundColor = Color.Black
    )

}

@Composable
fun AppBarIcon(icon: Int, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Icon"
        )
    }
}
