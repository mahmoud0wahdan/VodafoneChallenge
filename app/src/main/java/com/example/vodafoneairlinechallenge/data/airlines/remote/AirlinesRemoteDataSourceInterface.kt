package com.example.vodafoneairlinechallenge.data.airlines.remote

import com.example.vodafoneairlinechallenge.data.airlines.dataSource.airlineCreation.request.AirLineCreationRequest
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.airlineCreation.response.AirLineCreationResponse
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import retrofit2.Response

interface AirlinesRemoteDataSourceInterface {
    suspend fun getAllAirlines(): Response<List<AirlinesResponseItem>>
    suspend fun createAirlineNewRecord(airLineCreationRequest: AirLineCreationRequest): Response<AirLineCreationResponse>
}