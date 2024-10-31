package com.dwe.bookkmp.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dwe.bookkmp.presentation.screen.home.SortType

@Composable
fun BookFilterChip(
    isSelected: Boolean,
    text: String,
    sortType: SortType,
    onSelected: (SortType) -> Unit
) {
    FilterChip(
        onClick = { onSelected(sortType) },
        selected = isSelected,
        label = { Text(text = text) },
        leadingIcon = if (isSelected) {
            {
                Icon(
                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                    imageVector = Icons.Rounded.Done,
                    contentDescription = "Selected",
                )
            }
        } else {
            null
        }
    )
}