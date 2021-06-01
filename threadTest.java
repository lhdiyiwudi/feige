package example.testForJava;

public class threadTest extends Thread {
    private int ticket = 10;

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            if (this.ticket>0) {
                System.out.println(this.getName()+"卖出第"+(10-this.ticket-- + 1)+"票");
            }
        }

    }

    public static void main(String[] args) {
        threadTest threadTest1 = new threadTest();
        threadTest threadTest2 = new threadTest();
        threadTest threadTest3 = new threadTest();
        new Thread(threadTest1, "thread1").start();
        new Thread(threadTest2, "thread2").start();
        new Thread(threadTest3, "thread3").start();
    }
}
