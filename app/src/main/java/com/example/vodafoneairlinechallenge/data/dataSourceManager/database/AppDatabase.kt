package com.example.vodafoneairlinechallenge.data.dataSourceManager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem

@Database(
    entities = [AirlinesResponseItem::class], version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun channelDao(): DAO
}