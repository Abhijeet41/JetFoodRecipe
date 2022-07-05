package com.abhi41.jetfoodrecipeapp.presentation.screens.favoriteScreen

data class FavoriteState(
    val isContextual: Boolean = false,
    val multiSelection: Boolean = false,
    val selectedItem: Boolean = false,
    val actionModeTitle: String = ""
)