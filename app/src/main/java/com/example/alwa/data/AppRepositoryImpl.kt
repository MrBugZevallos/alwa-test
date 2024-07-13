package com.example.alwa.data

import android.content.Context
import com.example.alwa.data.local.LocalAppsDataProvider
import com.example.alwa.data.model.App
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppRepositoryImpl(private val context: Context) : AppRepository {
    override fun getAll(): Flow<List<App>> = flow {
        val data = LocalAppsDataProvider.readApps(context)
        emit(data)
    }
}