package com.abhi41.foodrecipe.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi41.jetfoodrecipeapp.utils.Constants
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class Result(
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int,

    @SerializedName("cheap")
    val cheap: Boolean,

    @SerializedName("dairyFree")
    val dairyFree: Boolean,

    @SerializedName("extendedIngredients")
    val extendedIngredients: @RawValue List<ExtendedIngredient>,

    @SerializedName("glutenFree")
    val glutenFree: Boolean,

    @PrimaryKey(autoGenerate = false)
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

    ) : Parcelable