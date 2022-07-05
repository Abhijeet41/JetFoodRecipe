package com.abhi41.jetfoodrecipeapp.presentation.screens.favoriteScreen

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.data.local.entities.FavoriteEntity
import com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.DetailViewModel
import com.abhi41.jetfoodrecipeapp.presentation.screens.detailScreen.tabs.overview.AppBarIcon
import com.abhi41.jetfoodrecipeapp.ui.theme.TXT_MEDIUM_SIZE
import com.abhi41.jetfoodrecipeapp.ui.theme.topAppBarBackgroundColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun FavoriteScreenAppbar(
    detailViewModel: DetailViewModel,
    state: MutableState<FavoriteState>,
    onDeleteClick: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val backgroundColor = if (state.value.isContextual) {
        Color.DarkGray
    } else {
        MaterialTheme.colors.topAppBarBackgroundColor
    }
    var isActionModeEmpty = state.value.actionModeTitle.equals("")

    TopAppBar(
        backgroundColor = backgroundColor,
        title = {
            Text(
                text = if (isActionModeEmpty) "Favorite" else state.value.actionModeTitle,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        actions = {
            if (state.value.isContextual) {
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            } else {
                NormalActionBarItems(coroutineScope, detailViewModel)
            }

        }
    )
}


@Composable
private fun NormalActionBarItems(
    coroutineScope: CoroutineScope,
    detailViewModel: DetailViewModel
) {
    var showMenu by remember { mutableStateOf(false) }

    IconButton(onClick = { showMenu = !showMenu }) {
        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "more icon")
    }
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false }
    ) {
        DropdownMenuItem(onClick = {
            coroutineScope.launch {
                detailViewModel.deleteAllFavoriteRecipes()
                showMenu = false
            }
        }) {
            Text(
                text = "Delete All",
                fontSize = TXT_MEDIUM_SIZE,
                color = Color.White
            )
            //Icon(Icons.Filled.Delete, "Delete Icons")
        }
    }
}
