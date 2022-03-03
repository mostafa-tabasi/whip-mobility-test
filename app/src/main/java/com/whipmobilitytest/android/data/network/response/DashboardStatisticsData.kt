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
      @SerializedName("items") var items: List<JobAndServiceItem>,
      @SerializedName("title") var title: String?
    )

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
      ) {
        fun toPairList() = arrayListOf<Pair<String, Int?>>().apply {
          add(Pair("1", rate1))
          add(Pair("2", rate2))
          add(Pair("3", rate3))
          add(Pair("4", rate4))
          add(Pair("5", rate5))
        }

        /**
         * calculate number of all votes
         */
        fun numberOfVotes() = toPairList().sumOf { it.second ?: 0 }.toLong()
      }
    }

    data class Service(
      @SerializedName("description") var description: String?,
      @SerializedName("items") var items: List<JobAndServiceItem>,
      @SerializedName("title") var title: String?,
    )

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

    data class JobAndServiceItem(
      @SerializedName("description") var description: String?,
      @SerializedName("growth") var growth: Int?,
      @SerializedName("title") var title: String?,
      @SerializedName("total") var total: Int?,
      @SerializedName("avg") var avg: String?,
    )
  }
}