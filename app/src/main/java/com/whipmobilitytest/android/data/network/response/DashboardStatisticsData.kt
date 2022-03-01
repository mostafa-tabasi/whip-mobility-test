package com.whipmobilitytest.android.data.network.response

import com.google.gson.annotations.SerializedName

data class DashboardStatisticsData(
  @SerializedName("analytics") val analytics: Analytics
) {
  data class Analytics(
    @SerializedName("job") var job: Job?,
    @SerializedName("pieCharts") var pieCharts: List<ChartData<List<PieChartItem>>>?,
    @SerializedName("rating") var rating: Rating?,
    @SerializedName("service") var service: Service?,
    @SerializedName("lineCharts") var lineCharts: List<List<ChartData<List<LineChartItem>>>>?,
  ) {
    data class Job(
      @SerializedName("description") var description: String?,
      @SerializedName("items") var items: List<Item>,
      @SerializedName("title") var title: String?
    ) {
      data class Item(
        @SerializedName("description") var description: String?,
        @SerializedName("growth") var growth: Int?,
        @SerializedName("title") var title: String?,
        @SerializedName("total") var total: Int?,
        @SerializedName("avg") var avg: String?,
      )
    }

    data class PieChartItem(
      @SerializedName("key") var key: String?,
      @SerializedName("value") var value: Float?,
    )

    data class Rating(
      @SerializedName("avg") var avg: Float?,
      @SerializedName("description") var description: String?,
      @SerializedName("items") var items: Items?,
      @SerializedName("title") var title: String?,
    ) {
      data class Items(
        @SerializedName("1") var rate1: Int?,
        @SerializedName("2") var rate2: Int?,
        @SerializedName("3") var rate3: Int?,
        @SerializedName("4") var rate4: Int?,
        @SerializedName("5") var rate5: Int?,
      )
    }

    data class Service(
      @SerializedName("description") var description: String?,
      @SerializedName("items") var items: List<Item>,
      @SerializedName("title") var title: String?,
    ) {
      data class Item(
        @SerializedName("description") var description: String?,
        @SerializedName("growth") var growth: Int?,
        @SerializedName("title") var title: String?,
        @SerializedName("total") var total: Int?,
        @SerializedName("avg") var avg: String?,
      )
    }

    data class LineChartItem(
      @SerializedName("key") var key: String?,
      @SerializedName("value") var value: List<Value>,
    ) {
      data class Value(
        @SerializedName("key") var key: String?,
        @SerializedName("value") var value: Int?,
      )
    }

    data class ChartData<T>(
      @SerializedName("chartType") var chartType: String?,
      @SerializedName("description") var description: String?,
      @SerializedName("items") var items: T,
      @SerializedName("title") var title: String?
    )
  }
}