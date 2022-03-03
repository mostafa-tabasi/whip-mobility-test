package com.whipmobilitytest.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
  primary = Blue700Dark,
  onPrimary = Color.White,
  secondary = BlueGrey300Dark,
  background = Color.White,
  onBackground = Grey700,
)

private val LightColorPalette = lightColors(
  primary = Blue700,
  onPrimary = Color.White,
  secondary = BlueGrey300,
  background = Color.White,
  onBackground = Grey700,

  /* Other default colors to override

    primaryVariant = Purple700,
    surface = Color.White,
    onSecondary = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun WhipMobilityTestTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colors = if (darkTheme) {
    // DarkColorPalette
    LightColorPalette
  } else {
    LightColorPalette
  }

  MaterialTheme(
    colors = colors,
    shapes = Shapes,
    content = content
  )
}