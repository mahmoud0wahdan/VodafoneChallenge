package com.example.vodafoneairlinechallenge.data.airlines.dataSource.airlineCreation.request

data class AirLineCreationRequest(
    val country: String,
    val established: String,
    val head_quaters: String,
    val id: Int?,
    val logo: String?,
    val name: String,
    val slogan: String,
    val website: String?
)