package com.apkglobal.healthmanager;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

public class WaterTwo extends Activity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_two);
        imageView = (ImageView) findViewById(R.id.imageView);

    }
}