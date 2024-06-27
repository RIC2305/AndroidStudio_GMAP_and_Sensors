package com.example.pr_tarea06_iglesiascostasroi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.pr_tarea06_iglesiascostasroi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensorTemperatura;
    private Sensor sensorHumedad;
    private TextView textViewTemperatura;
    private TextView textViewHumedad;
    private ActivityMainBinding binding;

    /*
    Creador : ROI IGLESIAS COSTAS
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Obtenemos todos los sensores del dispositivo.
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorTemperatura = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorHumedad = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        //ASignamos a variables los componentes de nuestra actividad (textView)
        textViewTemperatura = binding.textTemperatura;
        textViewHumedad = binding.textHumedad;

        //Controlo qué pasa si no tiene sensores
        if (sensorTemperatura == null) {

            Log.d("MainActivity", "El dispositivo no tiene sensor de temperatura");
        }

        if (sensorHumedad == null) {

            Log.d("MainActivity", "El dispositivo no tiene sensor de humedad");
        }
    }

    //Método cuando la actividad pasa a primer plano
    //Registramos los listeners para ambos sensores
    @Override
    protected void onResume() {
        super.onResume();
        //le pasamos el SensorEventListener que manejará los eventos del sensor
        //le pasamos el sensor al que estamos escuchando
        //indicamos el tiempo entre lecturas del sensor
        sensorManager.registerListener(this, sensorTemperatura, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorHumedad, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //Método cuando la actividad deja de estar en primer plano
    //"Desregistro" el Listener
    @Override
    protected void onPause() {
        super.onPause();
        //eliminamos los listeners registrados en onPause()
        sensorManager.unregisterListener(this); //this es la instancia actual
    }

    //Metodo que se ejecuta cuando los valores del sensor cambian
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            float temperatureValue = event.values[0];
            textViewTemperatura.setText(temperatureValue + " °C");
        } else if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            float humidityValue = event.values[0];
           textViewHumedad.setText(humidityValue + " %");
        }
    }

    //Este método se llama cuando se cambia la sensibilidad del sensor
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // ESte método no lo vamos a usar esta vez
    }
}