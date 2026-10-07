package com.example.testprojectfinal.wifi

data class WifiData(
    val ssid: String,
    val bssid: String,
    val rssi: Int,
    val frequency: Int,
    val timestamp: Long
)