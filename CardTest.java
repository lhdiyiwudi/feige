package learn;

import java.util.Arrays;
import java.util.Random;
/**
 * 某游戏要举办一次抽卡活动，有10种不同的卡，每次抽卡可以抽到这10种卡的一张（已抽到的卡可能重复抽到），
 * 每种卡被抽到的概率都是十分之一。当玩家集齐十种卡各一张时，便可以兑换大奖（玩家是不能把卡片给别人的，
 * 并且每个玩家最多只能兑换一次大奖，也就是说抽到重复的卡只能浪费了）。
 * 请问，玩家想要抽齐一套，所需抽卡次数的数学期望。
 * @author 29284
 *
 */
public class CardTest {

	 private static Random rand = new Random();
	    private static int count = 0;
	    public static void main(String[] args) {
	        long time = System.currentTimeMillis();
	        cal();
	        System.out.println("平均次数：" + count / 10000000.0);
	        System.out.println("耗时：" + (System.currentTimeMillis() - time) / 1000.0);
	    }
	    private static void cal() {
	        boolean[] buf = new boolean[10];
	        for (int i = 0; i < 10000000; i++) {//用一千万个玩家进行模拟抽卡
	            Arrays.fill(buf, false);
	            for (int c = 0; c < 10;) {//十张卡抽齐则跳出循环，下一个人
	                int index = rand.nextInt(10);
	                count++;//每random一次，相当于抽了一张卡
	                if (!buf[index]) {
	                    c++;//如果抽到不重复的卡，则加一
	                    buf[index] = true;
	                }
	            }
	        }
	    }
	
}
