package com.dwe.bookkmp.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dwe.bookkmp.presentation.components.BookFilterChip
import com.dwe.bookkmp.presentation.components.BookView
import com.dwe.bookkmp.presentation.components.ErrorView
import com.dwe.bookkmp.presentation.components.LoadingView
import com.dwe.bookkmp.utils.DisPlayResult
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onBookSelect: (Int) -> Unit,
    onCreateClick: () -> Unit
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val books by viewModel.books

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Book Library")
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateClick
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add Icon"
                )
            }
        }
    ) {
        books.DisPlayResult(
            onLoading = { LoadingView() },
            onError = { ErrorView(it) },
            onSuccess = { data ->
                if (data.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(
                                start = 12.dp,
                                end = 12.dp,
                                top = 12.dp,
                            )
                            .padding(top = it.calculateTopPadding()),
                        contentPadding = PaddingValues(bottom = 32.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 6.dp,
                                    ),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                BookFilterChip(
                                    isSelected = viewModel.sortType.value == SortType.NEWEST,
                                    text = "Newest",
                                    sortType = SortType.NEWEST,
                                    onSelected = { viewModel.setSortType(it) }
                                )

                                BookFilterChip(
                                    isSelected = viewModel.sortType.value == SortType.FAVORITE,
                                    text = "Favorite",
                                    sortType = SortType.FAVORITE,
                                    onSelected = { viewModel.setSortType(it) }
                                )

                                BookFilterChip(
                                    isSelected = viewModel.sortType.value == SortType.TITLE,
                                    text = "Name",
                                    sortType = SortType.TITLE,
                                    onSelected = { viewModel.setSortType(it) }
                                )
                            }
                        }

                        items(items = data, key = { it.id }) {
                            BookView(
                                book = it,
                                onClick = { onBookSelect(it.id) }
                            )
                        }
                    }
                } else {
                    ErrorView()
                }
            }
        )
    }
}