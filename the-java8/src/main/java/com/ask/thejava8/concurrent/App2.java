package com.ask.thejava8.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 고수준 (High-Level) Concurrency 프로그래밍
 * 쓰레드를 만들고 관리하는 작업을 애플리케이션에서 분리.
 * 그런 기능을 Executors 에게 위임.
 *
 * Executors 가 하는 일
 * 쓰레드 만들기: 애플리케이션이 사용할 쓰레드 풀을 만들어 관리한다.
 * 쓰레드 관리: 쓰레드 생명 주기를 관리한다.
 * 작업 처리 및 실행: 쓰레드로 실행할 작업을 제공할 수 있는 API 를 제공한다.
 *
 * 주요 인터페이스
 * Executor: execute(Runnable)
 * ExecutorService: Executor 상속 받은 인터페이스로, Callable 도 실행할 수 있으며, Executor 종료 시키거나, 여러 Callable 을 동시에 실행하는 등의 기능을 제공한다.
 * ScheduledExecutorService: ExecutorService 를 상속 받은 인터페이스로 특정 시간 이후에 또는 주기적으로 작업을 실행할 수 있다.
 */
public class App2 {

  public static void main(String[] args) {
    singleThreadExecutor();
    dualTreadExecutor();
    schedule();
    scheduleFixedRate();
  }

  private static void scheduleFixedRate() {
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.scheduleAtFixedRate(getRunnable("schedule2"), 2, 1, TimeUnit.SECONDS);
  }

  private static void schedule() {
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.schedule(getRunnable("schedule"), 2, TimeUnit.SECONDS);
    executorService.shutdown();
  }

  private static Runnable getRunnable(String message) {
    return () -> System.out.println(message + ":" + Thread.currentThread().getName());
  }

  private static void singleThreadExecutor() {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(getRunnable("Hello"));

    // graceful shutdown
    executorService.shutdown();

    // 즉시 종료
    //executorService.shutdownNow();
  }

  private static void dualTreadExecutor() {
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    executorService.submit(getRunnable("test1"));
    executorService.submit(getRunnable("test2"));
    executorService.submit(getRunnable("test3"));
    executorService.submit(getRunnable("test4"));
    executorService.submit(getRunnable("test5"));
    executorService.shutdown();
  }
}
