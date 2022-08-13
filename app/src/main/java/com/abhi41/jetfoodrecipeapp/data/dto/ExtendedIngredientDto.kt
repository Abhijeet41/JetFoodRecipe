package com.abhi41.jetfoodrecipeapp.data.dto


import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import com.abhi41.jetfoodrecipeapp.model.ExtendedIngredient
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class ExtendedIngredientDto(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("consistency")
    val consistency: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("original")
    val original: String,
    @SerializedName("unit")
    val unit: String
) : Parcelable {
    fun toExtendedIngredient(): ExtendedIngredient {
        return ExtendedIngredient(
            amount = amount,
            consistency = consistency,
            image = image,
            name = name,
            original = original,
            unit = unit
        )
    }
}