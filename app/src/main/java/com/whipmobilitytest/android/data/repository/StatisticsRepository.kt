package com.whipmobilitytest.android.data.repository

import com.whipmobilitytest.android.data.network.response.DashboardStatisticsData
import com.whipmobilitytest.android.data.network.response.NetworkResponse


interface StatisticsRepository {
    suspend fun dashboardStatistics(scope: String): NetworkResponse<DashboardStatisticsData>
}
