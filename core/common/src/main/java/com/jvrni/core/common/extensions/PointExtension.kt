package com.jvrni.core.common.extensions

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.input.pointer.PointerInputScope
import kotlin.math.abs

suspend fun PointerInputScope.detectSwipe(
    swipeState: MutableState<Int> = mutableStateOf(-1),
    onSwipeLeft: () -> Unit = {},
    onSwipeRight: () -> Unit = {},
    onSwipeUp: () -> Unit = {},
    onSwipeDown: () -> Unit = {},
) = detectDragGestures(
    onDrag = { change, dragAmount ->
        change.consume()
        val (x, y) = dragAmount
        if (abs(x) > abs(y)) {
            when {
                x > 0 -> swipeState.value = 0
                x < 0 -> swipeState.value = 1
            }
        } else {
            when {
                y > 0 -> swipeState.value = 2
                y < 0 -> swipeState.value = 3
            }
        }
    },
    onDragEnd = {
        when (swipeState.value) {
            0 -> onSwipeRight()
            1 -> onSwipeLeft()
            2 -> onSwipeDown()
            3 -> onSwipeUp()
        }
    }
)