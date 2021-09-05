package com.firstapp.weatherapp.activities.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firstapp.weatherapp.R;

public class MainUserActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        Button addUserBtn = findViewById(R.id.add_user);
        Button removeUserBtn = findViewById(R.id.remove_user);
        Button showAllUsersBtn = findViewById(R.id.show_all_users);
        showAllUsersBtn.setOnClickListener(this);
        removeUserBtn.setOnClickListener(this);
        addUserBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_user:
                Intent addUser = new Intent("ru.startandroid.intent.action.addUser");
                startActivity(addUser);
                break;
            case R.id.remove_user:
                Intent removeUser = new Intent("ru.startandroid.intent.action.removeUser");
                startActivity(removeUser);
                break;
            case R.id.show_all_users:
                Intent showAllUsers = new Intent("ru.startandroid.intent.action.showAllUsers");
                startActivity(showAllUsers);
                break;
        }
    }
}