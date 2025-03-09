package com.example.lab1

import java.io.Serializable

data class RouteData(
    val id: Long = System.currentTimeMillis(),
    val departurePoint: String,
    val arrivalPoint: String,
    val timeSlot: String
) : Serializable