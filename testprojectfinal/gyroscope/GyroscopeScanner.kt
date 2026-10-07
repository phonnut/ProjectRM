package com.example.testprojectfinal.gyroscope

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class GyroscopeScanner(
    context: Context,
    private val callback: GyroscopeCallback
) : SensorEventListener {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val gyroscope =
        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    fun start() {

        gyroscope?.let {

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

        if (event.sensor.type != Sensor.TYPE_GYROSCOPE)
            return

        callback.onGyroscopeChanged(

            GyroscopeData(
                event.values[0],
                event.values[1],
                event.values[2],
                System.currentTimeMillis()
            )

        )

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

}