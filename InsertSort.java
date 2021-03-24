package example;
public class InsertSort{
    public static int[] insertSort(int[] arr)
    {
        for (int i = 1; i < arr.length; i++)//每次选择一个插入
        {
            int k = arr[i];//k用来代替arr【i】进行大小判断
            int j = i;//j内循环用来判断当前位与前一位的大小
            while (j > 0 && arr[j - 1] > k)//如果要插入的元素的前一位比他大则前一位后移
            {
                arr[j] = arr[j - 1];//元素后移
                j--;//变量前移
            }
            arr[j] = k;//k插入到这
       }
        return arr;
    }
		public static void main(String[] args) {
			int[] arr={1,23,3,44,5,4,56,57,6,7,568,67,89,67,986,7,6,79,23,86,78,6,8};
			arr=insertSort(arr);
			for (int i : arr) {
				System.out.println(i);
			}
		}
}