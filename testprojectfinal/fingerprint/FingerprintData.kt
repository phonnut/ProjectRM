package com.example.testprojectfinal.fingerprint

data class FingerprintData(

    //==============================
    // Basic Information
    //==============================
    val timestamp: Long,

    val area: String,

    //==============================
    // WiFi
    //==============================
    val wifiSSID: String,

    val wifiBSSID: String,

    val wifiRSSI: Int,

    //==============================
    // Bluetooth
    //==============================
    val bleName: String,

    val bleAddress: String,

    val bleRSSI: Int,

    //==============================
    // Geomagnetic
    //==============================
    val magX: Float,

    val magY: Float,

    val magZ: Float,

    //==============================
    // Accelerometer
    //==============================
    val accX: Float,

    val accY: Float,

    val accZ: Float,

    //==============================
    // Gyroscope
    //==============================
    val gyroX: Float,

    val gyroY: Float,

    val gyroZ: Float

)