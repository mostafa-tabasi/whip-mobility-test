package com.whipmobilitytest.android.utils

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(
  private val connectivityManager: ConnectivityManager
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    if (!isConnectionAvailable())
      throw NoConnectionException("Make sure you have an active data connection")
    return chain.proceed(chain.request())
  }

  /**
   * Check if an active internet connection is available or not
   */
  private fun isConnectionAvailable(): Boolean {
    var result = false
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      val networkCapabilities = connectivityManager.activeNetwork ?: return false
      val actNw =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
      result = when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
      }
    } else {
      connectivityManager.run {
        connectivityManager.activeNetworkInfo?.run {
          result = when (type) {
            ConnectivityManager.TYPE_VPN -> true
            ConnectivityManager.TYPE_WIFI -> true
            ConnectivityManager.TYPE_MOBILE -> true
            ConnectivityManager.TYPE_ETHERNET -> true
            else -> false
          }
        }
      }
    }
    return result
  }

}