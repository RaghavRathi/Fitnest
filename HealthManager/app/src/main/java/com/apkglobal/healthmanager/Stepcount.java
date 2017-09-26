package com.apkglobal.healthmanager;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dell on 7/31/2017.
 */

public class Stepcount extends Fragment implements SensorEventListener {
    TextView tv_stepcount, tv_caloriecount, tv_stepdetector;
    private SensorManager sManager;
    private Sensor sensor;
    private boolean activityRunning;
    private Thread detectorTimeStampUpdaterThread;

    private Handler handler;
    private long timestamp;

    private boolean isRunning = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stepcount, container, false);
        tv_caloriecount = (TextView) view.findViewById(R.id.tv_caloriecount);
        tv_stepcount = (TextView) view.findViewById(R.id.tv_stepcount);
        tv_stepdetector = (TextView) view.findViewById(R.id.tv_stepdetector);
        sManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(getActivity(),"Count sensor not available",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        activityRunning = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning){
            tv_stepcount.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}




