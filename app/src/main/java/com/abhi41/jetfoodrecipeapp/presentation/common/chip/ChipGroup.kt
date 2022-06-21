package com.riegersan.composeexperiments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abhi41.jetfoodrecipeapp.presentation.common.chip.*
import com.abhi41.jetfoodrecipeapp.ui.theme.SMALL_PADDING

@Preview(showBackground = true)
@Composable
fun ChipGroupMultiSelection(
    modifier: Modifier = Modifier,
    cars: List<Meal> = MealType.getMeals(),
    selectedCars: List<Meal?> = MealType.getMeals(),
    onSelectedChanged: (String) -> Unit = {},
) {
    Column(modifier = modifier) {
        LazyRow {
            items(cars) { item ->
                Chip(
                    name = item.meal,
                    isSelected = selectedCars.contains(item),
                    onSelectionChanged = {
                        onSelectedChanged(it)
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MealTypeChipGroup(
    modifier: Modifier = Modifier,
    meals: List<Meal> = MealType.getMeals(),
    selectedMeal: Meal? = MealType.getMeals().get(0),
    onSelectedChanged: (String) -> Unit = {},
) {
    Column(modifier = modifier.padding(top = SMALL_PADDING)) {
        LazyRow {
            items(meals) { item ->
                Chip(
                    name = item.meal,
                    isSelected = selectedMeal == item,
                    onSelectionChanged = {
                        onSelectedChanged(it)
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DietTypeChipGroup(
    modifier: Modifier = Modifier,
    meals: List<Diet> = DietType.getDiets(),
    selectedMeal: Diet? = DietType.getDiets().get(0),
    onSelectedChanged: (String) -> Unit = {}
) {
    Column(modifier = modifier.padding(top = SMALL_PADDING)) {
        LazyRow {
            items(meals) { item->
                Chip(
                    name = item.diet,
                    isSelected = selectedMeal == item,
                    onSelectionChanged = {
                        onSelectedChanged(it)
                    }
                )
            }
        }
    }
}

