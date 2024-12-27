package com.example.hateandroidstudio

data class BookResponse(
    val title: String,
    val author: String,
    val coverImage: String,
    val section: String,
    val maxDays: Int,
    val isElectronic: Boolean
)