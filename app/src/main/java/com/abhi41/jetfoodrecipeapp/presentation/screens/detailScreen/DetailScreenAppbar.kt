package com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.tabs.overview

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.abhi41.foodrecipe.model.Result
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.data.local.entities.FavoriteEntity

@Composable
fun DetailScreenAppbar(
    navController: NavHostController,
    favoriteRecipe: State<List<FavoriteEntity>?>,
    selectedHero: Result?,
    onFavoriteClick: () -> Unit
) {
    var isRecipeSaved by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {
            Text(text = "Detail", fontWeight = FontWeight.Bold, color = Color.White)
        },
        navigationIcon = {
            AppBarIcon(R.drawable.ic_arrow_back) {
                navController.popBackStack()
            }
        },
        actions = {
            if (!favoriteRecipe.value.isNullOrEmpty()) {
                for (savedFavorite in favoriteRecipe.value!!) {
                    if (savedFavorite.result.recipeId == selectedHero?.recipeId) {
                        isRecipeSaved = true
                        break
                    } else {
                        isRecipeSaved = false
                    }
                }
            }
            AppBarIcon(icon = R.drawable.ic_star, isRecipeSaved) {
                onFavoriteClick()
            }
        },
        backgroundColor = Color.Black
    )

}

@Composable
fun AppBarIcon(icon: Int, isRecipeSaved: Boolean = false, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Icon",
            tint = if (isRecipeSaved) Color.Yellow else Color.White
        )
    }
}
