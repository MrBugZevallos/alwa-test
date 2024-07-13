package com.example.alwa.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val PATTERN_DATE = "dd/MM/yyyy"

private fun sdf(): SimpleDateFormat = SimpleDateFormat(PATTERN_DATE, Locale.getDefault())

fun Date.dateToString(): String {
    return sdf().format(this)
}

fun String.stringToDate(): Date? {
    return sdf().parse(this)
}

fun Long.longToStringDate(): String {
    return sdf().format(Date(this))
}