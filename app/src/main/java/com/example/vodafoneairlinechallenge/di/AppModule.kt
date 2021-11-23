package com.example.vodafoneairlinechallenge.di

import com.example.vodafoneairlinechallenge.data.airlines.AirlinesRepo
import com.example.vodafoneairlinechallenge.data.airlines.AirlinesRepoInterface
import com.example.vodafoneairlinechallenge.data.airlines.local.AirlinesLocalDataSource
import com.example.vodafoneairlinechallenge.data.airlines.local.AirlinesLocalDataSourceInterface
import com.example.vodafoneairlinechallenge.data.airlines.remote.AirlinesRemoteDataSource
import com.example.vodafoneairlinechallenge.data.airlines.remote.AirlinesRemoteDataSourceInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun getAirlinesRepo(
        tripsOneWaySearchRepo: AirlinesRepo
    ): AirlinesRepoInterface

    @Singleton
    @Binds
    abstract fun getAirlinesRemote(
        airlinesRemoteDataSource: AirlinesRemoteDataSource
    ): AirlinesRemoteDataSourceInterface

    @Singleton
    @Binds
    abstract fun getAirLinesLocalDataSource(
        lookupLocalSource: AirlinesLocalDataSource
    ): AirlinesLocalDataSourceInterface
}