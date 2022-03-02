package com.whipmobilitytest.android.ui.screens.dashboard

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.whipmobilitytest.android.ui.components.HorizontalSpacer
import com.whipmobilitytest.android.ui.components.VerticalSpacer
import com.whipmobilitytest.android.ui.screens.dashboard.components.PieCharts
import com.whipmobilitytest.android.ui.theme.Green600
import com.whipmobilitytest.android.ui.theme.Red600
import com.whipmobilitytest.android.utils.TimeScope
import com.intuit.sdp.R as DP
import com.intuit.ssp.R as SP

@Composable
fun DashboardScreen() {
  val viewModel: StatisticsViewModel = viewModel()

  Column(Modifier.fillMaxSize()) {
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
        color = MaterialTheme.colors.onSurface
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
  onPieChartIndexChange: (Int) -> Unit
) {
  Column(
    modifier
      .verticalScroll(rememberScrollState())
      .padding(vertical = dimensionResource(id = DP.dimen._4sdp))
  ) {
    // pie charts content if exists
    if (data.pieCharts != null) {
      PieCharts(
        data = data.pieCharts!!,
        selectedIndex = selectedPieChartIndex,
        onIndexChange = onPieChartIndexChange
      )
      VerticalSpacer(space = DP.dimen._8sdp)
    }
    // jobs content if exists
    if (data.job != null) Jobs(data.job!!)
  }
}

@Composable
fun Jobs(jobData: DashboardStatisticsData.Analytics.Job) {
  // var visible by remember { mutableStateOf(true) }
  Row(verticalAlignment = Alignment.CenterVertically) {
    Column(Modifier.weight(1f)) {
      // job title
      if (!jobData.title.isNullOrEmpty()) Text(
        text = jobData.title!!,
        modifier = Modifier
          .padding(horizontal = dimensionResource(id = DP.dimen._8sdp)),
        color = MaterialTheme.colors.onSurface,
        fontSize = dimensionResource(id = SP.dimen._14ssp).value.sp,
        fontWeight = FontWeight.ExtraBold
      )
      // job description
      if (!jobData.description.isNullOrEmpty()) Text(
        text = jobData.description!!,
        modifier = Modifier.padding(horizontal = dimensionResource(id = DP.dimen._8sdp)),
        color = MaterialTheme.colors.secondary,
        fontSize = dimensionResource(id = SP.dimen._12ssp).value.sp,
        fontWeight = FontWeight.Light
      )
    }
    // job layout expand button
    /*
    val rotationState by animateFloatAsState(
      targetValue = if (visible) 0f else 180f
    )
    Icon(painter = painterResource(id = R.drawable.ic_arrow_up),
      contentDescription = null,
      Modifier
        .padding(horizontal = dimensionResource(id = DP.dimen._6sdp))
        .clip(shape = CircleShape)
        .clickable { visible = !visible }
        .padding(dimensionResource(id = DP.dimen._6sdp))
        .rotate(rotationState),
      tint = MaterialTheme.colors.secondary
    )
    */
  }
  // job items
  AnimatedVisibility(
    enter = expandVertically(),
    exit = shrinkVertically(),
    // visible = visible
    visible = true
  ) {
    LazyRow(Modifier.fillMaxWidth()) {
      items(jobData.items) {
        Column(
          Modifier
            .padding(dimensionResource(id = DP.dimen._8sdp))
            .clip(shape = RoundedCornerShape(dimensionResource(id = DP.dimen._8sdp)))
            .background(
              color = when {
                it.growth ?: 0 > 0 -> Green600.copy(alpha = 0.1f)
                it.growth ?: 0 < 0 -> Red600.copy(alpha = 0.1f)
                else -> MaterialTheme.colors.secondary.copy(alpha = 0.1f)
              }
            )
            .padding(
              start = dimensionResource(id = DP.dimen._16sdp),
              end = dimensionResource(id = DP.dimen._16sdp),
              bottom = dimensionResource(id = DP.dimen._16sdp)
            )
        ) {
          Row(verticalAlignment = Alignment.Bottom) {
            // job item value
            Text(
              text = "${it.avg ?: it.total ?: ""}",
              modifier = Modifier.alignByBaseline(),
              color = MaterialTheme.colors.onSurface,
              style = MaterialTheme.typography.h2
            )
            if (it.growth != null) {
              HorizontalSpacer(space = DP.dimen._4sdp)
              Row(modifier = Modifier.alignByBaseline()) {
                if (it.growth != 0) {
                  // job item growth icon
                  Icon(
                    painter = painterResource(id = if (it.growth!! > 0) R.drawable.ic_growth_up else R.drawable.ic_growth_down),
                    contentDescription = null,
                    tint = if (it.growth!! > 0) Green600 else Red600
                  )
                  HorizontalSpacer(space = DP.dimen._2sdp)
                }
                // job item growth value
                Text(
                  text = it.growth.toString(),
                  color = when {
                    it.growth!! > 0 -> Green600
                    it.growth!! < 0 -> Red600
                    else -> MaterialTheme.colors.onSurface
                  },
                  fontSize = dimensionResource(id = SP.dimen._13ssp).value.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
          // job item title
          Text(
            text = it.title ?: "",
            color = MaterialTheme.colors.onSurface,
            fontSize = dimensionResource(id = SP.dimen._13ssp).value.sp,
            fontWeight = FontWeight.Bold
          )
          // job item description
          Text(
            text = it.description ?: "",
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
            fontSize = dimensionResource(id = SP.dimen._12ssp).value.sp,
            fontWeight = FontWeight.Light
          )
        }
      }
    }
  }
}