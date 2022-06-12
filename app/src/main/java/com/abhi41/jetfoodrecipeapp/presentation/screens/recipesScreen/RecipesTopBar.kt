package com.abhi41.jetfoodrecipeapp.presentation.screens.recipesScreen

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.abhi41.jetfoodrecipeapp.ui.theme.topAppBarBackgroundColor
import com.abhi41.jetfoodrecipeapp.ui.theme.topAppBarContentColor

@Composable
fun RecipesTopBar(
    onSearchClicked: () -> Unit
) {

    TopAppBar(
        title = {
            Text(
                text = "Search...",
                color = MaterialTheme.colors.topAppBarContentColor
            )
        }, backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            IconButton(onClick = {
                onSearchClicked()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icons",
                    tint = Color.White
                )
            }
        }
    )

}

@Preview(showBackground = true)
@Composable
fun SearchTopBarPreview() {
    TopAppBar(){}
}