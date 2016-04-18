package com.xilosada.introrxjava.callbackVsRx.xilosada.introrxjava.callbackVsRx.tasks;

import rx.Observable;

import java.util.concurrent.Callable;

public final class CallToRemoteServiceC implements Runnable {

        private final Callback<String> callback;
        private final String dependencyFromA;

        public CallToRemoteServiceC(Callback<String> callback, String dependencyFromA) {
            this.callback = callback;
            this.dependencyFromA = dependencyFromA;
        }

        @Override
        public void run() {
            // simulate fetching data from remote service
            try {
            callback.call(getStringBlocking(dependencyFromA));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    public static String getStringBlocking(String dependencyFromA) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " ==> blocked running task C");
        Thread.sleep(60);
        String result = "responseB_" + dependencyFromA;
        System.out.println("intermediate callback: " + result + " => " + ("f4 * f5"));
        System.out.println(Thread.currentThread().getName() + " ==> finish running task C");
        return result;
    }

    public static Observable<String> callToRemoteServiceC(final String dependencyFromA) {
        return Observable.fromCallable(new Callable<String>() {

            @Override
            public String call() throws Exception {
                return getStringBlocking(dependencyFromA);
            }
        });
    }

}

