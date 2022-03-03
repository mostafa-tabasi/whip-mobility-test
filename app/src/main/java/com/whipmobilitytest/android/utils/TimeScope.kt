package com.whipmobilitytest.android.utils

import com.whipmobilitytest.android.R
import com.whipmobilitytest.android.utils.AppConstants.API_QUERY_ALL
import com.whipmobilitytest.android.utils.AppConstants.API_QUERY_LAST_30_DAYS
import com.whipmobilitytest.android.utils.AppConstants.API_QUERY_LAST_7_DAYS
import com.whipmobilitytest.android.utils.AppConstants.API_QUERY_TODAY

enum class TimeScope {
  TODAY {
    override val apiQueryValue = API_QUERY_TODAY
    override val title = R.string.today
  },
  LAST_WEEK {
    override val apiQueryValue = API_QUERY_LAST_7_DAYS
    override val title = R.string.last_seven_days
  },
  LAST_MONTH {
    override val apiQueryValue = API_QUERY_LAST_30_DAYS
    override val title = R.string.last_thirty_days
  },
  ALL {
    override val apiQueryValue = API_QUERY_ALL
    override val title = R.string.all
  };

  abstract val apiQueryValue: String
  abstract val title: Int
}