package example;


public class FixdPointBinarySearch {
	
	public static int binarySearch(int []a ,int i,int n){
		int low,high,midle=0;
		low=0;
		high=n-1;
		while(low<=high){
			midle=(low+high)>>1;
			if(a[midle]==i){
				System.out.println("this is midle "+midle+"next");
				return midle+1;
			}else if(i>midle){
				low=midle+1;
			}else{
				high=midle-1;
			}
		}
		System.out.println("have nothing");
		return midle+1;
	}
	public static void main(String[] args) {
		int[] a={1,2,3,4,5,7,8,9};
		int b=binarySearch(a,5,7);
		System.out.println(b);
	}
}
