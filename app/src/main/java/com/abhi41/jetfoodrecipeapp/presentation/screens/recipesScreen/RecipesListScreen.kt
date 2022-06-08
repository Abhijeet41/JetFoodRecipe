package com.abhi41.jetfoodrecipeapp.presentation.screens.recipesScreen


import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@Composable
fun RecipesScreen(navController: NavHostController) {
    ListRecipes()
}

@Composable
fun ListRecipes() {
   Text(text = "recipes")
}

@Preview
@Composable
fun RecipesScreenPreview() {
    ListRecipes()
}