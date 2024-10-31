package com.dwe.bookkmp.presentation.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwe.bookkmp.data.domain.Book
import com.dwe.bookkmp.data.room.BookDatabase
import com.dwe.bookkmp.utils.RequestState
import kotlinx.coroutines.launch

enum class SortType {
    NEWEST,
    TITLE,
    FAVORITE,
    RANDOM
}

class HomeViewModel(private val db: BookDatabase) : ViewModel() {

    private var _books: MutableState<RequestState<List<Book>>> =
        mutableStateOf(RequestState.Loading)
    val books: State<RequestState<List<Book>>> = _books

    private var _sortType: MutableState<SortType> = mutableStateOf(SortType.NEWEST)
    val sortType: State<SortType> = _sortType

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            when (sortType.value) {
                SortType.NEWEST -> {
                    println("HomeViewModel.loadBooks: SortType.NEWEST")
                    db.bookDao().readAllBooks().collect {
                        _books.value = RequestState.Success(it)
                    }
                }
                SortType.TITLE -> {
                    println("HomeViewModel.loadBooks: SortType.TITLE")
                    db.bookDao().readAllBooksSortedByTitle().collect {
                        _books.value = RequestState.Success(it)
                    }
                }
                SortType.FAVORITE -> {
                    println("HomeViewModel.loadBooks: SortType.FAVORITE")
                    db.bookDao().readAllBooksSortedByFavorite().collect {
                        _books.value = RequestState.Success(it)
                    }
                }

                SortType.RANDOM -> {
                    println("HomeViewModel.loadBooks: SortType.RANDOM")
                    db.bookDao().readAllBooksRandom().collect {
                        _books.value = RequestState.Success(it)
                    }
                }
            }
        }
    }

    fun setSortType(sortType: SortType) {
        _sortType.value = sortType
        loadBooks()
    }

}