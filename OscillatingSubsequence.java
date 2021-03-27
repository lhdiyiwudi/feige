package example;

public class OscillatingSubsequence {
	final static int BEGIN = 0;
	final static int UP = 1;
	final static int DOWN = 2;
	static int START = BEGIN;
	static int Max_len = 1;	  
		  
	public static int Solution(int[] nums){
		if(nums.length<=2){
			System.err.println("array length <=2");
			return 0;
		}
		for(int i=1;i<nums.length;i++ ){
			switch (START) {
			case BEGIN:
				if(nums[i-1]<nums[i]){
					START=UP;
					Max_len++;
				}else if(nums[i-1]>nums[i]){
					START=DOWN;
					Max_len++;
				}
				break;
			case UP:
				if(nums[i-1]>nums[i]){
					START=DOWN;
					Max_len++;
				}
				break;
			case DOWN:
				if(nums[i-1]<nums[i]){
					START=UP;
					Max_len++;
				}
				break;
			default:
				break;
			}
		}
		return Max_len;
		
	}

	public static void main(String[] args) {	
		int[] nums=new int[]{4,234,23,34,32,43,43};
		System.out.println(Solution(nums));
		//	String [] aStrings=new String[]{"12","123"};
	}
}
