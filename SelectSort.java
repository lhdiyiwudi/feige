package example;

public class SelectSort {
	
	public static int[] selectSort(int[] arr,int n) {
		for (int i = 0; i < n - 1; i++) {//外循环是趟数，每一趟就位置加一
            int index = i;
            int j;
            // 找出最小值得元素下标
            for (j = i + 1; j < n; j++) {//内循环是每一趟一个元素与其他元素的每一次比较
                if (arr[j] < arr[index]) {
                    index = j;//每次将小的赋给index，循环结束后index就是最小的
                }
            }
            int tmp = arr[index];
            arr[index] = arr[i];
            arr[i] = tmp;//将找到的数赋给外循环的那个循环位置
		}
		return arr;
	}
	public static void main(String[] args) {
		int[] arr={12,3,23,2,3,2314,23,45,3425,34,53,45,4,65,7,56,786,78,34};
		arr=selectSort(arr,arr.length);
		for (int i : arr) {
		System.out.println(i);	
		}
	}
}
