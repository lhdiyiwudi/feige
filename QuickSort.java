package example;

public class QuickSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
			List<int> qew=new ArrayList<int>();
	}
	public static int partition(int[] arr,int left,int right){
        int pivot = arr[left];
        while(left < right){
            while(left<right && arr[right] >= pivot)
                right--;
            arr[left] = arr[right];
            while(left < right && arr[left]<= pivot)
                left++;
            arr[right] = arr[left];
        }
        arr[left] = pivot;
        return left;
    }
	
}
