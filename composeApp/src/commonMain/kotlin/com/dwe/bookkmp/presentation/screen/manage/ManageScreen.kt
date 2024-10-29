package com.dwe.bookkmp.presentation.screen.manage

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageScreen(
    id: Int,
    onBackClick: () -> Unit
) {
    val viewModel = koinViewModel<ManageViewModel>()
    val focusController = LocalFocusManager.current
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
                    IconButton(
                        onClick = {
                            focusController.clearFocus()
                            onBackClick()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // check if the book is new or not
                        if (id == -1) {
                            viewModel.insertBook(
                                onSuccess = {
                                    focusController.clearFocus()
                                    onBackClick()
                                },
                                onError = { println(it) }
                            )
                        } else {
                            viewModel.updateBook(
                                onSuccess = {
                                    focusController.clearFocus()
                                    onBackClick()
                                },
                                onError = { println(it) }
                            )
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Create"
                        )
                    }
                }
            )
        },
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
                .imePadding()
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusController.clearFocus()
                    }
                }
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                value = imageField,
                onValueChange = { imageField = it },
                placeholder = { Text("Image URL") }
            )
            Spacer(modifier = Modifier.padding(12.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                value = titleField,
                onValueChange = { titleField = it },
                placeholder = { Text("Title") }
            )
            Spacer(modifier = Modifier.padding(12.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                value = summaryField,
                onValueChange = { summaryField = it },
                placeholder = { Text("Summary") }
            )
            Spacer(modifier = Modifier.padding(12.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                value = categoryField,
                onValueChange = { categoryField = it },
                placeholder = { Text("Category") }
            )
            Spacer(modifier = Modifier.padding(12.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                value = tagsField,
                onValueChange = { tagsField = it },
                placeholder = { Text("Tags") }
            )
            Spacer(modifier = Modifier.padding(12.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                value = authorField,
                onValueChange = { authorField = it },
                placeholder = { Text("Author") }
            )
            Spacer(modifier = Modifier.padding(24.dp))
        }
    }
}