package com.dwe.bookkmp.presentation.screen.manage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageScreen(
    id: Int,
    onBackClick: () -> Unit
) {
    val viewModel = koinViewModel<ManageViewModel>()
    var imageField by viewModel.imageField
    var titleField by viewModel.titleField
    var summaryField by viewModel.summaryField
    var categoryField by viewModel.categoryField
    var tagsField by viewModel.tagsField
    var authorField by viewModel.authorField

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = if (id == -1) "Create" else "Update")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (id == -1) {
                            viewModel.insertBook(
                                onSuccess = { onBackClick() },
                                onError = { println(it) }
                            )
                        } else {
                            viewModel.updateBook(
                                onSuccess = onBackClick,
                                onError = { println(it) }
                            )
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Create"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 12.dp,
                )
                .verticalScroll(rememberScrollState())
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = imageField,
                onValueChange = { imageField = it },
                placeholder = { Text("Image URL") }
            )
            Spacer(modifier = Modifier.padding(12.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = titleField,
                onValueChange = { titleField = it },
                placeholder = { Text("Title") }
            )
            Spacer(modifier = Modifier.padding(12.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = summaryField,
                onValueChange = { summaryField = it },
                placeholder = { Text("Summary") }
            )
            Spacer(modifier = Modifier.padding(12.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = categoryField,
                onValueChange = { categoryField = it },
                placeholder = { Text("Category") }
            )
            Spacer(modifier = Modifier.padding(12.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = tagsField,
                onValueChange = { tagsField = it },
                placeholder = { Text("Tags") }
            )
            Spacer(modifier = Modifier.padding(12.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = authorField,
                onValueChange = { authorField = it },
                placeholder = { Text("Author") }
            )
            Spacer(modifier = Modifier.padding(24.dp))
        }
    }
}