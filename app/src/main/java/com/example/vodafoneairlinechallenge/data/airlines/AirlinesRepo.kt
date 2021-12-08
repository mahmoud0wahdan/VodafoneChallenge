package com.example.vodafoneairlinechallenge.data.airlines

import com.example.vodafoneairlinechallenge.data.airlines.dataSource.airlineCreation.request.AirLineCreationRequest
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.airlineCreation.response.AirLineCreationResponse
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import com.example.vodafoneairlinechallenge.data.airlines.local.AirlinesLocalDataSourceInterface
import com.example.vodafoneairlinechallenge.data.airlines.remote.AirlinesRemoteDataSourceInterface
import retrofit2.Response
import javax.inject.Inject

class AirlinesRepo @Inject constructor(
    private val airlinesRemoteDataSourceInterface: AirlinesRemoteDataSourceInterface,
    private val airlinesLocalDataSourceInterface: AirlinesLocalDataSourceInterface
) : AirlinesRepoInterface {
    override suspend fun getAirLinesListFromAPI(): Response<List<AirlinesResponseItem>> =
        airlinesRemoteDataSourceInterface.getAllAirlines()

    override suspend fun getAirLinesListFromDB(): List<AirlinesResponseItem> =
        airlinesLocalDataSourceInterface.getAllAirlines()

    override suspend fun saveAirLinesList(airLines: List<AirlinesResponseItem>) =
        airlinesLocalDataSourceInterface.saveAirlinesList(airLines)

    override suspend fun createAirlineNewRecord(airLineCreationRequest: AirLineCreationRequest): Response<AirLineCreationResponse> {
      return  airlinesRemoteDataSourceInterface.createAirlineNewRecord(airLineCreationRequest)
    }
}