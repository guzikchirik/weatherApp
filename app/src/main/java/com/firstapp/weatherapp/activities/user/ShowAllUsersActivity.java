package com.firstapp.weatherapp.activities.user;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.firstapp.weatherapp.R;
import com.firstapp.weatherapp.httpclient.HttpClientDeprecated;
import com.firstapp.weatherapp.model.User;
import com.firstapp.weatherapp.tasks.GetUsers;
import com.firstapp.weatherapp.tasks.UserService;
import com.firstapp.weatherapp.utils.PropertyReader;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShowAllUsersActivity extends Activity {
    private UserService userService;
    private static final String MY_SERVICE_URL = "http://192.168.100.52:4000/api/v1/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_users);

        UserService userService = new UserService(this);
        List<User> allUsers = userService.getAllUsers();

    }
}