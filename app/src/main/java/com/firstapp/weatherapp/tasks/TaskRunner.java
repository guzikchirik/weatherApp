package com.firstapp.weatherapp.tasks;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRunner {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Executor executor = Executors.newCachedThreadPool();

    public <R> void executeAsync(CustomCallable<R> callable) {
        try {
            callable.setUiForLoading();
            executor.execute(new RunnableTask<R>(handler, callable));
        } catch (Exception e) {

        }
    }
}
