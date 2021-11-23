package com.example.vodafoneairlinechallenge.data.dataSourceManager.network

import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import retrofit2.Response

interface APIHelper {
    suspend fun getAirlineList(): Response<List<AirlinesResponseItem>>
}