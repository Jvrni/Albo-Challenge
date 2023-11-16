package com.jvrni.albo_challenge.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.jvrni.core.designsystem.theme.AlboChallengeTheme
import com.jvrni.feature.card.CardScreen
import com.jvrni.feature.card.CardViewModel
import com.jvrni.feature.card.CardState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * ENGLISH
         *
         * Create a ViewModel the first time the system calls an activity's onCreate() method.
         * Re-created activities receive the same [MainViewModel] instance created by the first activity.
         *
         * This is an example using LiveData without collectAsState, because we can get the data directly on our [CardScreen] with StateFlow or LiveData too.
         *
         * In [CardScreen] and [CardViewModel] we have an example using StateFlow.
         *
         * .
         *
         * .
         *
         * SPANISH
         *
         * Crea un ViewModel la primera vez que el sistema llama al método onCreate() de una actividad.
         * Las actividades recreadas reciben la misma instancia de [MainViewModel] creada por la primera actividad.
         *
         * Este es un ejemplo que usa LiveData sin collectAsState, porque también podemos obtener los datos directamente en nuestro [CardScreen] con StateFlow o LiveData.
         *
         * En [CardScreen] y [CardViewModel] tenemos un ejemplo usando StateFlow.
         */

        val viewModel by viewModel<MainViewModel>()

        setContent {
            val state = remember { mutableStateOf(CardState()) }

            AlboChallengeTheme {
                viewModel.state.observe(this) { result -> state.value = result }
                CardScreen()
            }
        }
    }
}
