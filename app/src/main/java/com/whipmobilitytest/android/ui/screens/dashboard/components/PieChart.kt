package com.whipmobilitytest.android.ui.screens.dashboard.components

import android.graphics.Color
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.intuit.sdp.R
import com.whipmobilitytest.android.data.network.response.DashboardStatisticsData
import com.whipmobilitytest.android.ui.theme.*

@Composable
fun PieCharts(
  data: List<DashboardStatisticsData.Analytics.ChartData<List<DashboardStatisticsData.Analytics.PieChartItem>>>,
  selectedIndex: Int,
  onIndexChange: (Int) -> Unit
) {
  Row(
    Modifier
      .horizontalScroll(rememberScrollState())
      .padding(vertical = dimensionResource(id = R.dimen._6sdp))
  ) {
    data.forEachIndexed { index, chartData ->
      Column(horizontalAlignment = CenterHorizontally) {
        // chart tab title
        Text(
          chartData.title ?: "",
          color = if (index == selectedIndex) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
          fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._13ssp).value.sp,
          textAlign = TextAlign.Center,
          modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen._4sdp))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen._20sdp)))
            .clickable { onIndexChange(index) }
            .background(
              color = if (index == selectedIndex) MaterialTheme.colors.primary.copy(alpha = 0.2f)
              else MaterialTheme.colors.secondary.copy(alpha = 0.2f)
            )
            .padding(
              horizontal = dimensionResource(id = R.dimen._16sdp),
              vertical = dimensionResource(id = R.dimen._6sdp)
            ))
      }
    }
  }
  // chart layout
  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
      .padding(horizontal = dimensionResource(id = R.dimen._8sdp))
      .padding(top = dimensionResource(id = R.dimen._4sdp))
      .fillMaxWidth()
      .height(dimensionResource(id = R.dimen._250sdp))
      .border(
        BorderStroke(
          width = dimensionResource(id = R.dimen._1sdp),
          color = MaterialTheme.colors.secondary.copy(alpha = 0.3f)
        ), shape = RoundedCornerShape(dimensionResource(id = R.dimen._8sdp))
      )
  ) {
    // chart view
    Crossfade(targetState = data[selectedIndex]) { chartData ->
      AndroidView(modifier = Modifier.padding(dimensionResource(id = R.dimen._8sdp)), update = {
        updatePieChart(it, chartData)
      }, factory = { context ->
        PieChart(context).apply {
          layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
          )
          initPieChart(this)
        }
      })
    }
  }
  // chart tab description
  Crossfade(targetState = data[selectedIndex].description) {
    Text(
      it ?: "",
      color = MaterialTheme.colors.secondary,
      fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._11ssp).value.sp,
      textAlign = TextAlign.Center,
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = dimensionResource(id = R.dimen._4sdp))
    )
  }
}

/**
 * Initialize pie chart for the first time
 */
fun initPieChart(chart: PieChart) {
  chart.description.isEnabled = false

  // radius of the center hole in percent of maximum radius
  chart.holeRadius = 1f
  chart.transparentCircleRadius = 1f

  chart.legend.isEnabled = false
}

/**
 * Update pie chart every time recomposition happen
 *
 * @param data pie chart data
 */
fun updatePieChart(
  chart: PieChart,
  data: DashboardStatisticsData.Analytics.ChartData<List<DashboardStatisticsData.Analytics.PieChartItem>>
) {

  val count = data.items.size
  val entries = ArrayList<PieEntry>()

  // build chart data based on the data that we have
  for (i in 0 until count) {
    val item = data.items[i]
    entries.add(PieEntry(item.value ?: 0.toFloat(), item.key ?: ""))
  }

  val ds = PieDataSet(entries, "")
  ds.colors = arrayListOf(
    Blue200.toArgb(),
    BlueGrey200.toArgb(),
    Blue300.toArgb(),
    BlueGrey300.toArgb(),
    Blue500.toArgb(),
    BlueGrey500.toArgb(),
    Blue700.toArgb(),
    BlueGrey700.toArgb(),
  )
  ds.sliceSpace = 2f
  ds.valueTextColor = Color.BLACK
  ds.label
  ds.valueTextSize = 12f

  val d = PieData(ds)

  chart.data = d
  chart.invalidate()
}