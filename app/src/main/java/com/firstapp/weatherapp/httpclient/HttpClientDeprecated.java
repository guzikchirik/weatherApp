package com.firstapp.weatherapp.httpclient;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClientDeprecated {

    private static HttpURLConnection connection = null;
    private static BufferedReader reader = null;


    public static String getResponse(String endpoint) {

        try {
            URL url = new URL(endpoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            return buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    private static OkHttpClient client = new OkHttpClient();

//    public static String get(String url) {
//        final String[] actualResponse = new String[1];
//
//        Request request = new Request.Builder()
//                .url(url)
//                .get()
//                .build();
//
////        try (Response response = client.newCall(request).execute()) {
////            return response.body().string();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                actualResponse[0] = "Error:" + e.getMessage();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    actualResponse[0] = response.body().string();
//                    // Do what you want to do with the response.
//                } else {
//                    actualResponse[0] = "Error:";
//                }
//            }
//        });
//        return actualResponse[0];
//    }

    private static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public static String post(String url, String json) {
        final String[] actualResponse = new String[1];
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                actualResponse[0] = "Error:" + e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    actualResponse[0] = response.body().string();
                    // Do what you want to do with the response.
                } else {
                    actualResponse[0] = "Error:";
                }
            }
        });
        return actualResponse[0];
    }

    public static void get(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static String get(String url) {
        final String[] actualResponse = new String[1];
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
//                .enqueue(new Callback() {
//                    @Override
//                    public void onFailure(final Call call, IOException e) {
//                    }
//
//                    @Override
//                    public void onResponse(Call call, final Response response) throws IOException {
//                        actualResponse[0] = response.body().string();
//                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                        System.out.println(actualResponse[0]);
//                        // Do something with the response
//                    }
//                });
        return actualResponse[0];
    }

    public static void remove(String url) {
        remove(url, new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!   statusCode = " +
                        response.code() + "   !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                // Do something with the response
            }
        });
    }

    public static void remove(String url, Callback callback) {
        final int[] statusCode = new int[1];
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();

        client.newCall(request)
                .enqueue(callback);
    }
}
