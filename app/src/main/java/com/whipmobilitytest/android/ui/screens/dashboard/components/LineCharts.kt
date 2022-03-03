package com.whipmobilitytest.android.ui.screens.dashboard.components

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import com.whipmobilitytest.android.R
import com.whipmobilitytest.android.data.network.response.DashboardStatisticsData
import com.whipmobilitytest.android.ui.theme.Blue300
import com.whipmobilitytest.android.ui.theme.BlueGrey300
import com.whipmobilitytest.android.utils.ChartAxisDateValueFormatter
import com.whipmobilitytest.android.utils.CommonUtils
import com.intuit.sdp.R as DP

@Composable
fun LineCharts(
  headerTitle: String?,
  headerDescription: String?,
  data: List<DashboardStatisticsData.Analytics.LineChartItem>
) {
  // section header
  SectionHeader(title = headerTitle, description = headerDescription)
  // chart layout
  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
      .padding(dimensionResource(id = DP.dimen._8sdp))
      .fillMaxWidth()
      .height(dimensionResource(id = DP.dimen._250sdp))
      .border(
        BorderStroke(
          width = dimensionResource(id = DP.dimen._1sdp),
          color = MaterialTheme.colors.secondary.copy(alpha = 0.3f)
        ), shape = RoundedCornerShape(dimensionResource(id = DP.dimen._8sdp))
      )
      .padding(top = dimensionResource(id = DP.dimen._8sdp))
  ) {
    // chart view
    AndroidView(modifier = Modifier.padding(dimensionResource(id = DP.dimen._8sdp)), update = {
      updateLineChart(it, data)
    }, factory = { context ->
      LineChart(context).apply {
        layoutParams = LinearLayout.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT
        )
        initLineChart(this)
      }
    })
  }
}

/**
 * Initialize line chart for the first time
 */
fun initLineChart(chart: LineChart) {
  // background color
  chart.setBackgroundColor(Color.WHITE)

  // disable description text
  chart.description.isEnabled = false

  // enable touch gestures
  chart.setTouchEnabled(true)
  chart.setDrawGridBackground(false)

  // enable scaling and dragging
  chart.isDragEnabled = true

  // disable highlight on values
  chart.isHighlightPerTapEnabled = false
  chart.isHighlightPerDragEnabled = false

  // chart scale only on X axis
  chart.setScaleEnabled(true)
  chart.isScaleXEnabled = true
  chart.isScaleYEnabled = false

  // force pinch zoom along both axis
  chart.setPinchZoom(true)

  val xAxis: XAxis = chart.xAxis
  // vertical grid lines
  xAxis.enableGridDashedLine(10f, 10f, 0f)
  xAxis.valueFormatter = ChartAxisDateValueFormatter()
  xAxis.position = XAxis.XAxisPosition.BOTTOM
  xAxis.labelRotationAngle = 45f

  val yAxis: YAxis = chart.axisLeft
  // disable dual axis (only use LEFT axis)
  chart.axisRight.isEnabled = false

  // horizontal grid lines
  yAxis.enableGridDashedLine(10f, 10f, 0f)

  // get the legend (only possible after setting data)
  val l = chart.legend
  l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
  l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
  l.orientation = Legend.LegendOrientation.HORIZONTAL
  l.yOffset = 4f
  // draw legend entries as lines
  l.form = LegendForm.LINE
}

/**
 * Update line chart every time recomposition happens
 *
 * @param data line chart data
 */
private fun updateLineChart(
  chart: LineChart,
  data: List<DashboardStatisticsData.Analytics.LineChartItem>,
) {
  val context = chart.context
  val jobValues = ArrayList<Entry>()
  val serviceValues = ArrayList<Entry>()

  for (i in data.indices) {
    val item = data[i]
    // x axis values that represent time
    val dateTimestamp = CommonUtils.parseDateString(item.key)?.time?.toFloat() ?: 0f

    jobValues.add(
      Entry(
        dateTimestamp,
        // search on data to find the item that relate to job and get its value
        item.value.find { it.key == "jobs" }?.value?.toFloat() ?: 0f
      )
    )
    serviceValues.add(
      Entry(
        dateTimestamp,
        // search on data to find the item that relate to service and get its value
        item.value.find { it.key == "services" }?.value?.toFloat() ?: 0f
      )
    )
  }

  // job dataset
  val jobDataSet = buildDataSet(
    name = context.getString(R.string.jobs),
    values = jobValues,
    lineColor = Blue300.toArgb(),
    fillColor = ContextCompat.getDrawable(context, R.drawable.fade_blue)!!
  )

  // service dataset
  val serviceDataSet = buildDataSet(
    name = context.getString(R.string.services),
    values = serviceValues,
    lineColor = BlueGrey300.toArgb(),
    fillColor = ContextCompat.getDrawable(context, R.drawable.fade_blue_grey)!!
  )

  val dataSets = ArrayList<ILineDataSet>()
  // add the data sets
  dataSets.add(jobDataSet)
  dataSets.add(serviceDataSet)

  // set data
  chart.data = LineData(dataSets)
}

/**
 * build data set for line chart based on the given data
 *
 * @param name name of the data set
 * @param values data values
 * @param lineColor chart line color of data set
 * @param fillColor the color drawable which chart will fill with it
 */
private fun buildDataSet(
  name: String,
  values: List<Entry>,
  @ColorRes lineColor: Int,
  fillColor: Drawable
): LineDataSet {
  val dataSet = LineDataSet(values, name)
  dataSet.setDrawIcons(false)
  dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

  // line color
  dataSet.color = lineColor

  // draw points as solid circles
  dataSet.setDrawCircleHole(false)
  dataSet.setDrawCircles(false)

  // customize legend entry
  dataSet.formSize = 15f

  // text size of values
  dataSet.valueTextSize = 9f

  // set the filled area
  dataSet.setDrawFilled(true)

  // set color of filled area
  if (Utils.getSDKInt() >= 18) {
    // drawables only supported on api level 18 and above
    dataSet.fillDrawable = fillColor
  } else dataSet.fillColor = lineColor

  return dataSet
}