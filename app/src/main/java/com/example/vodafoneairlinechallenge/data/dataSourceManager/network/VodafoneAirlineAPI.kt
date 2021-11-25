package com.example.vodafoneairlinechallenge.data.dataSourceManager.network

import com.example.vodafoneairlinechallenge.data.airlines.dataSource.airlineCreation.request.AirLineCreationRequest
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.airlineCreation.response.AirLineCreationResponse
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface VodafoneAirlineAPI {

    @GET("/v1/airlines")
    suspend fun getAirlineList(): Response<List<AirlinesResponseItem>>

    @POST("/v1/airlines")
    suspend fun createAirlineNewRecord(@Body airLineCreationRequest: AirLineCreationRequest):Response<AirLineCreationResponse>
}