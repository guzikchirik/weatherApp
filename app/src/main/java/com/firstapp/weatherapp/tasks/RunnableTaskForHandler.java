package com.firstapp.weatherapp.tasks;

public class RunnableTaskForHandler<R> implements Runnable {

    private CustomCallable<R> callable;
    private R result;

    public RunnableTaskForHandler(CustomCallable<R> callable, R result) {
        this.callable = callable;
        this.result = result;
    }

    @Override
    public void run() {
        callable.setDataAfterLoading(result);
    }
}
