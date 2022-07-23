package semophore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Semophore {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        //Ограничивает количество потоков при работе с одна ресурсом, когда один поток закончил работу он отдает разрешение другому в очереди.
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Connection connection = Connection.getConnection();
        for (int i = 0; i <10; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        connection.doWork();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                    semaphore.release();
                    }
                }
            });

        }
        executorService.shutdown();


    }
}

class Connection {
    private static Connection connection = new Connection();
    private  int connectionCount;

    private Connection() {
    }

    public  static Connection getConnection() {
        return connection;
    }
    public void doWork(){
        synchronized (this){
            connectionCount++;
            System.out.println(connectionCount);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this){
            connectionCount--;
        }
    }
}
