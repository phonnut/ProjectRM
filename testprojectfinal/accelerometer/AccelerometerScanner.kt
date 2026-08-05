package com.example.testprojectfinal.accelerometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class AccelerometerScanner(
    context: Context,
    private val callback: AccelerometerCallback
) : SensorEventListener {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val accelerometer =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    fun start() {

        accelerometer?.let {

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

        if (event.sensor.type != Sensor.TYPE_ACCELEROMETER)
            return

        callback.onAccelerometerChanged(

            AccelerometerData(
                event.values[0],
                event.values[1],
                event.values[2],
                System.currentTimeMillis()
            )

        )

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

}