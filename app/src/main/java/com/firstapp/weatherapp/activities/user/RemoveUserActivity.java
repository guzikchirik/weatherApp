package com.firstapp.weatherapp.activities.user;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.firstapp.weatherapp.R;
import com.firstapp.weatherapp.tasks.UserService;

public class RemoveUserActivity extends Activity {

    private UserService userService;
    private EditText removeByValueField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_user);
        userService = new UserService(this);
        removeByValueField = findViewById(R.id.remove_by_value);
        Button remove_user_btn = findViewById(R.id.remove_user_btn);

        remove_user_btn.setOnClickListener(removeUserBtnListener());

    }

    private View.OnClickListener removeUserBtnListener() {
        return view -> {
            RadioButton removeByIdOpt = findViewById(R.id.remove_by_id_opt);
            RadioButton removeByLoginOpt = findViewById(R.id.remove_by_login_opt);
            String value = removeByValueField.getText().toString();
            if (removeByIdOpt.isChecked()) {
                userService.removeUserById(value);
            } else if (removeByLoginOpt.isChecked()) {
                userService.removeUserByLogin(value);
            } else {
                Toast.makeText(this, getResources().getString(R.string.choose_param), Toast.LENGTH_LONG).show();
            }
        };
    }
}