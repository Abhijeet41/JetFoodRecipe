package com.abhi41.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi41.ui.model.Result
import com.abhi41.ui.util.Constants

@Entity(tableName = Constants.FAVORITE_RECIPES_TABLE)
class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)