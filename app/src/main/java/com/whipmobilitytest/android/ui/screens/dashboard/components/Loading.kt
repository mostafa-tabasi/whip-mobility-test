package com.whipmobilitytest.android.ui.screens.dashboard.components


import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.intuit.sdp.R as DP


@Composable
fun Loading() {
  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier.fillMaxSize()
  ) { LoadingIndicator() }
}

private const val NUM_INDICATORS = 3
private const val INDICATOR_SIZE = 12
private const val ANIMATION_DURATION_MILLIS = 300
private const val ANIMATION_DELAY_MILLIS = ANIMATION_DURATION_MILLIS / NUM_INDICATORS

@Composable
private fun LoadingDot(
  color: Color,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .clip(shape = CircleShape)
      .background(color = color)
  )
}

@Composable
private fun LoadingIndicator(
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.colors.primary,
  indicatorSpacing: Dp = dimensionResource(id = DP.dimen._8sdp),
) {
  // 1
  val animatedValues = List(NUM_INDICATORS) { index ->
    var animatedValue by remember(key1 = Unit) { mutableStateOf(0f) }
    LaunchedEffect(key1 = Unit) {
      animate(
        initialValue = INDICATOR_SIZE / 2f,
        targetValue = -INDICATOR_SIZE / 2f,
        animationSpec = infiniteRepeatable(
          animation = tween(durationMillis = ANIMATION_DURATION_MILLIS),
          repeatMode = RepeatMode.Reverse,
          initialStartOffset = StartOffset(ANIMATION_DELAY_MILLIS * index),
        ),
      ) { value, _ -> animatedValue = value }
    }
    animatedValue
  }
  Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
    // 2
    animatedValues.forEach { animatedValue ->
      LoadingDot(
        modifier = Modifier
          .padding(horizontal = indicatorSpacing)
          .width(INDICATOR_SIZE.dp)
          .aspectRatio(1f)
          // 3
          .offset(y = animatedValue.dp),
        color = color,
      )
    }
  }
}
