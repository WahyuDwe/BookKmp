package com.dwe.bookkmp.presentation.screen.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dwe.bookkmp.data.domain.Book
import com.dwe.bookkmp.data.room.BookDatabase
import kotlinx.coroutines.flow.Flow

class DetailsViewModel(
    private val db: BookDatabase,
) : ViewModel() {

    var isFavorite = mutableStateOf(false)
        private set

    fun getBookById(bookId: Int): Flow<Book?> {
        return db.bookDao().getBookByIdFlow(bookId)
    }

}