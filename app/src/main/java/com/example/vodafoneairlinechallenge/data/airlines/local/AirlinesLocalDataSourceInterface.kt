package com.example.vodafoneairlinechallenge.data.airlines.local

import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem

interface AirlinesLocalDataSourceInterface {
    suspend fun saveAirlinesList(airlines: List<AirlinesResponseItem>)
    suspend fun getAllAirlines(): List<AirlinesResponseItem>
}