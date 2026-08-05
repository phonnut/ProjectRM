package com.example.testprojectfinal

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat

import com.example.testprojectfinal.accelerometer.AccelerometerCallback
import com.example.testprojectfinal.accelerometer.AccelerometerData
import com.example.testprojectfinal.accelerometer.AccelerometerScanner

import com.example.testprojectfinal.bluetooth.BleCallback
import com.example.testprojectfinal.bluetooth.BleData
import com.example.testprojectfinal.bluetooth.BleScanner

import com.example.testprojectfinal.geomagnetic.GeomagneticCallback
import com.example.testprojectfinal.geomagnetic.GeomagneticData
import com.example.testprojectfinal.geomagnetic.GeomagneticScanner

import com.example.testprojectfinal.gyroscope.GyroscopeCallback
import com.example.testprojectfinal.gyroscope.GyroscopeData
import com.example.testprojectfinal.gyroscope.GyroscopeScanner

import com.example.testprojectfinal.ui.theme.TestProjectFinalTheme

import com.example.testprojectfinal.wifi.WifiCallback
import com.example.testprojectfinal.wifi.WifiData
import com.example.testprojectfinal.wifi.WifiScanner

class MainActivity : ComponentActivity(), WifiCallback {

    //==========================
    // WiFi
    //==========================
    private lateinit var wifiScanner: WifiScanner
    private val wifiList = mutableStateListOf<WifiData>()

    //==========================
    // Bluetooth
    //==========================
    private lateinit var bleScanner: BleScanner
    private val bleList = mutableStateListOf<BleData>()

    //==========================
    // Geomagnetic
    //==========================
    private lateinit var geomagneticScanner: GeomagneticScanner
    private var geomagneticData by mutableStateOf<GeomagneticData?>(null)

    //==========================
    // Accelerometer
    //==========================
    private lateinit var accelerometerScanner: AccelerometerScanner
    private var accelerometerData by mutableStateOf<AccelerometerData?>(null)

    //==========================
    // Gyroscope
    //==========================
    private lateinit var gyroscopeScanner: GyroscopeScanner
    private var gyroscopeData by mutableStateOf<GyroscopeData?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //-------------------------
        // WiFi
        //-------------------------
        wifiScanner = WifiScanner(this, this)

        //-------------------------
        // Bluetooth
        //-------------------------
        bleScanner = BleScanner(
            this,
            object : BleCallback {

                override fun onBleResult(list: List<BleData>) {

                    runOnUiThread {

                        bleList.clear()
                        bleList.addAll(list)

                    }

                }

            }
        )

        //-------------------------
        // Geomagnetic
        //-------------------------
        geomagneticScanner = GeomagneticScanner(
            this,
            object : GeomagneticCallback {

                override fun onGeomagneticChanged(data: GeomagneticData) {

                    runOnUiThread {

                        geomagneticData = data

                    }

                }

            }
        )

        //-------------------------
        // Accelerometer
        //-------------------------
        accelerometerScanner = AccelerometerScanner(
            this,
            object : AccelerometerCallback {

                override fun onAccelerometerChanged(data: AccelerometerData) {

                    runOnUiThread {

                        accelerometerData = data

                    }

                }

            }
        )

        //-------------------------
        // Gyroscope
        //-------------------------
        gyroscopeScanner = GyroscopeScanner(
            this,
            object : GyroscopeCallback {

                override fun onGyroscopeChanged(data: GyroscopeData) {

                    runOnUiThread {

                        gyroscopeData = data

                    }

                }

            }
        )

        checkPermission()

        setContent {

            TestProjectFinalTheme {

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    WifiScreen(wifiList)

                    BleScreen(bleList)

                    GeomagneticScreen(geomagneticData)

                    AccelerometerScreen(accelerometerData)

                    GyroscopeScreen(gyroscopeData)

                }

            }

        }

    }

    //==========================
    // WiFi Callback
    //==========================
    override fun onScanResult(list: List<WifiData>) {

        runOnUiThread {

            wifiList.clear()

            wifiList.addAll(list)

        }

    }
    //==========================
    // Permission
    //==========================
    private val permissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val granted = permissions.values.all { it }

            if (granted) {

                // WiFi
                wifiScanner.startScanning()

                // Bluetooth
                bleScanner.startScanning()

                // Geomagnetic
                geomagneticScanner.start()

                // Accelerometer
                accelerometerScanner.start()

                // Gyroscope
                gyroscopeScanner.start()

            }

        }

    private fun checkPermission() {

        val permissionList = mutableListOf<String>()

        permissionList.add(
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        permissionList.add(
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            permissionList.add(
                Manifest.permission.NEARBY_WIFI_DEVICES
            )

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            permissionList.add(
                Manifest.permission.BLUETOOTH_SCAN
            )

            permissionList.add(
                Manifest.permission.BLUETOOTH_CONNECT
            )

        }

        val granted = permissionList.all {

            ContextCompat.checkSelfPermission(
                this,
                it
            ) == PackageManager.PERMISSION_GRANTED

        }

        if (granted) {

            // WiFi
            wifiScanner.startScanning()

            // Bluetooth
            bleScanner.startScanning()

            // Geomagnetic
            geomagneticScanner.start()

            // Accelerometer
            accelerometerScanner.start()

            // Gyroscope
            gyroscopeScanner.start()

        } else {

            permissionLauncher.launch(
                permissionList.toTypedArray()
            )

        }

    }

    override fun onDestroy() {

        // WiFi
        wifiScanner.stopScanning()

        // Bluetooth
        bleScanner.stopScanning()

        // Geomagnetic
        geomagneticScanner.stop()

        // Accelerometer
        accelerometerScanner.stop()

        // Gyroscope
        gyroscopeScanner.stop()

        super.onDestroy()

    }

}