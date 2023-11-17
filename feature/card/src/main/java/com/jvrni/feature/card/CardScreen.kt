package com.jvrni.feature.card

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
import com.jvrni.core.designsystem.theme.AlboChallengeTheme
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
            CardBank(rotated = rotated.value, rotation = rotation)
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


/**
 * ENGLISH
 *
 * Card that changes color and data position according to rotation.
 *
 * .
 *
 * .
 *
 * SPANISH
 *
 * Tarjeta que cambia de color y posición de datos según la rotación.
 */
@Composable
private fun CardBank(
    rotated: Boolean,
    rotation: Float
) {
    val animateWhite by animateFloatAsState(
        targetValue = if (!rotated) 1f else 0f,
        animationSpec = tween(500), label = ""
    )

    val animateBlue by animateFloatAsState(
        targetValue = if (rotated) 1f else 0f,
        animationSpec = tween(500), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .align(if (rotated) Alignment.TopStart else Alignment.TopEnd)
                .padding(30.dp)
                .graphicsLayer {
                    alpha = if (!rotated) animateWhite else animateBlue
                    rotationY = rotation
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(60.dp)
                    .rotate(90f)
                    .graphicsLayer {
                        alpha = if (!rotated) animateWhite else animateBlue
                    },
                painter = painterResource(id = R.drawable.chip_card),
                contentDescription = ""
            )
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .graphicsLayer {
                        alpha = if (!rotated) animateWhite else animateBlue
                    },
                painter = painterResource(id = R.drawable.ic_contactless),
                tint = Color.Gray,
                contentDescription = ""
            )
        }

        Column(
            modifier = Modifier
                .align(if (rotated) Alignment.CenterEnd else Alignment.CenterStart)
                .padding(top = 30.dp, start = 10.dp),
            horizontalAlignment = if (rotated) Alignment.End else Alignment.Start
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .graphicsLayer {
                        alpha = if (!rotated) animateWhite else animateBlue

                        if (rotated)
                            rotationY = rotation
                    },
                painter = painterResource(id = if (!rotated) R.drawable.albo_logo_blue else R.drawable.albo_logo_white),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.graphicsLayer {
                    alpha = if (!rotated) animateWhite else animateBlue

                    if (rotated)
                        rotationY = rotation
                },
                text = stringResource(id = R.string.card_user_name),
                color = if (!rotated) Color.Black else Color.White
            )
        }

        Image(
            modifier = Modifier
                .size(100.dp)
                .align(if (rotated) Alignment.BottomStart else Alignment.BottomEnd)
                .graphicsLayer {
                    alpha = if (!rotated) animateWhite else animateBlue

                    if (rotated)
                        rotationY = rotation
                },
            painter = painterResource(id = R.drawable.mastercard_logo),
            contentDescription = ""
        )
    }
}

@Preview
@Composable
private fun PrevCardWithe() {
    AlboChallengeTheme {
        CardBank(false, 0f)
    }
}

@Preview
@Composable
private fun PrevCardBlack() {
    AlboChallengeTheme {
        CardBank(true, 180f)
    }
}
