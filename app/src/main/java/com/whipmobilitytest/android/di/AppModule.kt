package com.whipmobilitytest.android.di

import android.content.Context
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
  ): StatisticsRepository = StatisticsRepositoryImpl(statisticsService = statisticsService,)

}