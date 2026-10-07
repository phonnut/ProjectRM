package com.example.testprojectfinal.wifi

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat

class WifiScanner(
    private val context: Context,
    private val callback: WifiCallback
) {

    private val wifiManager =
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    private val handler = Handler(Looper.getMainLooper())

    private var scanning = false

    private val scanRunnable = object : Runnable {
        override fun run() {

            if (!scanning) return

            wifiManager.startScan()

            handler.postDelayed(this,1000)

        }
    }

    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            if (ActivityCompat.checkSelfPermission(
                    this@WifiScanner.context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) return

            val list = mutableListOf<WifiData>()

            wifiManager.scanResults.forEach {

                list.add(

                    WifiData(
                        ssid = it.SSID,
                        bssid = it.BSSID,
                        rssi = it.level,
                        frequency = it.frequency,
                        timestamp = System.currentTimeMillis()
                    )

                )

            }

            callback.onScanResult(
                list.sortedByDescending { it.rssi }
            )

        }

    }

    fun startScanning() {

        if(scanning) return

        scanning = true

        context.registerReceiver(
            receiver,
            IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        )

        handler.post(scanRunnable)

    }

    fun stopScanning() {

        scanning = false

        handler.removeCallbacks(scanRunnable)

        try{
            context.unregisterReceiver(receiver)
        }catch (_:Exception){}

    }

}