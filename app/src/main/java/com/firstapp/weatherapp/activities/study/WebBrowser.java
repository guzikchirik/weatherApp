package com.firstapp.weatherapp.activities.study;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.firstapp.weatherapp.R;

public class WebBrowser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);

        Uri uri = getIntent().getData();
        WebView webView = findViewById(R.id.wb_web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(uri.toString());
    }
}