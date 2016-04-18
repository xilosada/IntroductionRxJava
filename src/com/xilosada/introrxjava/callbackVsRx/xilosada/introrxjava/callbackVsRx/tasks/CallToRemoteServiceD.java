package com.xilosada.introrxjava.callbackVsRx.xilosada.introrxjava.callbackVsRx.tasks;

import rx.Observable;

import java.util.concurrent.Callable;

public final class CallToRemoteServiceD implements Runnable {

    private final Callback<Integer> callback;
    private final Integer dependencyFromB;

    public CallToRemoteServiceD(Callback<Integer> callback, Integer dependencyFromB) {
        this.callback = callback;
        this.dependencyFromB = dependencyFromB;
    }

    @Override
    public void run() {
        // simulate fetching data from remote service
        try {
            callback.call(getIntBlocking(dependencyFromB));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int getIntBlocking(int dependencyFromB) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " ==> blocked running task D");
        Thread.sleep(60);
        int result =  40 + dependencyFromB;
        System.out.println("intermediate callback: f3" + " => " + (result + " * f5"));
        System.out.println(Thread.currentThread().getName() + " ==> finish running task D");
        return result;
    }

    public static Observable<Integer> callToRemoteServiceD(int dependencyFromA) {
        return Observable.fromCallable(() -> getIntBlocking(dependencyFromA));
    }
}
