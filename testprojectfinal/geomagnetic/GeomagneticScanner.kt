package com.example.testprojectfinal.geomagnetic

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class GeomagneticScanner(
    context: Context,
    private val callback: GeomagneticCallback
) : SensorEventListener {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val magneticSensor =
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    fun start() {

        magneticSensor?.let {

            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )

        }

    }

    fun stop() {

        sensorManager.unregisterListener(this)

    }

    override fun onSensorChanged(event: SensorEvent) {

        if (event.sensor.type != Sensor.TYPE_MAGNETIC_FIELD)
            return

        val x = event.values[0]

        val y = event.values[1]

        val z = event.values[2]

        val magnitude = sqrt(
            x * x +
                    y * y +
                    z * z
        )

        callback.onGeomagneticChanged(

            GeomagneticData(
                x,
                y,
                z,
                magnitude,
                System.currentTimeMillis()
            )

        )

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

}