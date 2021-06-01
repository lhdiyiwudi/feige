package example.testForJava;

import java.util.concurrent.Callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class callableThreadTest implements Callable<Integer> {
    public static void main(String[] args) {
        callableThreadTest callableThreadTest = new callableThreadTest();
        FutureTask<Integer> futureTask = new FutureTask<>(callableThreadTest);
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " 的循环变量的i的值" + i);
            if (i == 20) {
                new Thread(futureTask, "futureTask thread").start();
            }
        }
        try {
            System.out.println("子 thread return " + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Integer call() throws Exception {
        int i = 0;
        for (; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "  " + i);
        }
        return i;
    }
}
