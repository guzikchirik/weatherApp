package com.firstapp.weatherapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.firstapp.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityTime extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date(System.currentTimeMillis()));

        TextView tvTime = (TextView) findViewById(R.id.tvTime);
        tvTime.setText(time);
    }
}