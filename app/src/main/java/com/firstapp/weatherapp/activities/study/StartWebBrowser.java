package com.firstapp.weatherapp.activities.study;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firstapp.weatherapp.R;

public class StartWebBrowser extends Activity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_web_browser);

        editText = findViewById(R.id.swb_enter_uri_field);
        Button searchBtn = findViewById(R.id.swb_btn);
        searchBtn.setOnClickListener(searchBtnListener());
    }

    private View.OnClickListener searchBtnListener() {
        return View -> {
            String uri = editText.getText().toString();
            if (uri.isEmpty()) {
                uri = "http://startandroid.ru";
            } else {
                uri = checkAndUpdateUrl(uri);
            }

            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("actualUrl", uri);
            editor.commit();

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);

//            Toast.makeText(this, "U R returned from " + preferences.getString("actualUrl", ""), Toast.LENGTH_LONG).show();
        };
    }

    private String checkAndUpdateUrl(String uri) {
        if (!uri.contains("http://")) {
            return "http://" + uri;
        }
        return uri;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}