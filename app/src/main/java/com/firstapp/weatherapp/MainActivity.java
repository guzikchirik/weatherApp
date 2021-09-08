package com.firstapp.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firstapp.weatherapp.activities.study.DataBaseActivity;
import com.firstapp.weatherapp.activities.study.StartWebBrowser;
import com.firstapp.weatherapp.activities.study.WebMapCallActivity;
import com.firstapp.weatherapp.activities.user.MainUserActivity;
import com.firstapp.weatherapp.tasks.GetWeather;
import com.firstapp.weatherapp.tasks.GetWeatherExecutor;
import com.firstapp.weatherapp.tasks.NetworkTask;
import com.firstapp.weatherapp.tasks.TaskRunner;

public class MainActivity extends AppCompatActivity {

    private EditText userField;
    private Button mainBtn;
    private ImageButton clearBtn;
    private TextView resultInfoDescription;

    private TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        logo = findViewById(R.id.logo);
        registerForContextMenu(logo);

        userField = findViewById(R.id.user_field);
        mainBtn = findViewById(R.id.main_btn);
        resultInfoDescription = findViewById(R.id.result_info_description);
        clearBtn = findViewById(R.id.clear_btn);

        Button showTimeBtn = findViewById(R.id.show_time_btn);
        showTimeBtn.setOnClickListener(onClickListenerShowTime());
        Button showDateBtn = findViewById(R.id.show_date_btn);
        showDateBtn.setOnClickListener(onClickListenerShowDate());

        clearBtn.setOnClickListener(onClickListenerClearBtn());
        mainBtn.setOnClickListener(onClickListenerFindOutWeather());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 1, 2, getResources().getString(R.string.red)); // (groupId, itemId, itemOrder, Title)
        menu.add(0, 2, 3, getResources().getString(R.string.green));
        menu.add(0, 3, 4, getResources().getString(R.string.blue));
        menu.add(0, 4, 5, getResources().getString(R.string.black));
        menu.add(0, 5, 1, getResources().getString(R.string.white));
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                logo.setTextColor(getResources().getColor(R.color.red));
                break;
            case 2:
                logo.setTextColor(getResources().getColor(R.color.green));
                break;
            case 3:
                logo.setTextColor(getResources().getColor(R.color.blue));
                break;
            case 4:
                logo.setTextColor(getResources().getColor(R.color.black));
                break;
            case 5:
                logo.setTextColor(getResources().getColor(R.color.white));
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_functional_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.weather: {

                break;
            }
            case R.id.work_with_user_account: {
                Intent intent = new Intent(this, MainUserActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.study: {
                Intent intent = new Intent(this, WebMapCallActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.myBrowser: {
                Intent intent = new Intent(this, StartWebBrowser.class);
                startActivity(intent);
                break;
            }
            case R.id.data_base: {
                Intent intent = new Intent(this, DataBaseActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.quit: {
                finish();
                break;
            }
        }
            return super.onOptionsItemSelected(item);
    }

    public View.OnClickListener onClickListenerShowTime() {
        return View -> {
            Intent showTime = new Intent("ru.startandroid.intent.action.showtime");
            startActivity(showTime);
        };
    }

    public View.OnClickListener onClickListenerShowDate() {
        return View -> {
            Intent showDate = new Intent("ru.startandroid.intent.action.showdate");
            startActivity(showDate);
        };
    }

    public View.OnClickListener onClickListenerFindOutWeather() {
        return (View) -> {
            if (userField.getText().toString().trim().isEmpty()) {
                Toast.makeText(MainActivity.this, R.string.no_user_input, Toast.LENGTH_LONG).show();
            } else {
                showClearBtn();
                String actualCity = userField.getText().toString();
//                String openWeatherUrl = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&lang=ru&units=metric", actualCity, API_KEY);
                new GetWeather(this).execute(actualCity);
//                new GetWeatherExecutor(this).getWeather(openWeatherUrl);
//                TaskRunner runner = new TaskRunner();
//
//                runner.executeAsync(new NetworkTask(this));
            }
            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(userField.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        };
    }

    public View.OnClickListener onClickListenerClearBtn() {
        return (View) -> {
            userField.setText("");
            hideClearBtn();
        };
    }

    private void showClearBtn() {
        clearBtn.setVisibility(View.VISIBLE);
    }

    private void hideClearBtn() {
        clearBtn.setVisibility(View.INVISIBLE);
    }

//    public void goToNewScreen(View view) {
//        Intent intent = new Intent(this, AddUserActivity.class);
//        startActivities(new Intent[]{intent});
//    }
}