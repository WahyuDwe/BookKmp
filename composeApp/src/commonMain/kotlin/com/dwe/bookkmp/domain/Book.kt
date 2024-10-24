package com.dwe.bookkmp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val imageUrl: String,
    val summary: String,
    val isFavorite: Boolean,
    val category: String,
    val tags: List<String>,
    val author: String
)
