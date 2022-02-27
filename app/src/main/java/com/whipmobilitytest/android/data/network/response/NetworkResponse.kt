package com.whipmobilitytest.android.data.network.response

import com.google.gson.annotations.SerializedName

data class NetworkResponse<T>(
  @SerializedName("httpStatus") var httpStatus: Int,
  @SerializedName("response") var response: GenericResponse<T>,
) {
  data class GenericResponse<T>(
    @SerializedName("message") var message: String,
    @SerializedName("data") var data: T,
  )
}

