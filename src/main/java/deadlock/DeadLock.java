package deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {
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

        test.finish();
    }


}

class Test {
    private Account account2 = new Account();
    private Account account1 = new Account();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();
    private Random random = new Random();

    private void takenLock(Lock lock1, Lock lock2) {
        boolean firstTaken = false;
        boolean secondTaken = false;
        while (true) {
            try {
                firstTaken = lock1.tryLock();
                secondTaken = lock2.tryLock();
            } finally {
                if (firstTaken && secondTaken) {
                    return;
                }
                if (firstTaken) {
                    lock1.unlock();
                }
                if (secondTaken) {
                    lock2.unlock();
                }
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void first() {
        for (int i = 0; i < 1000; i++) {
            takenLock(lock1, lock2);
            try {
                Account.transaction(account1, account2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void second() {
        for (int i = 0; i < 1000; i++) {
            takenLock(lock2, lock1);
            try {
                Account.transaction(account2, account1, random.nextInt(100));
            } finally {
                lock2.unlock();
                lock1.unlock();
            }
        }
    }

    public void finish() {
        System.out.println(account1.getBalance());
        System.out.println(account2.getBalance());
        System.out.println("Total balance:  " + (account1.getBalance() + account2.getBalance()));
    }
}

class Account {
    private int balance = 10000;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public static void transaction(Account acc1, Account acc2, int amount) {
        acc2.balance = acc2.balance - amount;
        acc1.balance = acc1.balance + amount;
    }

    public int getBalance() {
        return balance;
    }
}
