package com.abhi41.data.local

import androidx.annotation.Keep
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.abhi41.jetfoodrecipeapp.data.local.util.JsonParser
import com.abhi41.ui.model.ExtendedIngredient
import com.google.gson.reflect.TypeToken
import com.abhi41.ui.model.Result
@Keep
@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
    // private val jsonParser: GsonParser  //we don't need to this whatever converts use moshi, gson etc
    // private val jsonParser: MoshiParser
) {

    @TypeConverter
    fun fromIngredientsJson(json: String): List<ExtendedIngredient> {
        return jsonParser.fromJson<ArrayList<ExtendedIngredient>>(
            json = json,
            type = object : TypeToken<ArrayList<ExtendedIngredient>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toIngredientJson(extendedIngredient: List<ExtendedIngredient>): String {
        return jsonParser.toJson(
            extendedIngredient,
            type = object : TypeToken<ArrayList<ExtendedIngredient>>() {}.type
        ) ?: "[]"
    }


    @TypeConverter
    fun stringToResult(data: String): Result? {
        val listType = object : TypeToken<Result>(){}.type
        return jsonParser.fromJson(data,listType)
    }

    @TypeConverter
    fun toResultJson(result: Result): String {
        return jsonParser.toJson(
            result,
            type = object : TypeToken<Result>() {}.type
        ) ?: "[]"
    }
}