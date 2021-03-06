package com.whipmobilitytest.android.ui.screens.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.whipmobilitytest.android.R
import com.whipmobilitytest.android.data.network.response.DashboardStatisticsData
import com.whipmobilitytest.android.ui.components.VerticalSpacer
import com.whipmobilitytest.android.ui.screens.dashboard.components.*
import com.whipmobilitytest.android.utils.TimeScope
import com.intuit.sdp.R as DP
import com.intuit.ssp.R as SP

@Composable
fun DashboardScreen(innerPaddingModifier: PaddingValues) {
  val viewModel: StatisticsViewModel = viewModel()

  Column(
    Modifier
      .fillMaxSize()
      .padding(innerPaddingModifier)
  ) {
    Header(
      isScopeMenuExpanded = viewModel.uiState.isScopeMenuExpanded,
      currentSelectedScope = viewModel.uiState.selectedTimeScope,
      onScopeMenuToggle = viewModel::onScopeMenuToggle,
      onScopeChange = viewModel::onTimeScopeChange
    )
    // Loading
    AnimatedVisibility(
      visible = viewModel.uiState.loading,
      enter = fadeIn(),
      exit = fadeOut()
    ) { Loading() }
    // Error message
    AnimatedVisibility(
      visible = viewModel.uiState.errorMessage.isNotEmpty(),
      enter = fadeIn(),
      exit = fadeOut()
    ) {
      ErrorMessage(
        message = viewModel.uiState.errorMessage,
        onTryAgainClick = viewModel::onTryAgainClick
      )
    }
    // Screen content
    if (viewModel.uiState.dashboardStatisticsData != null) AnimatedVisibility(
      visible = viewModel.uiState.dashboardStatisticsData != null,
      enter = fadeIn(),
      exit = fadeOut()
    ) {
      Content(
        modifier = Modifier.weight(1f),
        data = viewModel.uiState.dashboardStatisticsData!!.analytics,
        selectedPieChartIndex = viewModel.uiState.selectedPieChartIndex,
        onPieChartIndexChange = viewModel::onPieChartIndexChange,
        isRatingDetailsVisible = viewModel.uiState.isRatingsExpanded,
        onRatingDetailsToggle = viewModel::onRatingDetailsToggle,
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
  Column(
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
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
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
      // time scopes menu toggle button
      Row(
        modifier = Modifier
          .widthIn(min = dimensionResource(id = DP.dimen._80sdp))
          .border(
            border = BorderStroke(
              width = dimensionResource(id = DP.dimen._1sdp),
              color = MaterialTheme.colors.onPrimary.copy(alpha = 0.1f)
            ),
            shape = RoundedCornerShape(dimensionResource(id = DP.dimen._20sdp))
          )
          .clip(RoundedCornerShape(dimensionResource(id = DP.dimen._20sdp)))
          .clickable(
            onClick = onScopeMenuToggle,
            indication = (rememberRipple(color = Color.White)),
            interactionSource = remember { MutableInteractionSource() })
          .padding(dimensionResource(id = DP.dimen._6sdp))
          .animateContentSize(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
      ) {
        // animate icon rotation
        val rotationAngle by animateFloatAsState(
          targetValue = if (isScopeMenuExpanded) 180f else 0f,
          animationSpec = tween(durationMillis = 200, easing = FastOutLinearInEasing)
        )
        // menu state icon
        Icon(
          painter = painterResource(id = R.drawable.ic_arrow_down),
          modifier = Modifier.rotate(rotationAngle),
          contentDescription = null,
          tint = MaterialTheme.colors.onPrimary.copy(alpha = 0.7f)
        )
        // selected scope title
        Text(
          stringResource(id = currentSelectedScope.title),
          color = MaterialTheme.colors.onPrimary.copy(alpha = 0.7f),
          fontSize = dimensionResource(id = SP.dimen._13ssp).value.sp,
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(horizontal = dimensionResource(id = DP.dimen._6sdp))
        )
      }
    }
    // time scopes options
    AnimatedVisibility(visible = isScopeMenuExpanded) {
      Row(
        Modifier
          .fillMaxWidth()
          .padding(top = dimensionResource(id = DP.dimen._4sdp))
          .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
      ) {
        val timeScopes = TimeScope.values()
        timeScopes.forEachIndexed { index, timeScope ->
          // scope title
          Text(
            text = stringResource(id = timeScope.title),
            modifier = Modifier
              .widthIn(min = dimensionResource(id = DP.dimen._50sdp))
              .clip(RoundedCornerShape(dimensionResource(id = DP.dimen._20sdp)))
              .clickable(
                onClick = { onScopeChange(timeScope) },
                indication = (rememberRipple(color = Color.White)),
                interactionSource = remember { MutableInteractionSource() })
              .padding(
                vertical = dimensionResource(id = DP.dimen._8sdp),
                horizontal = dimensionResource(id = DP.dimen._12sdp)
              ),
            textAlign = TextAlign.Center,
            fontSize = dimensionResource(id = SP.dimen._13ssp).value.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colors.onPrimary
          )
          // divider between scope options
          // doesn't need after last item
          if (index < timeScopes.size - 1) Divider(
            Modifier
              .padding(horizontal = dimensionResource(id = DP.dimen._6sdp))
              .height(dimensionResource(id = DP.dimen._12sdp))
              .width(dimensionResource(id = DP.dimen._1sdp))
              .background(color = MaterialTheme.colors.onPrimary.copy(alpha = 0.3f))
          )
        }
      }
    }
  }
}

@Composable
fun ErrorMessage(message: String, onTryAgainClick: () -> Unit) {
  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
      .fillMaxSize()
      .padding(dimensionResource(id = DP.dimen._16sdp))
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      // error description
      Text(
        text = message,
        textAlign = TextAlign.Center,
        fontSize = dimensionResource(id = SP.dimen._17ssp).value.sp,
        color = MaterialTheme.colors.onBackground
      )
      VerticalSpacer(space = DP.dimen._16sdp)
      // try again button
      Text(
        text = stringResource(id = R.string.try_again),
        modifier = Modifier
          .border(
            border = BorderStroke(
              width = dimensionResource(id = DP.dimen._1sdp),
              color = MaterialTheme.colors.primary
            ),
            shape = RoundedCornerShape(dimensionResource(id = DP.dimen._24sdp))
          )
          .clip(shape = RoundedCornerShape(dimensionResource(id = DP.dimen._24sdp)))
          .clickable { onTryAgainClick() }
          .padding(
            horizontal = dimensionResource(id = DP.dimen._16sdp),
            vertical = dimensionResource(id = DP.dimen._8sdp)
          ),
        style = TextStyle(
          fontSize = dimensionResource(id = SP.dimen._17ssp).value.sp,
          fontWeight = FontWeight.Bold
        ),
        color = MaterialTheme.colors.primary
      )
    }
  }
}

@Composable
fun Content(
  modifier: Modifier,
  data: DashboardStatisticsData.Analytics,
  selectedPieChartIndex: Int,
  onPieChartIndexChange: (Int) -> Unit,
  isRatingDetailsVisible: Boolean,
  onRatingDetailsToggle: () -> Unit,
) {
  Column(
    modifier
      .verticalScroll(rememberScrollState())
      .padding(vertical = dimensionResource(id = DP.dimen._4sdp))
  ) {
    // pie charts content if exists
    if (!data.lineCharts?.get(0).isNullOrEmpty()) {
      data.lineCharts!![0].forEach {
        LineCharts(
          headerTitle = it.title,
          headerDescription = it.description,
          data = it.items,
        )
        SectionDivider()
      }
    }
    // jobs content if exists
    if (data.job != null) {
      JobsAndServices(
        headerTitle = data.job!!.title,
        headerDescription = data.job!!.description,
        data = data.job!!.items
      )
      SectionDivider()
    }
    // services content if exists
    if (data.service != null) {
      JobsAndServices(
        headerTitle = data.service!!.title,
        headerDescription = data.service!!.description,
        data = data.service!!.items
      )
      SectionDivider()
    }
    // pie charts content if exists
    if (data.pieCharts != null) {
      PieCharts(
        data = data.pieCharts!!,
        selectedIndex = selectedPieChartIndex,
        onIndexChange = onPieChartIndexChange
      )
      SectionDivider()
    }
    // ratings if exists
    if (data.rating != null) {
      Ratings(
        headerTitle = data.rating!!.title,
        headerDescription = data.rating!!.description,
        average = data.rating!!.avg,
        data = data.rating!!.items,
        isDetailsVisible = isRatingDetailsVisible,
        onDetailsToggle = onRatingDetailsToggle
      )
      SectionDivider()
    }
  }
}