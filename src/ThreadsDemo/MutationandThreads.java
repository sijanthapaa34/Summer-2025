package ThreadsDemo;

class Counter{
    int count;
    public synchronized void increment(){
        count++;
    }
}

public class MutationandThreads {
    public static void main(String[] args) throws InterruptedException {
        Counter c = new Counter();
        Runnable r = () ->{
            for(int i=0; i<100; i++){
                c.increment();
            }
        };
        Runnable r1 =() ->{
            for(int i=0; i<100; i++){
                c.increment();
            }
        };
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r1);
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println(c.count);

    }
}
