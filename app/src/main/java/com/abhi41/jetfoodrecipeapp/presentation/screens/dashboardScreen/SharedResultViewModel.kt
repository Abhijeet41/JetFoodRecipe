package com.abhi41.jetfoodrecipeapp.presentation.screens.dashboardScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.abhi41.foodrecipe.model.Result

class SharedResultViewModel: ViewModel() {
    var result by mutableStateOf<Result?>(null)
        private set

    fun addResult(newResult: Result) {
        result = newResult
    }
}