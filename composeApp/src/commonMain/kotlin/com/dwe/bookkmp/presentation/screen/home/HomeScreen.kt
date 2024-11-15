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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bookkmp.composeapp.generated.resources.Res
import bookkmp.composeapp.generated.resources.book_library
import bookkmp.composeapp.generated.resources.favorite
import bookkmp.composeapp.generated.resources.latest
import bookkmp.composeapp.generated.resources.name
import bookkmp.composeapp.generated.resources.random
import com.dwe.bookkmp.presentation.components.BookFilterChip
import com.dwe.bookkmp.presentation.components.BookView
import com.dwe.bookkmp.presentation.components.ErrorView
import com.dwe.bookkmp.presentation.components.LoadingView
import com.dwe.bookkmp.utils.DisPlayResult
import com.dwe.bookkmp.utils.isScrollingUp
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class,
)
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
        SortType.NEWEST to stringResource(Res.string.latest),
        SortType.FAVORITE to stringResource(Res.string.favorite),
        SortType.TITLE to stringResource(Res.string.name),
        SortType.RANDOM to stringResource(Res.string.random)
    )
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stringResource(Res.string.book_library))
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
                                    onSelected = {
                                        viewModel.setSortType(it)
                                    }
                                )
                            }
                        }
                    }
                },
                expandedHeight = 100.dp
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onCreateClick,
                expanded = listState.isScrollingUp(),
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add"
                    )
                },
                text = { Text(text = "Add Book") }
            )
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
                        state = listState,
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