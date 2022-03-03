package com.whipmobilitytest.android.utils

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class ChartAxisDateValueFormatter : ValueFormatter() {

  /**
   * Format axis values and convert them to specific date format
   */
  override fun getAxisLabel(value: Float, axis: AxisBase?): String {
    //                         date format
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val date = Date(value.toLong())
    return sdf.format(date)
  }
}