package com.abhi41.jetfoodrecipeapp.data.local.entities

import androidx.datastore.preferences.protobuf.Empty
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi41.foodrecipe.model.Result
import com.abhi41.jetfoodrecipeapp.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Entity(tableName = Constants.FAVORITE_RECIPES_TABLE)
class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result,
)

