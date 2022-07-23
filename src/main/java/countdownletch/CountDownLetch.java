package countdownletch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLetch {
    public static void main(String[] args) throws InterruptedException {
        //Ставит счетчик с каким то число, и пока это число не станет 0 процесс после вызова метода await() - не продолжится.
        CountDownLatch count = new CountDownLatch(3);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        // Пул потоков, позволяет нам разделить работы на n кол-во потоков. Каждый из которых заберет себе 1 действия и если 1 выполнит метод за 9 сек, то 3 сделают за 6.
        //Thread pool allows us break up jobs into n account of threads each of which will take one action itself and if a thread is executed the method for 9 seconds,that 3 threads will do it for 3 seconds.
        for (int i = 0; i <3; i++) {
            executorService.submit(new Processor(count));
        }
        executorService.shutdown();
        count.await();
        System.out.println("AAAAAAAAAA");
    }
}

class Processor implements Runnable{
CountDownLatch countDownLatch;

    public Processor(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();

    }
}
