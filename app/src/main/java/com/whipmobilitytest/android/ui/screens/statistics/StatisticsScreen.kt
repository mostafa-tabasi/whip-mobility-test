package com.whipmobilitytest.android.ui.screens.statistics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.whipmobilitytest.android.R
import com.whipmobilitytest.android.ui.components.VerticalSpacer
import com.whipmobilitytest.android.utils.TimeScope
import com.intuit.sdp.R as DP
import com.intuit.ssp.R as SP

@Composable
fun StatisticsScreen() {
  val viewModel: StatisticsViewModel = viewModel()

  Column(Modifier.fillMaxSize()) {
    Header(
      isScopeMenuExpanded = viewModel.uiState.isScopeMenuExpanded,
      currentSelectedScope = viewModel.uiState.currentSelectedTimeScope,
      onScopeMenuToggle = viewModel::onScopeMenuToggle,
      onScopeChange = viewModel::onTimeScopeChange
    )
    // Loading
    AnimatedVisibility(
      visible = viewModel.uiState.loading,
      enter = fadeIn(),
      exit = fadeOut()
    ) { Loading() }

    // Screen content
    AnimatedVisibility(
      visible = viewModel.uiState.dataString.isNotEmpty(),
      enter = fadeIn(),
      exit = fadeOut()
    ) {
      Content(
        modifier = Modifier.weight(1f),
        dataString = viewModel.uiState.dataString
      )
    }
  }
}

@Composable
fun Header(
  isScopeMenuExpanded: Boolean,
  currentSelectedScope: TimeScope,
  onScopeMenuToggle: () -> Unit,
  onScopeChange: (TimeScope) -> Unit
) {
  // header layout
  Row(
    Modifier
      .fillMaxWidth()
      .clip(
        RoundedCornerShape(
          bottomStartPercent = 30,
          bottomEndPercent = 30
        )
      )
      .background(MaterialTheme.colors.primary)
      .padding(dimensionResource(id = DP.dimen._10sdp)),
    verticalAlignment = Alignment.CenterVertically
  ) {
    // header title
    Text(
      text = stringResource(id = R.string.dashboard),
      Modifier.weight(1f),
      style = TextStyle(
        fontSize = dimensionResource(id = SP.dimen._20ssp).value.sp,
        fontWeight = FontWeight.Bold
      ),
      color = MaterialTheme.colors.onPrimary
    )
    // time scopes drop down menu
    Column {
      // drop down toggle button
      Text(
        stringResource(id = currentSelectedScope.title),
        color = MaterialTheme.colors.onPrimary,
        fontSize = dimensionResource(id = SP.dimen._13ssp).value.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
          .widthIn(min = dimensionResource(id = DP.dimen._80sdp))
          .border(
            border = BorderStroke(
              width = dimensionResource(id = DP.dimen._1sdp),
              color = MaterialTheme.colors.onPrimary
            ),
            shape = RoundedCornerShape(dimensionResource(id = DP.dimen._20sdp))
          )
          .clip(RoundedCornerShape(dimensionResource(id = DP.dimen._20sdp)))
          .clickable { onScopeMenuToggle() }
          .padding(
            horizontal = dimensionResource(id = DP.dimen._16sdp),
            vertical = dimensionResource(id = DP.dimen._6sdp)
          )
          .animateContentSize())
      VerticalSpacer(space = DP.dimen._2sdp)
      // drop down menu items
      DropdownMenu(
        expanded = isScopeMenuExpanded,
        onDismissRequest = { onScopeMenuToggle() }) {
        TimeScope.values().forEach {
          DropdownMenuItem(onClick = {
            onScopeChange(it)
            onScopeMenuToggle()
          }) { Text(text = stringResource(id = it.title)) }
        }
      }
    }
  }
}

@Composable
fun Loading() {
  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier.fillMaxSize()
  ) { CircularProgressIndicator() }
}

@Composable
fun Content(modifier: Modifier, dataString: String) {
  Text(text = dataString)
}