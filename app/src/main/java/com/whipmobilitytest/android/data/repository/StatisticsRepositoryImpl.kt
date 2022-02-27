package com.whipmobilitytest.android.data.repository

import com.whipmobilitytest.android.data.network.StatisticsService
import com.whipmobilitytest.android.data.network.response.DashboardStatisticsData
import com.whipmobilitytest.android.data.network.response.NetworkResponse

class StatisticsRepositoryImpl(
  private val statisticsService: StatisticsService,
) : StatisticsRepository {

  override suspend fun dashboardStatistics(scope: String): NetworkResponse<DashboardStatisticsData> =
    statisticsService.dashboardStatistics(scope)

}