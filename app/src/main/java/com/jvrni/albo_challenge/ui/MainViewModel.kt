package com.jvrni.albo_challenge.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrni.core.service.repository.ValueRepository
import com.jvrni.feature.card.CardState
import com.jvrni.feature.card.ProgressState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * ENGLISH
 *
 * Example using LiveData.
 *
 * The life cycle of a ViewModel is directly related to its scope.
 * A ViewModel remains in memory until the ViewModelStoreOwner whose rank disappears.
 *
 * In the case of an activity, when it ends for example ([MainActivity]).
 *
 * Typically, a ViewModel is requested the first time the system calls the onCreate() method of an activity object.
 * The system may call onCreate() multiple times during the existence of an activity, such as when rotating a device's screen.
 * ViewModel exists from the time a ViewModel is first requested until the activity terminates and is destroyed.
 *
 * .
 *
 * .
 *
 * SPANISH
 *
 * Ejemplo usando LiveData.
 *
 * El ciclo de vida de un ViewModel está directamente relacionado con su alcance.
 * Un ViewModel permanece en la memoria hasta que el ViewModelStoreOwner cuyo alcance desaparece.
 *
 * En el caso de una actividad, cuando finaliza por ejemplo ([MainActivity]).
 *
 * Normalmente, se solicita un ViewModel la primera vez que el sistema llama al método onCreate() de un objeto de actividad.
 * El sistema puede llamar a onCreate() varias veces durante la existencia de una actividad, como cuando se gira la pantalla de un dispositivo.
 * ViewModel existe desde el momento en que se solicita por primera vez un ViewModel hasta que la actividad finaliza y se destruye.
 */

class MainViewModel(private val repository: ValueRepository) : ViewModel() {

    /**
     * ENGLISH
     *
     * [_state], private variable to update data.
     *
     * [state], variable to observe the change of state.
     *
     * .
     *
     * .
     *
     * SPANISH
     *
     * [_state], variable privada para actualizar datos.
     *
     * [state], variable para observar el cambio de estado.
     */
    private val _state = MutableLiveData<CardState>()
    val state: LiveData<CardState> = _state

//    Example, we have the same code in our CardViewModel.
//    Ejemplo, tenemos el mismo código en nuestro CardViewModel.

//    init {
//        repository.getValue()
//            .onEach { list ->
//                _state.value = CardState(isLoading = false, isAnimation = true, list = list)
//
//                delay(1500)
//                _state.value = CardState(
//                    isLoading = false,
//                    isAnimation = false,
//                    progressState = ProgressState(actual = 1, quantity = list.size),
//                    list = list
//                )
//            }
//            .catch {
//                // todo error case
//            }
//            .launchIn(viewModelScope)
//    }
}