package com.dwe.bookkmp.presentation.screen.manage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwe.bookkmp.data.domain.Book
import com.dwe.bookkmp.data.room.BookDatabase
import kotlinx.coroutines.launch

const val IMAGE_URL =
    "https://r2.erweima.ai/imgcompressed/compressed_c29235854ffdfec1257c539ecfc35783.webp"


class ManageViewModel(
    private val db: BookDatabase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val selectedBookId: Int = savedStateHandle["bookId"] ?: -1

    var imageField = mutableStateOf(IMAGE_URL)
    var titleField = mutableStateOf("")
    var summaryField = mutableStateOf("")
    var categoryField = mutableStateOf("")
    var tagsField = mutableStateOf("")
    var authorField = mutableStateOf("")

    init {
        viewModelScope.launch {
            if (selectedBookId != -1) {
                val selectedBook = db.bookDao().getBook(selectedBookId)
                selectedBook.let {
                    imageField.value = it.imageUrl
                    titleField.value = it.title
                    summaryField.value = it.summary
                    categoryField.value = it.category
                    tagsField.value = it.tags.joinToString()
                    authorField.value = it.author
                }
            }
        }
    }

    fun insertBook(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (
                    titleField.value.isNotEmpty() &&
                    summaryField.value.isNotEmpty() &&
                    categoryField.value.isNotEmpty() &&
                    tagsField.value.isNotEmpty() &&
                    authorField.value.isNotEmpty()
                ) {
                    db.bookDao().insertBook(
                        book = Book(
                            imageUrl = imageField.value,
                            title = titleField.value,
                            summary = summaryField.value,
                            category = categoryField.value,
                            tags = tagsField.value.split(","),
                            author = authorField.value,
                            isFavorite = false
                        )
                    )
                    onSuccess()
                } else {
                    onError("Please fill all fields")
                    return@launch
                }
            } catch (e: Exception) {
                onError(e.message ?: "An error occurred")
            }
        }
    }

    fun updateBook(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (titleField.value.isNotEmpty() &&
                    summaryField.value.isNotEmpty() &&
                    categoryField.value.isNotEmpty() &&
                    tagsField.value.isNotEmpty() &&
                    authorField.value.isNotEmpty()
                ) {
                    db.bookDao().updateBook(
                        book = Book(
                            id = selectedBookId,
                            imageUrl = imageField.value,
                            title = titleField.value,
                            summary = summaryField.value,
                            category = categoryField.value,
                            tags = tagsField.value.split(","),
                            author = authorField.value,
                            isFavorite = false
                        )
                    )
                    onSuccess()
                } else {
                    onError("Please fill all fields")
                    return@launch
                }
            } catch (e: Exception) {
                onError(e.message ?: "An error occurred")
            }
        }
    }
}