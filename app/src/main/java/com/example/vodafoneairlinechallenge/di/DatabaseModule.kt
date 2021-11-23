package com.example.vodafoneairlinechallenge.di

import android.content.Context
import androidx.room.Room
import com.example.vodafoneairlinechallenge.data.dataSource.database.AppDatabase
import com.example.vodafoneairlinechallenge.data.dataSource.database.DAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): DAO {
        return appDatabase.channelDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "VodafoneAirlinesChallenge"
        ).fallbackToDestructiveMigration().build()
    }
}