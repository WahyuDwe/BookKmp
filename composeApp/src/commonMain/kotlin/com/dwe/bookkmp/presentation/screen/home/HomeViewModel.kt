package com.dwe.bookkmp.presentation.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwe.bookkmp.domain.Book
import com.dwe.bookkmp.room.BookDatabase
import com.dwe.bookkmp.utils.RequestState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(private val db: BookDatabase) : ViewModel() {
    private var _sortedByFavorite = MutableStateFlow(false)
    val sortedByFavorite: StateFlow<Boolean> = _sortedByFavorite

    private var _books: MutableState<RequestState<List<Book>>> =
        mutableStateOf(RequestState.Loading)
    val books: State<RequestState<List<Book>>> = _books

    init {
        viewModelScope.launch {
            _sortedByFavorite.collectLatest { favorite ->
                if (favorite) {
                    db.bookDao()
                        .readAllBooksSortedByFavorite()
                        .collectLatest { sortedBooks ->
                            _books.value = RequestState.Success(
                                data = sortedBooks.sortedBy { !it.isFavorite }
                            )
                        }
                } else {
                    db.bookDao()
                        .readAllBooks()
                        .collectLatest { allBooks ->
                            _books.value = RequestState.Success(
                                data = allBooks.sortedBy { it.isFavorite }
                            )
                        }
                }
            }
        }
    }

    fun toggleSortByFavorite() {
        _sortedByFavorite.value = !_sortedByFavorite.value
    }

}