package com.firstapp.weatherapp.tasks;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstapp.weatherapp.R;
import com.firstapp.weatherapp.httpclient.HttpClientDeprecated;
import com.firstapp.weatherapp.model.User;
import com.firstapp.weatherapp.utils.PropertyReader;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserService {
    private Activity activity;
    private Properties properties;
    private List<User> users = new ArrayList<>();
    private final String[] responseActual = new String[1];

    public UserService(Activity activity) {
        this.activity = activity;
        PropertyReader propertyReader = new PropertyReader(activity);
        properties = propertyReader.getMyProperties("app.properties");
    }

//    public void addUser(User user) {
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        Handler handler = new Handler(Looper.getMainLooper());
//        HttpClientDeprecated client = new HttpClientDeprecated();
//
//        executor.execute(() -> {
//            //Background work here
//            handler.post(() -> {
//                //UI Thread work here
//            });
//        });
//    }

    public void addUser(User user, String json) {
        String serviceUrl = properties.getProperty("MY_SERVICE_URL");
//        String responseStr = HttpClientDeprecated.get(serviceUrl);
        String post = HttpClientDeprecated.post(serviceUrl, json);
    }

    public List<User> getAllUsers() {
        String serviceUrl = properties.getProperty("MY_SERVICE_URL");
        HttpClientDeprecated.get(serviceUrl, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {

                responseActual[0] = "Stub!";
                try {
                    responseActual[0] = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                activity.runOnUiThread(() -> {
                    TextView text = activity.findViewById(R.id.textView3);
//                    text.setText(Objects.requireNonNull(responseActual[0]));
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        users = objectMapper.readValue(responseActual[0], new TypeReference<>() {
                        });
                        StringBuilder sb = new StringBuilder();
                        for (User user : users) {
                            sb.append(user.getLogin()).append("\n");
                        }
                        text.setText(sb.toString());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });

            }
        });

        return users;
    }

    public void removeUserById(String id) {
        String serviceUrl = properties.getProperty("MY_SERVICE_URL");
        HttpClientDeprecated.remove(serviceUrl + "/" + id, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                responseActual[0] = "Failure!";
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                responseActual[0] = String.valueOf(response.code());
                activity.runOnUiThread(() -> {
                    TextView text = activity.findViewById(R.id.status);
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + responseActual[0] + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                    text.setText(Objects.requireNonNull(responseActual[0]));
                    System.out.println("responseActual[0].equals(\"204\") = "+responseActual[0].equals("204"));
                    if (responseActual[0].equals("204")) {
                        text.setText("Пользователь удален успешно");
                    }
//                    try {
//                        ObjectMapper objectMapper = new ObjectMapper();
//                        users = objectMapper.readValue(responseActual[0], new TypeReference<>() {
//                        });
//                        StringBuilder sb = new StringBuilder();
//                        for (User user : users) {
//                            sb.append(user.getLogin()).append("\n");
//                        }
//
//                    } catch (JsonProcessingException e) {
//                        e.printStackTrace();
//                    }
                });
            }
        });
    }

    public void removeUserByLogin(String value) {
    }
}
