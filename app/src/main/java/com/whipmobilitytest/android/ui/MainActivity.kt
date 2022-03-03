package com.whipmobilitytest.android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import com.whipmobilitytest.android.ui.screens.dashboard.DashboardScreen
import com.whipmobilitytest.android.ui.theme.WhipMobilityTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      WhipMobilityTestTheme {
        Scaffold(
          backgroundColor = MaterialTheme.colors.background,
        ) { innerPaddingModifier ->
          DashboardScreen(innerPaddingModifier)
        }
      }
    }
  }
}