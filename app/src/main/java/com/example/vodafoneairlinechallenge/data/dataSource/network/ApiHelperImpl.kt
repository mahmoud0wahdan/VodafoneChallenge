package com.example.vodafoneairlinechallenge.data.dataSource.network

import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import com.example.vodafoneairlinechallenge.data.dataSource.network.APIHelper
import com.example.vodafoneairlinechallenge.data.dataSource.network.VodafoneAirlineAPI
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: VodafoneAirlineAPI
) : APIHelper {
    override suspend fun getAirlineList(): Response<List<AirlinesResponseItem>> =
        apiService.getAirlineList()
}