package com.whipmobilitytest.android.di

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.GsonBuilder
import com.whipmobilitytest.android.data.network.StatisticsService
import com.whipmobilitytest.android.utils.AppConstants.API_BASE_URL
import com.whipmobilitytest.android.utils.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Singleton
  @Provides
  fun provideNetworkConnectionInterceptor(
    @ApplicationContext context: Context
  ): NetworkConnectionInterceptor {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return NetworkConnectionInterceptor(cm)
  }

  @Singleton
  @Provides
  fun provideOkHttpClient(
    networkConnectionInterceptor: NetworkConnectionInterceptor
  ): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(networkConnectionInterceptor)
    .build()

  @Singleton
  @Provides
  fun provideStatisticsService(
    okHttpClient: OkHttpClient,
  ): StatisticsService {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl(API_BASE_URL)
      .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
      .build()
      .create(StatisticsService::class.java)
  }

}