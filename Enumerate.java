package example;

public class Enumerate {

	public static void main(String[] args) {
		long time=System.currentTimeMillis();
		int[]a=new int[10000];
		a[1]=1;
		a[2]=2;
		for(int i=2;i<9998;i++){
			a[i]=a[i-1]+a[i-2];
		}
		long timee =System.currentTimeMillis();
		System.out.println(timee-time);
		for(int i=0;i<10000;i++){
			System.out.println(a[i]);
		}
		
	long timeee=System.currentTimeMillis();
		System.out.println(timeee-timee);
	}
}
