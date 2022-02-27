package com.whipmobilitytest.android.di

import com.google.gson.GsonBuilder
import com.whipmobilitytest.android.data.network.StatisticsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideStatisticsService(): StatisticsService {
        return Retrofit.Builder()
            .baseUrl("https://skyrim.whipmobility.io/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(StatisticsService::class.java)
    }

}