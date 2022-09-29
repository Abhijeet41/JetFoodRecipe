package com.abhi41.jetfoodrecipeapp.utils.newtwork_status

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status{
        AVAILABLE, UNAVAILABLE,LOSING, LOST
    }

}