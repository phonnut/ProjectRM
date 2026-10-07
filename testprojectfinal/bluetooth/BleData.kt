package com.example.testprojectfinal.bluetooth

data class BleData(
    val name: String?,
    val address: String,
    val rssi: Int,
    val timestamp: Long
)