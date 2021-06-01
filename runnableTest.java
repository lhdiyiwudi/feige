package example.testForJava;

public class runnableTest implements Runnable {
    private int ticket = 10;

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            if (this.ticket > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出第" + (10 - this.ticket-- + 1) + "票");
            }
        }
    }

    public static void main(String[] args) {

        runnableTest runnableTest = new runnableTest();
        new Thread(runnableTest, "first thread").start();
        new Thread(runnableTest, "second thread").start();
        new Thread(runnableTest, "third thread").start();

    }
}

