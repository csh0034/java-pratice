package com.ask.thejava8.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Future 로는 하기 어렵던 작업들
 * Future 를 외부에서 완료 시킬 수 없다. 취소하거나, get()에 타임아웃을 설정할 수는 있다.
 * 블로킹 코드(get())를 사용하지 않고서는 작업이 끝났을 때 콜백을 실행할 수 없다.
 * 여러 Future 를 조합할 수 없다, 예) Event 정보 가져온 다음 Event 에 참석하는 회원 목록 가져오기
 * 예외 처리용 API 를 제공하지 않는다.
 */
public class App4 {

  /**
   * CompletableFuture
   * 리턴값이 없는 경우: runAsync()
   * 리턴값이 있는 경우: supplyAsync()
   *
   * 콜백 제공하기
   * thenApply(Function): 리턴값을 받아서 다른 값으로 바꾸는 콜백
   * thenAcceptAsync(Function, Executor) : 콜백을 별도의 pool 에서 실행
   * thenAccept(Consumer): 리턴값을 또 다른 작업을 처리하는 콜백 (리턴없이)
   * thenRun(Runnable): 리턴값 받지 다른 작업을 처리하는 콜백
   * 콜백 자체를 또 다른 쓰레드에서 실행할 수 있다.
   *
   * Fork Join Pool
   * Java 7에서 새로 지원하는 fork-join 풀은 기본적으로 큰 업무를 작은 업무로 나누어 배분해서 , 일을 한 후에 일을 취합하는 형태
   *
   * 조합하기
   * thenCompose(): 두 작업이 서로 이어서 실행하도록 조합
   * thenCombine(): 두 작업을 독립적으로 실행하고 둘 다 종료 했을 때 콜백 실행
   * allOf(): 여러 작업을 모두 실행하고 모든 작업 결과에 콜백 실행
   * anyOf(): 여러 작업 중에 가장 빨리 끝난 하나의 결과에 콜백 실행
   *
   * 예외처리
   * exceptionally(Function)
   * handle(BiFunction):
   */
  public static void main(String[] args) throws ExecutionException, InterruptedException {

    // 콜백이 리턴없이 작업을 처리
    // supplyAsyncAndThenAccept();

    // 콜백이 리턴값을 받아서 값을 바꿈
    // supplyAsyncAndThenApply();

    // 두 작업을 독립적으로 실행하고 둘 다 종료 했을 때 콜백 실행
    // thenCombine();

    // 여러 작업을 모두 실행하고 모든 작업 결과에 콜백 실행, 리턴의 경우 null
    // allOf();

    // 예외처리
    exceptionHandle(true);
  }

  private static void supplyAsyncAndThenAccept() throws InterruptedException, ExecutionException {
    // ForkJoinPool 사용하지 않고 Pool 직접 만들기
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
      System.out.println("Hello : " + Thread.currentThread().getName());
      return "hello";
    }, executorService).thenAcceptAsync(s -> {
      System.out.println(Thread.currentThread().getName());
      System.out.println(s.toUpperCase());
    }, executorService);
    future.get();

    executorService.shutdown();
  }

  private static void supplyAsyncAndThenApply() throws InterruptedException, ExecutionException {
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
      System.out.println("Hello : " + Thread.currentThread().getName());
      return "hello";
    }).thenApply(s -> {
      System.out.println(Thread.currentThread().getName());
      return s.toUpperCase();
    });

    System.out.println(future.get());
  }

  private static void thenCombine() throws InterruptedException, ExecutionException {
    CompletableFuture<String> hello = getFuture("hello");
    CompletableFuture<String> world = getFuture("world");

    CompletableFuture<String> future = hello.thenCombine(world, (h, w) -> h + " " + w);
    System.out.println(future.get());
  }

  private static void allOf() throws InterruptedException, ExecutionException {
    CompletableFuture<String> hello = getFuture("hello");
    CompletableFuture<String> world = getFuture("world");

    CompletableFuture.allOf(hello, world)
        .thenAccept(System.out::println)
        .get();
  }

  private static CompletableFuture<String> getFuture(String message) {
    return CompletableFuture.supplyAsync(() -> {
      System.out.println(message + " : " + Thread.currentThread().getName());
      return message;
    });
  }

  private static void exceptionHandle(boolean throwError) throws ExecutionException, InterruptedException {
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
      if (throwError) {
        throw  new IllegalStateException("error!!!");
      }

      System.out.println("Hello : " + Thread.currentThread().getName());
      return "hello";
    }).handle((result, ex) -> {
      if (ex != null) {
        System.out.println(ex.getMessage());
        return "ERROR!!";
      }
      return result;
    });

    System.out.println("future.get() = " + future.get());
  }
}
