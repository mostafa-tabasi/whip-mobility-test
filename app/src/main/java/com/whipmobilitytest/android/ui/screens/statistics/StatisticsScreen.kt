package com.whipmobilitytest.android.ui.screens.statistics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.whipmobilitytest.android.ui.theme.WhipMobilityTestTheme

@Composable
fun StatisticsScreen() {
  val viewModel: StatisticsViewModel = viewModel()
  Column(Modifier.verticalScroll(rememberScrollState())) {
    AnimatedVisibility(visible = viewModel.uiState.loading) {
      Text(text = "LOADING...")
    }
    AnimatedVisibility(visible = viewModel.uiState.dataString.isNotEmpty()) {
      Text(text = viewModel.uiState.dataString)
    }
  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  WhipMobilityTestTheme {
    StatisticsScreen()
  }
}