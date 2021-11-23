package com.example.vodafoneairlinechallenge.data.dataSource.network

import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface VodafoneAirlineAPI {

    @GET("/v1/airlines")
    suspend fun getAirlineList(): Response<List<AirlinesResponseItem>>
}