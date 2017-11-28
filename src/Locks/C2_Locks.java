package Locks;

/**
 * Created by c1526449 on 02/03/2017.
 */
import java.util.concurrent.atomic.AtomicInteger;

public class C2_Locks implements Runnable {

    private static AtomicInteger l1 = new AtomicInteger(0);
    private static AtomicInteger l2 = new AtomicInteger(0);
    private static long danger = 0;
    int method = 0;

    public C2_Locks(int m) { this.method = m; }

    public static void main(String[] args) {

        Thread t1 = new Thread(new C2_Locks(1));
        Thread t2 = new Thread(new C2_Locks(2));
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        System.out.println(method);
        for (int i = 0; i < 5000; i++) {
            if (method == 1) {
                while(l1.getAndSet(1) == 1);
                work(1, 1000);
                while(l2.getAndSet(1) ==1);
                work(1, 10000);
                l2.set(0);
                l1.set(0);
            } else {
                while(l2.getAndSet(1) == 1);
                work(2, 10000);
                while(l1.getAndSet(1) ==1);
                work(2, 100);
                l1.set(0);
                l2.set(0);
            }
            System.out.println(i);
        }
    }

    private void work(int method, int x) {
        int m = 0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < 10000; j++) {
                m++;
            }
        }
        System.out.println(method +", "+m);
    }


}

