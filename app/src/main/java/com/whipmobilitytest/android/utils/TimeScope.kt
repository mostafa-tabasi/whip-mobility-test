package com.whipmobilitytest.android.utils

import com.whipmobilitytest.android.R
import com.whipmobilitytest.android.utils.AppConstants.API_QUERY_ALL
import com.whipmobilitytest.android.utils.AppConstants.API_QUERY_LAST_MONTH
import com.whipmobilitytest.android.utils.AppConstants.API_QUERY_LAST_WEEK
import com.whipmobilitytest.android.utils.AppConstants.API_QUERY_TODAY

enum class TimeScope {
  ALL {
    override val apiQueryValue = API_QUERY_ALL
    override val title = R.string.all
  },
  TODAY {
    override val apiQueryValue = API_QUERY_TODAY
    override val title = R.string.today
  },
  LAST_WEEK {
    override val apiQueryValue = API_QUERY_LAST_WEEK
    override val title = R.string.last_week
  },
  LAST_MONTH {
    override val apiQueryValue = API_QUERY_LAST_MONTH
    override val title = R.string.last_month
  };

  abstract val apiQueryValue: String
  abstract val title: Int
}