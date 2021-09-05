package com.firstapp.weatherapp.tasks;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.firstapp.weatherapp.MainActivity;
import com.firstapp.weatherapp.R;
import com.firstapp.weatherapp.httpclient.HttpClientDeprecated;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetWeatherExecutor {

    private MainActivity mainActivity;
    private TextView statusInfo;
    private TableLayout result_info_table;

    ExecutorService executor = Executors.newFixedThreadPool(2);
    Handler handler = new Handler(Looper.getMainLooper());

    public GetWeatherExecutor(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void getWeather(String url) {
        executor.execute(() -> {
            String result = HttpClientDeprecated.getResponse(url);

            handler.post(() -> {
                result_info_table = mainActivity.findViewById(R.id.result_info_table);
                result_info_table.setVisibility(View.VISIBLE);
                TextView userField = mainActivity.findViewById(R.id.user_field);
                TextView weatherIn = mainActivity.findViewById(R.id.result_info_weather_in);
                TextView city = mainActivity.findViewById(R.id.result_info_city);
                TextView resultInfoDescription = mainActivity.findViewById(R.id.result_info_description);
                TextView temp_desc = mainActivity.findViewById(R.id.result_info_temp_desc);
                TextView temp_val = mainActivity.findViewById(R.id.result_info_temp_val);
                TextView feel_like_desc = mainActivity.findViewById(R.id.result_info_feel_like_desc);
                TextView feel_like_val = mainActivity.findViewById(R.id.result_info_feel_like_val);
                TextView pressure_desc = mainActivity.findViewById(R.id.result_info_pressure_desc);
                TextView pressure_val = mainActivity.findViewById(R.id.result_info_pressure_val);
                TextView wind_speed_desc = mainActivity.findViewById(R.id.result_info_wind_speed_desc);
                TextView wind_speed_val = mainActivity.findViewById(R.id.result_info_wind_speed_val);
                TextView humidity_desc = mainActivity.findViewById(R.id.result_info_humidity_desc);
                TextView humidity_val = mainActivity.findViewById(R.id.result_info_humidity_val);
                try {
                    JSONObject object = new JSONObject(result);
                    weatherIn.setText(mainActivity.getResources().getString(R.string.weather_in));
                    String cityStr = userField.getText().toString().toLowerCase();
                    city.setText(String.format("%s%s", cityStr.substring(0, 1).toUpperCase(), cityStr.substring(1)));
                    resultInfoDescription.setText(object.getJSONArray("weather").getJSONObject(0).getString("description"));
                    temp_desc.setText(mainActivity.getResources().getString(R.string.temperature));
                    temp_val.setText(String.format("%s°C", object.getJSONObject("main").getString("temp")));
                    feel_like_desc.setText(mainActivity.getResources().getString(R.string.temperature_feels_like));
                    feel_like_val.setText(String.format("%s°C", object.getJSONObject("main").getString("feels_like")));
                    pressure_desc.setText(mainActivity.getResources().getString(R.string.pressure));
                    pressure_val.setText(String.format("%s мбар", object.getJSONObject("main").getString("pressure")));
                    wind_speed_desc.setText(mainActivity.getResources().getString(R.string.wind_speed));
                    wind_speed_val.setText(String.format("%s м/с", object.getJSONObject("wind").getString("speed")));
                    humidity_desc.setText(mainActivity.getResources().getString(R.string.humidity));
                    humidity_val.setText(String.format("%s%%", object.getJSONObject("main").getString("humidity")));
                } catch (JSONException e) {
                    e.printStackTrace();
                    result_info_table.setVisibility(View.INVISIBLE);
                    resultInfoDescription.setText(R.string.no_info);
                }
            });
        });

        executor.execute(() ->
                handler.post(() -> {
                    statusInfo = mainActivity.findViewById(R.id.status_info);
                    statusInfo.setVisibility(View.VISIBLE);
                    statusInfo.setText(R.string.wait_please);
                })
        );
    }
}
