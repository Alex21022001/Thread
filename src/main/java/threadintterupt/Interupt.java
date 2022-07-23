package threadintterupt;

import java.util.Random;

public class Interupt {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();

                for (int i = 0; i < 1_000_000_000; i++) {
                    if (Thread.currentThread().isInterrupted()){
                        break;
                    }
                    Math.sin(random.nextDouble(100));
                }
            }
        });
        System.out.println("start");
        thread.start();
        Thread.sleep(3000);
        thread.interrupt();
        thread.join();
        System.out.println("finish");
    }
}
