package example;

public class SynchronizedThread implements Runnable{

	private static int count;
	
	public SynchronizedThread() {
	count=0;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//synchronized(this){
			{
				for(int i=0;i<5;i++){
				try {
					System.out.println(Thread.currentThread().getName()+":"+(count++));
					Thread.sleep(1000);
				} catch (InterruptedException e) {
						e.printStackTrace();
					// TODO: handle exception
				}
			}
		}
	}
	public int getCount() {
		return count;
	}
public static void main(String[] args) {
	SynchronizedThread synchronizedThread=new SynchronizedThread();
	Thread thread1=new Thread(synchronizedThread);
	Thread thread2=new Thread(synchronizedThread);
	thread1.start();
	thread2.start();
}
}
