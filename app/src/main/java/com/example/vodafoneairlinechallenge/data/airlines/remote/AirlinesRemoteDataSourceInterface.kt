package com.example.vodafoneairlinechallenge.data.airlines.remote

import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import retrofit2.Response

interface AirlinesRemoteDataSourceInterface {
    suspend fun getAllAirlines(): Response<List<AirlinesResponseItem>>
}