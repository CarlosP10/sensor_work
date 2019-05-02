package com.example.sensor_ejer

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle



class MainActivity : AppCompatActivity(){

    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    var cont:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        accelerometer()
        proximity()
        //rotationVector()
    }

    fun accelerometer(){
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (sensor == null)
            finish()
        Start()
    }

    fun proximity(){
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        if (sensor == null)
            finish()
        sensorManager.registerListener(sensorEveListenerProxi, sensor, SensorManager.SENSOR_DELAY_NORMAL)

        Start()
    }

    fun rotationVector(){
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        if (sensor == null)
            finish()
        sensorManager.registerListener(sensorEveListenerRoVec, sensor, SensorManager.SENSOR_DELAY_NORMAL)

        Start()
    }

    var sensorEveListenerRoVec =
        object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }

            override fun onSensorChanged(sensorevent: SensorEvent?) {
                val x: Float = sensorevent!!.values[0]
                val y = sensorevent!!.values[1]
                val z = sensorevent!!.values[2]
                println("Valor Giro X$x" + "Valor Giro Y$y" + "Valor Giro Z$z")
                if (x < 0 && y>0 && z>0) {
                    window.decorView.setBackgroundColor(Color.GRAY)
                } else if (x>0 && y<0 && z<0){
                    window.decorView.setBackgroundColor(Color.YELLOW)
                }
            }
        }

    var sensorEveListenerProxi = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }

            override fun onSensorChanged(sensorevent: SensorEvent?) {
                val x: Float = sensorevent!!.values[0]
                val y = sensorevent!!.values[1]
                val z = sensorevent!!.values[2]
                println("Valor GiroX$x")
                if (x < 1 && x > -1) {
                    window.decorView.setBackgroundColor(Color.BLACK)
                } else {
                    window.decorView.setBackgroundColor(Color.CYAN)
                }
            }
    }

    var sensorEventListener =
    object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

        override fun onSensorChanged(sensorevent: SensorEvent?) {
            val x: Float = sensorevent!!.values[0]
            val y = sensorevent!!.values[1]
            val z = sensorevent!!.values[2]
            println("Valor GiroX$x")
            if (x < -5 && cont == 0) {
                window.decorView.setBackgroundColor(Color.GREEN)
                cont++
            } else if (x > -5 && cont == 1) {
                cont++
                window.decorView.setBackgroundColor(Color.MAGENTA)

            }

            if (cont == 2) {
                cont = 0
            }
        }
    }

    private fun Start() {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private fun Stop() {
        sensorManager.unregisterListener(sensorEventListener)
    }

    override fun onPause() {
        super.onPause()
        Stop()
    }

    override fun onResume() {
        super.onResume()
        Start()
    }

}
