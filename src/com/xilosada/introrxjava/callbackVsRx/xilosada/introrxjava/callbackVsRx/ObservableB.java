package com.xilosada.introrxjava.callbackVsRx.xilosada.introrxjava.callbackVsRx;

import com.xilosada.introrxjava.callbackVsRx.xilosada.introrxjava.callbackVsRx.tasks.*;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.concurrent.*;

import static com.xilosada.introrxjava.callbackVsRx.xilosada.introrxjava.callbackVsRx.tasks.CallToRemoteServiceB.callToRemoteServiceB;
import static com.xilosada.introrxjava.callbackVsRx.xilosada.introrxjava.callbackVsRx.tasks.CallToRemoteServiceC.callToRemoteServiceC;
import static com.xilosada.introrxjava.callbackVsRx.xilosada.introrxjava.callbackVsRx.tasks.CallToRemoteServiceD.callToRemoteServiceD;
import static com.xilosada.introrxjava.callbackVsRx.xilosada.introrxjava.callbackVsRx.tasks.CallToRemoteServiceE.callToRemoteServiceE;

/**
 * Created by xilosada on 17/04/16.
 */
public class ObservableB {

    public static void run() {
        final Scheduler jobScheduler = Schedulers.from(
                new ThreadPoolExecutor(4, 4, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>()));


        Observable<String> f3 = CallToRemoteServiceA.callToRemoteServiceA() // call serviceA
                // call serviceC with the result of serviceA
                .flatMap(f1 -> callToRemoteServiceC(f1))
                .subscribeOn(jobScheduler);


        Observable<Integer> f4Andf5 = callToRemoteServiceB() // call serviceC
                // call serviceD and serviceE then build a new value
                .flatMap((f2) -> callToRemoteServiceD(f2)
                        .subscribeOn(jobScheduler)
                        .zipWith(
                                callToRemoteServiceE(f2).subscribeOn(jobScheduler)
                        ,(f4, f5) -> f4 * f5))
                .subscribeOn(jobScheduler);

        // compute the string to display from f3, and the f4, f5 pair
        f3.zipWith(f4Andf5, (childF3, childF4Andf5) -> childF3 + " => " + childF4Andf5)
                // display the value
                .subscribe(s -> System.out.println(Thread.currentThread().getName() +" =>" + s));
    }

    public static void main(String... args) {
        run();
    }

}
