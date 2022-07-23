package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    public static void main(String[] args) throws InterruptedException {
        long before = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 100000; i++) {
            executorService.submit(new Work(i));
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);
        System.out.println(after()-before);
    }

    public static long after() {
        return System.currentTimeMillis();
    }
}

class Work implements Runnable {
    private int id;

    public Work(int id) {
        this.id = id;
    }

    @Override
    public void run() {

        System.out.println("threadpool.Work " + id + "has done");
    }
}
