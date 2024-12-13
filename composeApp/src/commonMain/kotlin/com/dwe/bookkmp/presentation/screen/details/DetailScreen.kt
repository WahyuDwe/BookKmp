package com.dwe.bookkmp.presentation.screen.details

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.koin.compose.viewmodel.koinViewModel

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
fun DetailsScreen(
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val viewModel = koinViewModel<DetailsViewModel>()
    val book by viewModel.selectedBook
    val isFavorite by viewModel.isFavorite
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back arrow icon"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onEditClick) {
                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = "Edit icon"
                        )
                    }
                    IconButton(
                        onClick = { viewModel.setFavoriteBook() }
                    ) {
                        Icon(
                            imageVector = if (isFavorite)
                                Icons.Rounded.Star
                            else
                                Icons.Outlined.StarOutline,
                            contentDescription = "Star icon"
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.deleteBook()
                            onBackClick()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = "Delete icon"
                        )
                    }
                }
            )
        }
    ) { padding ->
        println("DetailsScreen: ${book?.id}")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 12.dp)
                .padding(
                    top = padding.calculateTopPadding(),
                )
                .verticalScroll(rememberScrollState()),
        ) {
            with(sharedTransitionScope) {
                Box(
                    modifier = Modifier
                        .height(500.dp)
                        .padding(vertical = 12.dp)
                        .align(Alignment.CenterHorizontally)
                        .sharedElement(
                            rememberSharedContentState(key = book?.id.toString()),
                            animatedContentScope
                        )
                ) {
                    CoilImage(
                        modifier = Modifier
                            .clip(RoundedCornerShape(size = 12.dp))
                            .fillMaxHeight(),
                        imageModel = { book?.imageUrl },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Fit,
                            alignment = Alignment.Center
                        )

                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier
                    .alpha(0.5f)
                    .padding(horizontal = 12.dp),
                text = book?.category ?: "",
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = book?.title ?: "",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = book?.summary ?: ""
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                modifier = Modifier
                    .alpha(0.5f)
                    .padding(horizontal = 12.dp),
                text = book?.author ?: "",
            )
            Spacer(modifier = Modifier.height(12.dp))
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, bottom = 32.dp),
            ) {
                book?.tags?.forEach { tag ->
                    SuggestionChip(
                        onClick = { },
                        label = { Text(tag) }
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }
        }
    }
}