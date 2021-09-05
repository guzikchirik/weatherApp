package com.firstapp.weatherapp.activities.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firstapp.weatherapp.R;

public class UserAddedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_added);
        TextView viewById = findViewById(R.id.user_added);
        String login = getIntent().getStringExtra("login");
        System.out.println("login  ===========================  "+login);
        viewById.setText(login);
    }
}