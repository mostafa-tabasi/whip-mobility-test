package com.whipmobilitytest.android.di

import android.content.Context
import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.whipmobilitytest.android.WhipMobilityTestApp
import com.whipmobilitytest.android.data.network.StatisticsService
import com.whipmobilitytest.android.data.repository.StatisticsRepository
import com.whipmobilitytest.android.data.repository.StatisticsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Singleton
  @Provides
  fun provideApplication(@ApplicationContext app: Context): WhipMobilityTestApp =
    app as WhipMobilityTestApp

  @Singleton
  @Provides
  fun provideStatisticsRepository(
    statisticsService: StatisticsService,
  ): StatisticsRepository = StatisticsRepositoryImpl(statisticsService = statisticsService)

  @Singleton
  @Provides
  fun provideGson(): Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

  @Singleton
  @Provides
  fun provideResources(@ApplicationContext context: Context): Resources = context.resources

}