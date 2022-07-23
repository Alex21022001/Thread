package rentlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentLock {
    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.first();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.second();
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        test.show();

    }
}

class Test {
    private int counter;
    private Lock lock = new ReentrantLock();

    public void show() {
        System.out.println(counter);
    }

    private void increment() {
        for (int i = 0; i < 1000; i++) {
            counter++;
        }
    }

    public void first() {
        lock.lock();
        increment();
        lock.unlock();
    }

    public void second() {
        lock.lock();
        increment();
        lock.unlock();
    }
}
