package com.whipmobilitytest.android.data.repository

import com.whipmobilitytest.android.data.network.response.DashboardStatisticsData
import com.whipmobilitytest.android.data.network.response.NetworkResponse
import kotlinx.coroutines.delay

class OfflineStatisticsRepositoryImpl : StatisticsRepository {

    override suspend fun dashboardStatistics(scope: String): NetworkResponse<DashboardStatisticsData> {
        delay(1000)
        return NetworkResponse(
            200,
            NetworkResponse.GenericResponse(
                message = "Success",
                data = DashboardStatisticsData(
                    analytics = DashboardStatisticsData.Analytics(
                        job = DashboardStatisticsData.Analytics.Job(
                            title = "Jobs",
                            description = "A placeholder for job description",
                            items = arrayListOf(
                                DashboardStatisticsData.Analytics.JobAndServiceItem(
                                    title = "JobAndServiceItem 1 Title",
                                    description = "JobAndServiceItem 1 Description",
                                    growth = 5,
                                    avg = "7",
                                    total = 20,
                                ),
                                DashboardStatisticsData.Analytics.JobAndServiceItem(
                                    title = "JobAndServiceItem 2 Title",
                                    description = "JobAndServiceItem 2 Description JobAndServiceItem 2 Description ",
                                    growth = -2,
                                    avg = "12",
                                    total = 20,
                                ),
                                DashboardStatisticsData.Analytics.JobAndServiceItem(
                                    title = "JobAndServiceItem 3 Title",
                                    description = "JobAndServiceItem 3 Description",
                                    growth = 9,
                                    avg = "2.5",
                                    total = 20,
                                ),
                            )
                        ),
                        pieCharts = arrayListOf(
                            DashboardStatisticsData.Analytics.ChartData(
                                chartType = "pie",
                                title = "pie chart 1",
                                description = "pie chart description 1",
                                items = arrayListOf(
                                    DashboardStatisticsData.Analytics.PieChartItem("key 1", 2.3f),
                                    DashboardStatisticsData.Analytics.PieChartItem("key 2", 5.2f),
                                    DashboardStatisticsData.Analytics.PieChartItem("key 3", 7.9f),
                                )
                            ),
                            DashboardStatisticsData.Analytics.ChartData(
                                chartType = "pie",
                                title = "pie chart 2",
                                description = "pie chart description 2",
                                items = arrayListOf(
                                    DashboardStatisticsData.Analytics.PieChartItem("key 1", 11f),
                                    DashboardStatisticsData.Analytics.PieChartItem("key 2", 1.2f),
                                    DashboardStatisticsData.Analytics.PieChartItem("key 3", 6.9f),
                                )
                            ),
                            DashboardStatisticsData.Analytics.ChartData(
                                chartType = "pie",
                                title = "pie chart 3",
                                description = "pie chart description 3",
                                items = arrayListOf(
                                    DashboardStatisticsData.Analytics.PieChartItem("key 1", 2.3f),
                                    DashboardStatisticsData.Analytics.PieChartItem("key 2", 15.2f),
                                    DashboardStatisticsData.Analytics.PieChartItem("key 3", 4.9f),
                                )
                            ),
                        ),
                        rating = DashboardStatisticsData.Analytics.Rating(
                            title = "Rate",
                            description = "Rate Description",
                            avg = 4.4f,
                            items = DashboardStatisticsData.Analytics.Rating.Items(
                                4, 6, 2, 17, 24
                            )
                        ),
                        // service json is like job's
                        service = null,
                        lineCharts = arrayListOf(
                            arrayListOf(
                                DashboardStatisticsData.Analytics.ChartData(
                                    chartType = "line",
                                    title = "chart title 1",
                                    description = "chart description 1",
                                    items = arrayListOf(
                                        DashboardStatisticsData.Analytics.LineChartItem(
                                            key = "2024-05-01 08:45:22",
                                            value = arrayListOf(
                                                DashboardStatisticsData.Analytics.LineChartItem.Value("jobs", 14),
                                                DashboardStatisticsData.Analytics.LineChartItem.Value("services", 7),
                                            )
                                        ),
                                        DashboardStatisticsData.Analytics.LineChartItem(
                                            key = "2024-05-02 08:45:22",
                                            value = arrayListOf(
                                                DashboardStatisticsData.Analytics.LineChartItem.Value("jobs", 1),
                                                DashboardStatisticsData.Analytics.LineChartItem.Value("services", 17),
                                            )
                                        ),
                                        DashboardStatisticsData.Analytics.LineChartItem(
                                            key = "2024-05-03 08:45:22",
                                            value = arrayListOf(
                                                DashboardStatisticsData.Analytics.LineChartItem.Value("jobs", 26),
                                                DashboardStatisticsData.Analytics.LineChartItem.Value("services", 20),
                                            )
                                        ),
                                    )
                                ),
                            )
                        ),
                    )
                )
            )
        )
    }
}