package com.whipmobilitytest.android.utils

import java.text.SimpleDateFormat
import java.util.*

object CommonUtils {

  /**
   * Convert a date string in specific format (yyyy-MM-dd) to date object
   *
   * @param date date in string
   * @return date object
   */
  fun parseDateString(date: String?): Date? =
    if (date.isNullOrEmpty()) null else SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(date)
}