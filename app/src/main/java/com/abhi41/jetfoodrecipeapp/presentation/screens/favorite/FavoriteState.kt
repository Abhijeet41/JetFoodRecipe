package com.abhi41.jetfoodrecipeapp.presentation.screens.favorite

import androidx.compose.runtime.MutableState

data class FavoriteState(
    val isContextual: Boolean = false,
    val multiSelection: Boolean = false,
    val selectedItem: Boolean = false,
    val actionModeTitle: String = ""
)