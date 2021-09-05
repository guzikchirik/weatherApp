package com.firstapp.weatherapp.tasks;

import android.os.AsyncTask;
import android.widget.TextView;

import com.firstapp.weatherapp.MainActivity;
import com.firstapp.weatherapp.R;
import com.firstapp.weatherapp.httpclient.HttpClientDeprecated;

import org.json.JSONException;
import org.json.JSONObject;

public class GetWeather extends AsyncTask<String, String, String> {
    private static final String API_KEY = "dc7c2b29e7551ad61808211eb3dc3d23";
    private MainActivity mainActivity;
    private TextView resultInfoDescription;

    public GetWeather(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.resultInfoDescription = mainActivity.findViewById(R.id.result_info_description);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        resultInfoDescription.setText(R.string.wait_please);
    }

    @Override
    protected String doInBackground(String... strings) {
        String actualCity = strings[0];
        String openWeatherUrl = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&lang=ru&units=metric", actualCity, API_KEY);
        return HttpClientDeprecated.get(openWeatherUrl);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        TextView userField = mainActivity.findViewById(R.id.user_field);
        TextView weatherIn = mainActivity.findViewById(R.id.result_info_weather_in);
        TextView city = mainActivity.findViewById(R.id.result_info_city);
        resultInfoDescription = mainActivity.findViewById(R.id.result_info_description);
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
            city.setText(userField.getText().toString());
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
            resultInfoDescription.setText(R.string.no_info);
        }
    }
}
