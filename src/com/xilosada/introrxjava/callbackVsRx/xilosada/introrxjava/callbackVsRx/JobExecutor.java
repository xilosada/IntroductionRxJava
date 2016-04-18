package com.xilosada.introrxjava.callbackVsRx.xilosada.introrxjava.callbackVsRx;

import java.util.concurrent.*;

public class JobExecutor extends ThreadPoolExecutor {

  private static final int INITIAL_POOL_SIZE = 5;
  private static final int MAX_POOL_SIZE = 5;

  // Sets the amount of time an idle thread waits before terminating
  private static final int KEEP_ALIVE_TIME = 10;

  // Sets the Time Unit to seconds
  private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

  public JobExecutor() {
    super(INITIAL_POOL_SIZE, MAX_POOL_SIZE,
            KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, new LinkedBlockingQueue<>(),  new JobThreadFactory());
  }

  private static class JobThreadFactory implements ThreadFactory {
    private static final String THREAD_NAME = "Thread Pool_";
    private int counter = 0;

    @Override public Thread newThread(Runnable runnable) {
      return new Thread(runnable, THREAD_NAME + counter++);
    }
  }
}