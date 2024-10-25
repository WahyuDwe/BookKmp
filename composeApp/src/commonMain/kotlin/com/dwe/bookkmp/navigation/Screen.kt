package com.dwe.bookkmp.navigation

import kotlinx.serialization.Serializable

const val BOOK_ID = "bookId"

// using type save navigation compose
@Serializable
object Home

@Serializable
data class Details(val bookId: Int)

@Serializable
data class Manage(val bookId: Int = -1)
