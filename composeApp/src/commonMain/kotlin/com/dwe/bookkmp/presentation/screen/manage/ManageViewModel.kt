package com.dwe.bookkmp.presentation.screen.manage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwe.bookkmp.domain.Book
import com.dwe.bookkmp.room.BookDatabase
import kotlinx.coroutines.launch

const val IMAGE_URL =
    "https://r2.erweima.ai/imgcompressed/compressed_c29235854ffdfec1257c539ecfc35783.webp"


class ManageViewModel(
    private val db: BookDatabase,
) : ViewModel() {
    var imageField = mutableStateOf(IMAGE_URL)
    var titleField = mutableStateOf("")
    var summaryField = mutableStateOf("")
    var categoryField = mutableStateOf("")
    var tagsField = mutableStateOf("")
    var authorField = mutableStateOf("")

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
}