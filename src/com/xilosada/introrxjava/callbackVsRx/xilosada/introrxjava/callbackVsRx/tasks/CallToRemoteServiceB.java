package com.xilosada.introrxjava.callbackVsRx.xilosada.introrxjava.callbackVsRx.tasks;

import rx.Observable;

public final class CallToRemoteServiceB implements Runnable {

        private final Callback<Integer> callback;

        public CallToRemoteServiceB(Callback<Integer> callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            // simulate fetching data from remote service
            try {
                callback.call(getIntBlocking());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    private static int getIntBlocking() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " ==> blocked running task B");
        Thread.sleep(40);
        System.out.println(Thread.currentThread().getName() + " ==> finish running task B");
        return 100;
    }

    public static Observable<Integer> callToRemoteServiceB() {
        return Observable.fromCallable(() -> getIntBlocking());
    }
}