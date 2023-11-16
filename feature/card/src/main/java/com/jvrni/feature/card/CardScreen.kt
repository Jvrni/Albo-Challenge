package com.jvrni.feature.card

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jvrni.core.common.extensions.detectSwipe
import com.jvrni.core.designsystem.components.StepProgress
import com.jvrni.core.designsystem.components.StepProgressEntity
import com.jvrni.core.designsystem.theme.primary
import org.koin.androidx.compose.koinViewModel

/**
 * ENGLISH
 *
 * By collecting [CardState] with StateFlow, we can do this way, so we don't need to collect our activity data.
 *
 * .
 *
 * .
 *
 * SPANISH
 *
 * Recopilando [CardState] con StateFlow, podemos hacerlo de esta manera para que no necesitemos recopilar los datos en nuestra Actividad.
 */
@Composable
fun CardScreen(viewModel: CardViewModel = koinViewModel()) {
    val state = viewModel.state.collectAsState().value

    val rotated = remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (rotated.value) 180f else 0f,
        animationSpec = tween(500),
        label = ""
    )

    Box(
        modifier = Modifier
            .pointerInput(Unit) {
                detectSwipe(
                    onSwipeLeft = {
                        viewModel.swipeLeft()
                        rotated.value = true
                    },
                    onSwipeRight = {
                        viewModel.swipeRight()
                        rotated.value = false
                    }
                )
            }
            .fillMaxSize()
            .background(Color.White)
            .padding(15.dp)
    ) {
        StepProgress(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            quantity = state.progressState.quantity,
            actual = state.progressState.actual,
            entity = StepProgressEntity(stringResource(id = R.string.description_progress))
        )

        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxHeight(0.7f)
                .fillMaxWidth(0.9f)
                .padding(10.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 8 * density
                },
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (!rotated.value) Color.White else primary
            )
        ) {

        }
    }

    if (state.isLoading) LoadingScreen()
    if (state.isAnimation) TutorialScreen()
}

/**
 * ENGLISH
 *
 * Loading screen while making the request.
 *
 * .
 *
 * .
 *
 * SPANISH
 *
 * Cargando pantalla mientras realiza la solicitud.
 */
@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = primary)
    }
}

/**
 * ENGLISH
 *
 * Tutorial screen, indicating the swipe to change card.
 *
 * .
 *
 * .
 *
 * SPANISH
 *
 * Pantalla de tutorial, indicando el deslizamiento para cambiar de tarjeta.
 */
@Composable
private fun TutorialScreen() {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.tutorial_animation))

            LottieAnimation(
                modifier = Modifier.align(Alignment.CenterEnd),
                composition = composition,
                iterations = LottieConstants.IterateForever,
                clipSpec = LottieClipSpec.Progress(0f, 1f)
            )
        }
    }
}
