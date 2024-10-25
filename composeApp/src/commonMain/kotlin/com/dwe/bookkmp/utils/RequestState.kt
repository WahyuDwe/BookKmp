package com.dwe.bookkmp.utils

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

sealed class RequestState<out T> {
    data object Idle : RequestState<Nothing>()
    data object Loading : RequestState<Nothing>()
    data class Success<out T>(val data: T) : RequestState<T>()
    data class Error(val message: String) : RequestState<Nothing>()

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
    fun isError() = this is Error

    fun getSuccessData() = (this as Success).data
    fun getErrorMessage(): String = (this as Error).message
}

@Composable
fun <T> RequestState<T>.DisPlayResult(
    modifier: Modifier = Modifier,
    onIdle: @Composable () -> Unit = {},
    onLoading: @Composable () -> Unit = {},
    onSuccess: @Composable (T) -> Unit = {},
    onError: @Composable (String) -> Unit = {},
    transitionSpec: ContentTransform = fadeIn(tween(durationMillis = 800)) togetherWith fadeOut(
        tween(durationMillis = 800)
    ),
    backgroundColor: Color? = null
) {
    AnimatedContent(
        modifier = modifier.background(color = backgroundColor ?: Color.Unspecified),
        targetState = this,
        transitionSpec = { transitionSpec },
        label = "Content Animation"
    ) { state ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            when (state) {
                is RequestState.Idle -> {
                    onIdle.invoke()
                }

                is RequestState.Loading -> {
                    onLoading.invoke()
                }

                is RequestState.Success -> {
                    onSuccess(state.getSuccessData())
                }

                is RequestState.Error -> {
                    onError.invoke(state.getErrorMessage())
                }
            }
        }
    }
}