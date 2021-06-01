package example.testForJava;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class threadPool {
    public static void main(String[] args) {
        //线程池容量为5，最大可扩展到10
        /*保持200表示线程没有任务执行时最多保持多久时间会终止。
         默认情况下，只有当线程池中的线程数大于corePoolSize时，keepAliveTime才会起作用，
         直到线程池中的线程数不大于corePoolSize，即当线程池中的线程数大于corePoolSize时，
         如果一个线程空闲的时间达到keepAliveTime，则会终止，直到线程池中的线程数不超过corePoolSize。
        unit为前一参数的单位*/
        //等待队列
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
        for (int i = 0; i < 15; i++) {
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("thread pool num " + executor.getPoolSize() + "  queue num " + executor.getQueue().size() + " already complected num " + executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }
}