package com.whipmobilitytest.android.data.network.response

import com.google.gson.annotations.SerializedName

data class DashboardStatisticsData(
  @SerializedName("analytics") val analytics: Analytics
){
  data class Analytics(
    @SerializedName("job") var job: Job?,
    @SerializedName("pieCharts") var pieCharts: ArrayList<PieCharts>?,
    @SerializedName("rating") var rating: Rating?,
    @SerializedName("service") var service: Service?,
    @SerializedName("lineCharts") var lineCharts: ArrayList<ArrayList<LineCharts>>,
  )

  data class Job(
    @SerializedName("description") var description: String?,
    @SerializedName("items") var items: ArrayList<Items>,
    @SerializedName("title") var title: String?
  )

  data class PieCharts(
    @SerializedName("chartType") var chartType: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("items") var items: ArrayList<Items>,
    @SerializedName("title") var title: String?
  )

  data class Rating(
    @SerializedName("avg") var avg: Int?,
    @SerializedName("description") var description: String?,
    @SerializedName("items") var items: Items?,
    @SerializedName("title") var title: String?,
  )

  data class Service(
    @SerializedName("description") var description: String?,
    @SerializedName("items") var items: ArrayList<Items>,
    @SerializedName("title") var title: String?,
  )

  data class Items(
    @SerializedName("key") var key: String?,
    @SerializedName("value") var value: ArrayList<Value>,
  )

  data class LineCharts(
    @SerializedName("chartType") var chartType: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("items") var items: ArrayList<Items>,
    @SerializedName("title") var title: String?,
  )

  data class Value(
    @SerializedName("key") var key: String?,
    @SerializedName("value") var value: Int?,
  )
}