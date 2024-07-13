package com.example.alwa.util

fun isValidRating(value: String): Boolean {
    return Regex("^[0-4](\\.\\d{1})?\$|^[5]\$").matches(value)
}