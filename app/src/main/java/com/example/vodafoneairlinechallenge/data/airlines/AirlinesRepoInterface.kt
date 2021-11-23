package com.example.vodafoneairlinechallenge.data.airlines

import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import retrofit2.Response

interface AirlinesRepoInterface {
    suspend fun getAirLinesListFromAPI(): Response<List<AirlinesResponseItem>>
    suspend fun getAirLinesListFromDB():List<AirlinesResponseItem>
    suspend fun saveAirLinesList(airLines: List<AirlinesResponseItem>)
}