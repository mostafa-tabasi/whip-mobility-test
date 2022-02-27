package com.whipmobilitytest.android.data.network

import com.whipmobilitytest.android.data.network.response.DashboardStatisticsData
import com.whipmobilitytest.android.data.network.response.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StatisticsService {

  @GET("v10/analytic/dashboard/operation/mock")
  suspend fun dashboardStatistics(
    @Query("scope") scope: String,
  ): NetworkResponse<DashboardStatisticsData>
}











