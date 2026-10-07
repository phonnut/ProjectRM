package com.example.testprojectfinal.wifi

interface WifiCallback {

    fun onScanResult(list: List<WifiData>)

}