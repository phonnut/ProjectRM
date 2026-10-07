package com.example.testprojectfinal.bluetooth

interface BleCallback {
    fun onBleResult(list: List<BleData>)
}