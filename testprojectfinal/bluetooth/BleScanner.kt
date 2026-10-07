package com.example.testprojectfinal.bluetooth

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.BluetoothLeScanner
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class BleScanner(
    private val context: Context,
    private val callback: BleCallback
) {

    private val adapter = BluetoothAdapter.getDefaultAdapter()
    private val scanner: BluetoothLeScanner? = adapter.bluetoothLeScanner

    private val deviceMap = HashMap<String, BleData>()

    private val scanCallback = object : ScanCallback() {

        override fun onScanResult(callbackType: Int, result: ScanResult) {

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_SCAN
                ) != PackageManager.PERMISSION_GRANTED
            ) return

            val device = result.device

            deviceMap[device.address] = BleData(
                name = device.name ?: "Unknown",
                address = device.address,
                rssi = result.rssi,
                timestamp = System.currentTimeMillis()
            )

            callback.onBleResult(
                deviceMap.values.sortedByDescending { it.rssi }
            )

        }

    }

    fun startScanning() {

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        deviceMap.clear()

        scanner?.startScan(scanCallback)

    }

    fun stopScanning() {

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        scanner?.stopScan(scanCallback)

    }

}