package com.example.vodafoneairlinechallenge.data.airlines.remote

import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import com.example.vodafoneairlinechallenge.data.dataSourceManager.network.APIHelper
import retrofit2.Response
import javax.inject.Inject

class AirlinesRemoteDataSource @Inject constructor(
    private val apiHelper: APIHelper
) : AirlinesRemoteDataSourceInterface {
    override suspend fun getAllAirlines(): Response<List<AirlinesResponseItem>> =
        apiHelper.getAirlineList()
}