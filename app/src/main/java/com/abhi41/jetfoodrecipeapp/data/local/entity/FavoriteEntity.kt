package com.abhi41.jetfoodrecipeapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi41.jetfoodrecipeapp.model.Result
import com.abhi41.jetfoodrecipeapp.utils.Constants

@Entity(tableName = Constants.FAVORITE_RECIPES_TABLE)
class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)