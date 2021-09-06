package com.firstapp.weatherapp.activities.study;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firstapp.weatherapp.R;

public class WebMapCallActivity extends Activity implements View.OnClickListener {

    private Button btnWeb;
    private Button btnMap;
    private Button btnCall;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_map_call);

        btnWeb = findViewById(R.id.btnWeb);
        btnMap = findViewById(R.id.btnMap);
        btnCall = findViewById(R.id.btnCall);

        btnWeb.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        btnCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnWeb:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com"));
                startActivity(intent);
                break;
            case R.id.btnMap:
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:55.754283,37.62002"));
                startActivity(intent);
                break;
            case R.id.btnCall:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:12345"));
                startActivity(intent);
                break;
        }
    }
}