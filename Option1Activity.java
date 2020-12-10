package com.example.proyecto2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Option1Activity extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    int whip=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option1);
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(sensor==null)
            finish();

        sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x=sensorEvent.values[0];
                System.out.println("valor giro"+x);
                if(x<-5 && whip==0)
                {
                  whip++;
                  getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }else if(x>5 && whip==1)
                {
                   getWindow().getDecorView().setBackgroundColor(Color.GRAY);
                }
                if(whip==2){
                    sound();
                    whip=0;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        start();
    }
    private void sound(){
        MediaPlayer mediaPlayer=MediaPlayer.create(this, R.raw.moneda);
        mediaPlayer.start();
    }
             private void start(){
             sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
             private void stop(){
             sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stop();

    }

    @Override
    protected void onResume() {
        super.onResume();
        start();
    }
}