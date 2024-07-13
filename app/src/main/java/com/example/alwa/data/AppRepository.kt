package com.example.alwa.data

import com.example.alwa.data.model.App
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getAll(): Flow<List<App>>
}