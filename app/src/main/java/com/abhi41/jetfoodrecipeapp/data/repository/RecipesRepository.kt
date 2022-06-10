package com.abhi41.jetfoodrecipeapp.data.repository

import com.abhi41.jetfoodrecipeapp.data.local.LocalDataSource
import com.abhi41.jetfoodrecipeapp.data.network.RemoteDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped //It survive configuration change it going to use same instance when user rotate screen
class RecipesRepository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
){
    val remote = remoteDataSource
    val local = localDataSource
}