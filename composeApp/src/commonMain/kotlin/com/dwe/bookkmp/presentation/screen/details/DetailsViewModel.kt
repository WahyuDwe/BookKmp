package com.dwe.bookkmp.presentation.screen.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwe.bookkmp.data.domain.Book
import com.dwe.bookkmp.data.room.BookDatabase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val db: BookDatabase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var selectedBook: MutableState<Book?> = mutableStateOf(null)
        private set
    var isFavorite = mutableStateOf(false)
        private set
    private val selectedBookId: Int = savedStateHandle.get<Int>("bookId") ?: 0

    init {
        viewModelScope.launch {
            db.bookDao()
                .getBookByIdFlow(selectedBookId)
                .collectLatest {
                    selectedBook.value = it
                    isFavorite.value = it?.isFavorite ?: false
                }
        }
    }

    fun setFavoriteBook() {
        viewModelScope.launch {
            if (selectedBook.value?.id != null) {
                db.bookDao().setFavorite(
                    isFavorite = !isFavorite.value,
                    bookId = selectedBook.value!!.id
                )
            }
        }
    }

    fun deleteBook() {
        viewModelScope.launch {
            db.bookDao()
                .deleteBookId(selectedBookId)
        }
    }

}