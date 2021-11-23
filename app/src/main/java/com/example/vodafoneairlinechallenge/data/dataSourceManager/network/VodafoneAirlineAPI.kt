package com.example.vodafoneairlinechallenge.data.dataSourceManager.network

import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface VodafoneAirlineAPI {

    @GET("/v1/airlines")
    suspend fun getAirlineList(): Response<List<AirlinesResponseItem>>
}