package producerconsumer;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class ProducerConsumer {

    public static void main(String[] args) throws InterruptedException {

        ProdCon prodCon = new ProdCon();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    prodCon.consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    prodCon.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread2.start();
        thread1.start();

        thread2.join();
        thread1.join();

    }
}

class ProdCon {
    private static Queue<Integer> queue = new PriorityQueue<>();
    private static final int CAPACITY = 10;
    private static final Object lock = new Object();
    private int count=0;

    public void produce() throws InterruptedException {
        System.out.println("start");
        while (true) {
            synchronized (lock) {

                if (queue.size() >= CAPACITY) {
                    lock.wait();
                }
                queue.add(count++);
                System.out.println(queue.size());
            }
        }
    }

    public void consumer() throws InterruptedException {
        Thread.sleep(1000);
        while (true) {
            Thread.sleep(1000);
            synchronized (lock){
                System.out.println(queue.poll());
                if (queue.size()<CAPACITY){
                    lock.notify();
                }
            }
        }
    }
}
