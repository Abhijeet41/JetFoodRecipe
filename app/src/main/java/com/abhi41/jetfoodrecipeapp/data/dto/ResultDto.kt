package com.abhi41.jetfoodrecipeapp.data.dto


import android.os.Parcelable
import com.abhi41.jetfoodrecipeapp.data.local.entity.ResultEntity
import com.abhi41.jetfoodrecipeapp.model.Result
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class ResultDto(
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int,

    @SerializedName("cheap")
    val cheap: Boolean,

    @SerializedName("dairyFree")
    val dairyFree: Boolean,

    @SerializedName("extendedIngredients")
    val extendedIngredients: @RawValue List<ExtendedIngredientDto>,

    @SerializedName("glutenFree")
    val glutenFree: Boolean,

    @SerializedName("id")
    val recipeId: Int,

    @SerializedName("image")
    val image: String,

    @SerializedName("readyInMinutes")
    val readyInMinutes: Int,

    @SerializedName("sourceName")
    val sourceName: String?,

    @SerializedName("sourceUrl")
    val sourceUrl: String,
    @SerializedName("summary")
    val summary: String,

    @SerializedName("title")
    val title: String,
    @SerializedName("vegan")
    val vegan: Boolean,
    @SerializedName("vegetarian")
    val vegetarian: Boolean,
    @SerializedName("veryHealthy")
    val veryHealthy: Boolean,

    ) : Parcelable {
    fun toResult(): ResultEntity {
        return ResultEntity(
            recipeId = recipeId,
            aggregateLikes = aggregateLikes,
            cheap = cheap,
            dairyFree = dairyFree,
            extendedIngredients = extendedIngredients.map { it.toExtendedIngredient() },
            glutenFree = glutenFree,
            image = image,
            readyInMinutes = readyInMinutes,
            sourceName = sourceName,
            sourceUrl = sourceUrl,
            summary = summary,
            title = title,
            vegan = vegan,
            vegetarian = vegetarian,
            veryHealthy = veryHealthy
        )
    }

    fun toSearchResult(): Result {
        return Result(
            recipeId = recipeId,
            aggregateLikes = aggregateLikes,
            cheap = cheap,
            dairyFree = dairyFree,
            extendedIngredients = extendedIngredients.map { it.toExtendedIngredient() },
            glutenFree = glutenFree,
            image = image,
            readyInMinutes = readyInMinutes,
            sourceName = sourceName,
            sourceUrl = sourceUrl,
            summary = summary,
            title = title,
            vegan = vegan,
            vegetarian = vegetarian,
            veryHealthy = veryHealthy
        )
    }
}