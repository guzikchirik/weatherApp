package com.firstapp.weatherapp.tasks;

import android.os.Handler;

public class RunnableTask<R> implements Runnable {
    private final Handler handler;
    private final CustomCallable<R> callable;

    public RunnableTask(Handler handler, CustomCallable<R> callable) {
        this.handler = handler;
        this.callable = callable;
    }

    @Override
    public void run() {
        try {
            final R result = callable.call();
            handler.post(new RunnableTaskForHandler(callable, result));
        } catch (Exception e) {

        }
    }
}
