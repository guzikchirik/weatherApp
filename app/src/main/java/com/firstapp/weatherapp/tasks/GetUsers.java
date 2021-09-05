package com.firstapp.weatherapp.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.firstapp.weatherapp.R;
import com.firstapp.weatherapp.activities.user.AddUserActivity;
import com.firstapp.weatherapp.httpclient.HttpClientDeprecated;

import org.json.JSONArray;
import org.json.JSONException;

public class GetUsers extends AsyncTask<String, String, String> {

    private static final String MY_SERVICE_URL = "http://192.168.100.52:4000/api/v1/users";

    private Activity activity;

    public GetUsers(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        return HttpClientDeprecated.get(MY_SERVICE_URL);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        System.out.println("222222222222222222222222222222222222222222222222222222");
        System.out.println(result);
        TextView textView3 = activity.findViewById(R.id.textView3);
        textView3.setText(result);
//        try {
//            JSONArray object = new JSONArray(result);
////            userField.setText(object.getJSONObject(0).toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
}
