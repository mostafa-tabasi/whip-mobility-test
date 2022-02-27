package com.whipmobilitytest.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
  primary = Blue600Dark,
  onPrimary = Color.White,
  secondary = BlueGrey200Dark,
  background = Color.White,
  onBackground = Color.Black,
)

private val LightColorPalette = lightColors(
  primary = Blue600,
  onPrimary = Color.White,
  secondary = BlueGrey200,
  background = Color.White,
  onBackground = Color.Black,

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
    DarkColorPalette
  } else {
    LightColorPalette
  }

  MaterialTheme(
    colors = colors,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}