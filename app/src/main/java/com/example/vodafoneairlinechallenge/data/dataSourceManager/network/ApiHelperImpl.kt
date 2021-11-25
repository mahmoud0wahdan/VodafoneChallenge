package com.example.vodafoneairlinechallenge.data.dataSourceManager.network

import com.example.vodafoneairlinechallenge.data.airlines.dataSource.airlineCreation.request.AirLineCreationRequest
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.airlineCreation.response.AirLineCreationResponse
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: VodafoneAirlineAPI
) : APIHelper {
    override suspend fun getAirlineList(): Response<List<AirlinesResponseItem>> =
       apiService.getAirlineList()

    override suspend fun createAirlineNewRecord( airLineCreationRequest: AirLineCreationRequest): Response<AirLineCreationResponse> =
        apiService.createAirlineNewRecord(airLineCreationRequest)
}