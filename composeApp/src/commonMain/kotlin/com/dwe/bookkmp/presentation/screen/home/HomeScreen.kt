package com.dwe.bookkmp.presentation.screen.home

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dwe.bookkmp.presentation.components.BookFilterChip
import com.dwe.bookkmp.presentation.components.BookView
import com.dwe.bookkmp.presentation.components.ErrorView
import com.dwe.bookkmp.presentation.components.LoadingView
import com.dwe.bookkmp.utils.DisPlayResult
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    onBookSelect: (Int) -> Unit,
    onCreateClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val books by viewModel.books
    val sortOption = listOf(
        SortType.NEWEST to "New",
        SortType.FAVORITE to "Favorite",
        SortType.TITLE to "Name",
        SortType.RANDOM to "Random"
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Book Library")
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            sortOption.forEach { (sortType, text) ->
                                BookFilterChip(
                                    isSelected = viewModel.sortType.value == sortType,
                                    text = text,
                                    sortType = sortType,
                                    onSelected = { viewModel.setSortType(it) }
                                )
                            }
                        }
                    }
                },
                expandedHeight = 85.dp
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
                        items(items = data, key = { it.id }) {
                            BookView(
                                book = it,
                                onClick = { onBookSelect(it.id) },
                                sharedTransitionScope = sharedTransitionScope,
                                animatedContentScope = animatedContentScope
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