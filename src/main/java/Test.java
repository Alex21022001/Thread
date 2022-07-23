import noka.*;


import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Test {
    private static String str;
    private static int n=0;

    public static void main(String[] args) throws IOException {


    }


}


class Worker {
    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();

    Random random = new Random();

    public void addToListOne() {
        synchronized (list1) {
            for (int i = 0; i < 1000; i++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list1.add(random.nextInt(100));
            }
        }
    }

    public synchronized void addToListTwo() {
        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list1.add(random.nextInt(100));
        }
    }

    public void work() {
        addToListOne();
        addToListTwo();
    }

    public void main() throws InterruptedException {
        long before = System.currentTimeMillis();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                work();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                work();
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();


        long after = System.currentTimeMillis();

        System.out.println(after - before);
        System.out.println(list1.size());
        System.out.println(list2.size());
    }
}