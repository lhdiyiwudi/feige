package example;

public class ShellSort {
	
	public static void main(String[] args) {
		  int[] arr = {5, 1, 7, 3, 1, 6, 9, 4};
	        shellSort(arr);

	        for (int i : arr) {
	            System.out.print(i + "\t");
	        }
	}
	  private static void shellSort(int[] arr) {
	        //step:步长
	        for (int step = arr.length / 2; step > 0; step /= 2) {//步长的变化依次变小
	            //对一个步长区间进行比较 [step,arr.length)
	            for (int i = step; i < arr.length; i++) {//固定步长下的隔步长距离的比较，i+即后移一位的另一步长组的比较
	                int value = arr[i];
	                int j;
	                //对步长区间中具体的元素进行比较
	                for (j = i - step; j >= 0 && arr[j] > value; j -= step) {//固定步长的数据大的依次后移
	                    //j为左区间的取值，j+step为右区间与左区间的对应值。
	                    arr[j + step] = arr[j]; 
	                }
	                //此时step为一个负数，[j + step]为左区间上的初始交换值
	                arr[j + step] = value;  
	            }
	        }
	    }
	  
	
}
