package com.jvrni.feature.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrni.core.service.repository.ValueRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update


/**
 * ENGLISH
 *
 * Example using StateFlow.
 *
 * We don't need to have the [MainViewModel] for example.
 *
 * .
 *
 * .
 *
 * SPANISH
 *
 * Ejemplo usando StateFlow.
 *
 * No necesitamos tener el [MainViewModel] por ejemplo.
 */
class CardViewModel(private val repository: ValueRepository) : ViewModel() {

    private val _state = MutableStateFlow(CardState())
    val state: StateFlow<CardState> = _state

    init {
        repository.getValue()
            .onEach { list ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isAnimation = true,
                        progressState = ProgressState(actual = 1, quantity = list.size),
                        list = list
                    )
                }

                delay(3000)
                _state.update {
                    it.copy(isAnimation = false)
                }
            }
            .catch {
                // todo error case
            }
            .launchIn(viewModelScope)
    }

    /**
     * ENGLISH
     *
     * Example using StateFlow.
     *
     * When you swipe, we call these functions to update StepProgress.
     *
     * .
     *
     * .
     *
     * SPANISH
     *
     * Cuando deslizas el dedo, llamamos a estas funciones para actualizar StepProgress.
     */
    fun swipeRight() {
        _state.update {
            if (it.progressState.actual > 1)
                it.copy(
                    progressState = ProgressState(
                        actual = it.progressState.actual - 1,
                        quantity = it.progressState.quantity
                    )
                )
            else
                it
        }
    }

    fun swipeLeft() {
        _state.update {
            if (it.progressState.actual < it.progressState.quantity)
                it.copy(
                    progressState = ProgressState(
                        actual = it.progressState.actual + 1,
                        quantity = it.progressState.quantity
                    )
                )
            else
                it
        }
    }
}

data class CardState(
    val isLoading: Boolean = true,
    val isAnimation: Boolean = false,
    val progressState: ProgressState = ProgressState(),
    val list: List<String> = emptyList()
)

data class ProgressState(
    val actual: Int = 0,
    val quantity: Int = 0,
)
