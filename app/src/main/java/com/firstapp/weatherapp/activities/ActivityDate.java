package com.firstapp.weatherapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.firstapp.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityDate extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String time = sdf.format(new Date(System.currentTimeMillis()));

        TextView tvTime = (TextView) findViewById(R.id.tvDate);
        tvTime.setText(time);
    }
}