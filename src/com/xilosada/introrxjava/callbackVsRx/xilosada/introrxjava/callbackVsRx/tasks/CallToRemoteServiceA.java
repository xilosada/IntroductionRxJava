package com.xilosada.introrxjava.callbackVsRx.xilosada.introrxjava.callbackVsRx.tasks;

import rx.Observable;

import java.util.concurrent.Callable;

public final class CallToRemoteServiceA implements Runnable {

    private final Callback<String> callback;

    public CallToRemoteServiceA(Callback<String> callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        // simulate fetching data from remote service
        try {
            callback.call(getStringBlocking());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getStringBlocking() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " ==> start running task A");
        Thread.sleep(100);
        System.out.println(Thread.currentThread().getName() + " ==> finish running task A");
        return  "responseA";
    }

    public static Observable<String> callToRemoteServiceA() {
        return Observable.fromCallable(() -> getStringBlocking());
    }
}

