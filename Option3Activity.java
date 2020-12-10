package com.example.proyecto2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Option3Activity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private View root;
    private float valormax;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option3);
        root = findViewById(R.id.root);
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        txt = findViewById(R.id.txtLum);

        if(lightSensor==null)
        {
            Toast.makeText(this,"El dispositivo no tiene sensor de luz!",Toast.LENGTH_SHORT).show();
            finish();
        }
        valormax = lightSensor.getMaximumRange();

        lightEventListener = new SensorEventListener()
        {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent)
            {

                    float value = sensorEvent.values[0];
                    getSupportActionBar().setTitle("Luminosidad: " + value + " lx");
                    txt.setText("" + sensorEvent.values[0]);
                    int newValue = (int) (255f * value / valormax);
                    root.setBackgroundColor(Color.rgb(newValue, newValue, newValue));


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i)
            {

            }
        };
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sensorManager.unregisterListener(lightEventListener);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sensorManager.registerListener(lightEventListener,lightSensor,SensorManager.SENSOR_DELAY_FASTEST);

    }
}