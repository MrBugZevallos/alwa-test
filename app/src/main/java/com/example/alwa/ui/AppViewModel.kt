package com.example.alwa.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alwa.data.AppRepository
import com.example.alwa.data.local.LocalAppsDataProvider
import com.example.alwa.data.model.App
import com.example.alwa.data.model.AppMetric
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.monthsUntil
import kotlinx.datetime.yearsUntil
import java.util.Date

class AppViewModel(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUIState())
    val uiState: StateFlow<AppUIState> = _uiState

    init {
        observeApps()
    }

    private fun observeApps() {
        viewModelScope.launch {
            appRepository.getAll().collect { apps ->
                initializeAnalyticsMetrics(apps)
            }
        }
    }

    fun addApp(appMetric: AppMetric) {
        val data = _uiState.value.apps.toMutableList()
        data.add(appMetric)
        initializeAnalyticsMetrics(data.map { it.app!! })
    }

    fun deleteApp(id: Int) {
        val data = _uiState.value.apps.toMutableList()
        data.removeIf { it.app!!.id == id }

        _uiState.value = AppUIState(
            apps = data
        )
    }

    private fun initializeAnalyticsMetrics(apps: List<App>) {
        val data = apps.map { app ->
            evaluateMetrics(app)
        }.sortedWith(
            compareByDescending<AppMetric> { it.level }
                .thenBy { it.app!!.rating }
        )

        _uiState.value = AppUIState(
            apps = data
        )
    }

    private fun evaluateMetrics(app: App): AppMetric {
        val appMetric = AppMetric(app)

        if (!isNewApp(app.uploadDate)) {
            val lowRating = isLowRating(app.rating)
            appMetric.lowRating = lowRating
            val obsolescence = isObsolete(app.lastRelease)
            appMetric.obsolete = obsolescence
        }

        val level = evaluateLevel(app.lastRelease, appMetric.lowRating)
        appMetric.level = level

        return appMetric
    }

    private fun isObsolete(lastReleaseAppDate: Date): Boolean {
        val lastReleaseDate = Instant.fromEpochMilliseconds(lastReleaseAppDate.time)
        val currentDate = Instant.fromEpochMilliseconds(Date().time)
        val obsolescence = lastReleaseDate.yearsUntil(currentDate, TimeZone.currentSystemDefault())
        return obsolescence >= LocalAppsDataProvider.OBSOLESCENCE_TIME
    }

    private fun evaluateLevel(lastRelease: Date, lowRating: Boolean): Int {
        val lastReleaseDate = Instant.fromEpochMilliseconds(lastRelease.time)
        val currentDate = Instant.fromEpochMilliseconds(Date().time)
        val releaseUp = lastReleaseDate.monthsUntil(currentDate, TimeZone.currentSystemDefault())
        return when {
            releaseUp <= LocalAppsDataProvider.HIGH_RELEASE_RANGE -> if (lowRating) 2 else 1
            releaseUp <= LocalAppsDataProvider.MEDIUM_RELEASE_RANGE -> 2
            else -> 3
        }
    }

    private fun isLowRating(rating: Float): Boolean {
        return rating <= LocalAppsDataProvider.LOW_RATING
    }

    private fun isNewApp(uploadDate: Date): Boolean {
        val appUploadDate = Instant.fromEpochMilliseconds(uploadDate.time)
        val currentDate = Instant.fromEpochMilliseconds(Date().time)
        val uploadDataLife = appUploadDate.monthsUntil(currentDate, TimeZone.currentSystemDefault())
        return uploadDataLife <= LocalAppsDataProvider.APP_LIFE_TIME
    }
}

data class AppUIState(
    val apps: List<AppMetric> = emptyList()
)