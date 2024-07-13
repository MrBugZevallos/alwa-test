package com.example.alwa.data.local

import android.content.Context
import com.example.alwa.data.model.App
import com.example.alwa.R
import com.example.alwa.util.stringToDate
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.Date

object LocalAppsDataProvider {

    const val OBSOLESCENCE_TIME = 2 // años

    const val HIGH_RELEASE_RANGE = 2 // meses
    const val MEDIUM_RELEASE_RANGE = 5 // meses

    const val LOW_RATING = 3.5

    const val APP_LIFE_TIME = 3

    fun readApps(context: Context): List<App> {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date> {
            override fun deserialize(
                json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?
            ): Date {
                return json!!.asString!!.stringToDate()!!
            }
        })

        val gson = gsonBuilder.create()
        val appsData =
            context.resources.openRawResource(R.raw.apps)
                .bufferedReader()
                .use { it.readText() }
        val apps = gson.fromJson(appsData, Array<App>::class.java).asList()
        return apps
    }

    val recommendationsByObsolescence = listOf(
        "- Kotlin", "- Jetpack Compose", "- MVVM", "- Clean Architecture"
    )

    val recommendationsByLowRating = listOf(
        "- Jetpack Compose",
        "- MVVM",
        "- Clean Architecture",
        "- Material Design",
        "- Recopilación de Feedback",
        "- Analítica de errores",
        "- Google Analytics",
        "- Adobe Analytics",
        "- Firebase Crashlytics"
    )
}