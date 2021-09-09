package com.firstapp.weatherapp.activities.study;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firstapp.weatherapp.R;

import java.util.concurrent.TimeUnit;

public class ThreadExperimentsActivity extends Activity {

    private final String LOG_TAG = "myLogs";

    private Handler handler;
    private TextView tvInfo;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_experiments);
        tvInfo = findViewById(R.id.tvInfo);
        btnStart = findViewById(R.id.btnStart);

        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                // обновляем TextView
                tvInfo.setText("Закачано файлов: " + msg.what);
                if (msg.what == 10) btnStart.setEnabled(true);
            };
        };
    }

    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                btnStart.setEnabled(false);
                Thread t = new Thread(() -> {
                    for (int i = 1; i <= 10; i++) {
                        // долгий процесс
                        downloadFile();
                        handler.sendEmptyMessage(i);
                        // пишем лог
                        Log.d(LOG_TAG, "i = " + i);
                    }
                });
                t.start();
                break;
            case R.id.btnTest:
                Log.d(LOG_TAG, "test");
                break;
            default:
                break;
        }
    }

    void downloadFile() {
        // пауза - 1 секунда
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}