package com.ask.thejava8.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Callable
 * Runnable 과 유사하지만 작업의 결과를 받을 수 있다.
 *
 * Future
 * 비동기적인 작업의 현재 상태를 조회하거나 결과를 가져올 수 있다.
 */
public class App3 {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // futureTest();
    // invokeAll();
    invokeAny();
  }

  private static void invokeAny() throws InterruptedException, ExecutionException {
    ExecutorService executorService = Executors.newFixedThreadPool(3);
    Callable<String> callable1 = () -> {
      Thread.sleep(4000);
      return "callable1";
    };

    Callable<String> callable2 = () -> {
      Thread.sleep(1000);
      return "callable2";
    };

    Callable<String> callable3 = () -> {
      Thread.sleep(2000);
      return "callable3";
    };

//    여러 작업 중에 하나라도 먼저 응답이 오면 끝내기 invokeAny()
//    동시에 실행한 작업 중에 제일 짧게 걸리는 작업 만큼 시간이 걸린다.
//    블로킹 콜이다.
    String s = executorService.invokeAny(Arrays.asList(callable1, callable2, callable3));

    System.out.println("s = " + s);

    executorService.shutdown();
  }

  private static void invokeAll() throws InterruptedException, ExecutionException {
    ExecutorService executorService = Executors.newFixedThreadPool(3);
    Callable<String> callable1 = () -> {
      //Thread.sleep(1000);
      TimeUnit.SECONDS.sleep(1);
      return "callable1";
    };

    Callable<String> callable2 = () -> {
      //Thread.sleep(4000);
      TimeUnit.SECONDS.sleep(4);
      return "callable2";
    };

    Callable<String> callable3 = () -> {
      //Thread.sleep(2000);
      TimeUnit.SECONDS.sleep(2);
      return "callable3";
    };

    //여러 작업 동시에 실행하기 invokeAll()
    //동시에 실행한 작업 중에 제일 오래 걸리는 작업 만큼 시간이 걸린다.
    List<Future<String>> futures = executorService.invokeAll(Arrays.asList(callable1, callable2, callable3));

    for (Future<String> future : futures) {
      System.out.println("future.get() = " + future.get());
    }

    executorService.shutdown();
  }

  private static void futureTest() throws InterruptedException, ExecutionException {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future<String> helloFuture = executorService.submit(() -> {
      Thread.sleep(2000L);
      return "Callable";
    });
    System.out.println("helloFuture.isDone() = " + helloFuture.isDone());

    // blocking call
    String result = helloFuture.get();

    System.out.println("helloFuture.isDone() = " + helloFuture.isDone());

    System.out.println("result = " + result);

    executorService.shutdown();
  }
}
