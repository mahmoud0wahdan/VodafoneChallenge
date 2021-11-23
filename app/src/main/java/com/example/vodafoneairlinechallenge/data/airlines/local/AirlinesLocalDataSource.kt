package com.example.vodafoneairlinechallenge.data.airlines.local

import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import com.example.vodafoneairlinechallenge.data.dataSource.database.DAO
import javax.inject.Inject

class AirlinesLocalDataSource @Inject constructor(
    private val dao: DAO
) : AirlinesLocalDataSourceInterface {
    override suspend fun saveAirlinesList(airlines: List<AirlinesResponseItem>) =
        dao.saveAirlineList(airlines)

    override suspend fun getAllAirlines(): List<AirlinesResponseItem> = dao.getAllAirlines()
}