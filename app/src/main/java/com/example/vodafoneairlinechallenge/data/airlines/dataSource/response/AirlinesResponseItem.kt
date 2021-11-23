package com.example.vodafoneairlinechallenge.data.airlines.dataSource.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airlines")
data class AirlinesResponseItem(
    val country: String,
    val createdDate: String,
    val established: String,
    val head_quaters: String,

    @PrimaryKey
    val id: Double,
    val logo: String,
    val name: String,
    val slogan: String,
    val website: String
)