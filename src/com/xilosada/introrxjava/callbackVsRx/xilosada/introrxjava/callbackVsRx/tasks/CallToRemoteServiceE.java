package com.xilosada.introrxjava.callbackVsRx.xilosada.introrxjava.callbackVsRx.tasks;

import rx.Observable;

import java.util.concurrent.Callable;

public final class CallToRemoteServiceE implements Runnable {

        private final Callback<Integer> callback;
        private final Integer dependencyFromB;

        public CallToRemoteServiceE(Callback<Integer> callback, Integer dependencyFromB) {
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
        System.out.println(Thread.currentThread().getName() + " ==> blocked running task E");
        Thread.sleep(55);
        int result = 5000 + dependencyFromB;
        System.out.println("intermediate callback: f3" + " => (f4  *"+ result+")");
        System.out.println(Thread.currentThread().getName() + " ==> finish running task E");
        return result;
    }

    public static Observable<Integer> callToRemoteServiceE(int dependencyFromB) {
        return Observable.fromCallable(() -> getIntBlocking(dependencyFromB));
    }
}
