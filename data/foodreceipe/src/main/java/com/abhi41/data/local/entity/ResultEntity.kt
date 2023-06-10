package com.abhi41.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi41.ui.model.ExtendedIngredient
import com.abhi41.ui.util.Constants
import com.abhi41.ui.model.Result

@Entity(tableName = Constants.RECIPES_TABLE)
data class ResultEntity(
    @PrimaryKey val recipeId: Int,
    val aggregateLikes: Int,
    val cheap: Boolean,
    val dairyFree: Boolean,
    val extendedIngredients: List<ExtendedIngredient>,
    val glutenFree: Boolean,
    val image: String,
    val readyInMinutes: Int,
    val sourceName: String?,
    val sourceUrl: String,
    val summary: String,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
) {
    fun toRecipes(): Result {
        return Result(
            recipeId,
            aggregateLikes,
            cheap,
            dairyFree,
            extendedIngredients,
            glutenFree,
            image,
            readyInMinutes,
            sourceName,
            sourceUrl,
            summary,
            title,
            vegan,
            vegetarian,
            veryHealthy
        )
    }
}
