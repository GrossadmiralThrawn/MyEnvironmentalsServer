package com.environmentalserver.models



data class ControllerData(
    val controllerID: String,
    val temperature:  Float,
    val humidity:     Float,
    val pressure:     Float,
    val uvIndex:      Float,
)
