package com.example.alwa.data.model

import java.util.Date

data class App(
    val id: Int,
    val name: String, val description: String,
    val rating: Float, val downloads: Int,
    val uploadDate: Date, val lastRelease: Date
)

class AppMetric(
    var app: App? = null,
    var obsolete: Boolean = false,
    var level: Int = 0,
    var lowRating: Boolean = false
)