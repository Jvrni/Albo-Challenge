package com.jvrni.core.designsystem.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jvrni.core.designsystem.theme.AlboChallengeTheme
import com.jvrni.core.designsystem.theme.Colors
import com.jvrni.core.designsystem.theme.primary
import kotlinx.coroutines.delay

private val progressHeight = 28.dp

@Composable
fun StepProgress(
    modifier: Modifier = Modifier,
    height: Dp = progressHeight,
    color: Color = Colors.primary,
    dividerColor: Color = Color.White,
    quantity: Int,
    actual: Int,
    entity: StepProgressEntity
) {
    if (quantity <= 0) return

    val steps = 1f / quantity

    val isAnimated = remember { mutableStateOf(false) }
    val animatedFloat = animateFloatAsState(
        targetValue = if (isAnimated.value) steps * actual else 0f,
        animationSpec = tween(1000), label = ""
    )

    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (progress, text) = createRefs()

        LinearProgressIndicator(
            modifier = Modifier
                .constrainAs(progress) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(height)
                .background(Color.Transparent, CircleShape)
                .clip(CircleShape),
            progress = animatedFloat.value,
            color = color,
            trackColor = ProgressIndicatorDefaults.linearTrackColor.copy(alpha = 0.5f)
        )

        repeat(quantity) { position ->
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (divider) = createRefs()
                val guideLine = createGuidelineFromStart(steps * position)

                if (position != 0)
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(height)
                            .constrainAs(divider) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(guideLine)
                            }
                            .background(dividerColor)
                    )
            }
        }

        Text(
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(progress.bottom)
                    end.linkTo(progress.end)
                }
                .padding(top = 7.dp, end = 3.dp),
            text = "$actual/$quantity ${entity.description}",
            color = Colors.text
        )
    }

    LaunchedEffect(key1 = Unit, block = {
        delay(500)
        isAnimated.value = true
    })
}

data class StepProgressEntity(
    val description: String
)


@Preview
@Composable
private fun PrevStepProgress() {
    AlboChallengeTheme {
        StepProgress(
            quantity = 5,
            actual = 0,
            entity = StepProgressEntity("")
        )
    }
}
