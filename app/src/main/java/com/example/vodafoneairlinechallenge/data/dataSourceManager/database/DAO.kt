package com.example.vodafoneairlinechallenge.data.dataSourceManager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem

@Dao
interface DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAirlineList(cites: List<AirlinesResponseItem>)

    @Query("SELECT * FROM airlines")
    suspend fun getAllAirlines(): List<AirlinesResponseItem>

}