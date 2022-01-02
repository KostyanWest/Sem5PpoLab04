package com.example.game

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager

import android.hardware.SensorEvent

import android.hardware.SensorEventListener


class OrientationData() : SensorEventListener {
    private val manager: SensorManager
    private val accelerometer: Sensor
    private val magnometer: Sensor
    private var accelOutput: FloatArray? = null
    private var magOutput: FloatArray? = null
    val orientation = FloatArray(3)
    var startOrientation: FloatArray? = null
        private set

    fun newGame() {
        startOrientation = null
    }

    fun register() {
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        manager.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_GAME)
    }

    fun pause() {
        manager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) accelOutput =
            event.values else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) magOutput =
            event.values
        if (accelOutput != null && magOutput != null) {
            val R = FloatArray(9)
            val I = FloatArray(9)
            val success = SensorManager.getRotationMatrix(R, I, accelOutput, magOutput)
            if (success) {
                SensorManager.getOrientation(R, orientation)
                if (startOrientation == null) {
                    startOrientation = FloatArray(orientation.size)
                    System.arraycopy(orientation, 0, startOrientation, 0, orientation.size)
                }
            }
        }
    }

    init {
        manager = Constants.CURRENT_CONTEXT!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }
}