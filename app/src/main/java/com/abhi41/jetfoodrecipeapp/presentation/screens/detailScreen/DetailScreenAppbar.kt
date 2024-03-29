package com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.data.local.entity.FavoriteEntity
import com.abhi41.jetfoodrecipeapp.data.local.entity.ResultEntity
import com.abhi41.jetfoodrecipeapp.model.Result
import com.abhi41.jetfoodrecipeapp.ui.theme.darkYello
import com.abhi41.jetfoodrecipeapp.ui.theme.tabBackgroundColor
import com.abhi41.jetfoodrecipeapp.utils.HexToJetpackColor
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun DetailScreenAppbar(
    favoriteRecipe: State<List<FavoriteEntity>?>,
    selectedHero: Result?,
    navigator: DestinationsNavigator,
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
                navigator.popBackStack()
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
        backgroundColor = MaterialTheme.colors.tabBackgroundColor
    )

}


@Composable
fun AppBarIcon(icon: Int, isRecipeSaved: Boolean = false, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Icon",
            tint = if (isRecipeSaved) HexToJetpackColor.getColor(darkYello) else Color.White
        )
    }
}
