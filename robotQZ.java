package example.testForJava;

import java.awt.Point;
import java.util.HashMap;

public class robotQZ {
    //对一个棋子进行8个方向的的依次判断
    public void GiveValue(int i,int j){
        String[] code=new String[8];
        //初始化
        for(int k=0;k<8;k++){
            code[k]="0";
        }
        //得到(i,j)位置的权值
        for(int k=1;k<=4;k++){
            //上方向
            if(j-k<0){
                code[0]+='0';
            }
            else{
                if(Board[i][j-k]==0){
                    code[0]+='0';
                }
                else if(Board[i][j-k]%2==Current%2){
                    code[0]+='1';
                }
                else  {
                    code[0]+="-1";
                }
            }

            //上右方向
            if(i+k>h-1||j-k<0){
                code[1]+='0';
            }
            else{
                if(Board[i+k][j-k]==0){
                    code[1]+='0';
                }
                else if(Board[i+k][j-k]%2==Current%2){
                    code[1]+='1';
                }
                else  {
                    code[1]+="-1";
                }
            }

            //右方向
            if(i+k>h-1){
                code[2]+='0';
            }
            else{
                if(Board[i+k][j]==0){
                    code[2]+='0';
                }
                else if(Board[i+k][j]%2==Current%2){
                    code[2]+='1';
                }
                else  {
                    code[2]+="-1";
                }
            }

            //右下方向
            if(i+k>h-1||j+k>w-1){
                code[3]+='0';
            }
            else{
                if(Board[i+k][j+k]==0){
                    code[3]+='0';
                }
                else if(Board[i+k][j+k]%2==Current%2){
                    code[3]+='1';
                }
                else  {
                    code[3]+="-1";
                }
            }

            //下方向
            if(j+k>w-1){
                code[4]+='0';
            }
            else{
                if(Board[i][j+k]==0){
                    code[4]+='0';
                }
                else if(Board[i][j+k]%2==Current%2){
                    code[4]+='1';
                }
                else  {
                    code[4]+="-1";
                }
            }

            //左下方向
            if(i-k<0||j+k>w-1){
                code[5]+='0';
            }
            else{
                if(Board[i-k][j+k]==0){
                    code[5]+='0';
                }
                else if(Board[i-k][j+k]%2==Current%2){
                    code[5]+='1';
                }
                else  {
                    code[5]+="-1";
                }
            }

            //左方向
            if(i-k<0){
                code[6]+='0';
            }
            else{
                if(Board[i-k][j]==0){
                    code[6]+='0';
                }
                else if(Board[i-k][j]%2==Current%2){
                    code[6]+='1';
                }
                else  {
                    code[6]+="-1";
                }
            }

            //左上方向
            if(i-k<0||j-k<0){
                code[7]+='0';
            }
            else{
                if(Board[i-k][j-k]==0){
                    code[7]+='0';
                }
                else if(Board[i-k][j-k]%2==Current%2){
                    code[7]+='1';
                }
                else  {
                    code[7]+="-1";
                }
            }


        }
        for(int f=0;f<8;f++){
            code[f]+='0';
            if(map.get(code[f])!=null)//根据key得到value
                ThinkBoard[i][j]+=map.get(code[f]);
//			System.out.println(code[f]);
        }
    }
    /****************************************************/
    int Board[][];int Current;
    int ThinkBoard[][];

    int h=0,w=0;
    HashMap<String,Integer> map = new HashMap<String,Integer>();


    Point p=new Point(0,0);

    /****************************************************/
    public Point Think(int B[][],int C){
        Board=B;Current=C;
        h=B.length;//棋盘的高
        w=B[0].length;//棋盘的宽
        ThinkBoard=new int[B.length][B[0].length];
        for(int i=0;i<h;i++)
            for(int j=0;j<w;j++)
                ThinkBoard[i][j]=1;

//		//将每一种棋子相连情况和权值存储到HashMap中
        //一子
        map.put("010000",50);
        map.put("001000",40);
        map.put("000100",30);
        map.put("000010",20);
        map.put("000001",10);

        map.put("0-10000",80);
        map.put("00-1000",70);
        map.put("000-100",60);
        map.put("0000-10",50);
        //两子
        map.put("011000",600);
        map.put("010100",500);
        map.put("010010",400);
        map.put("001100",300);
        map.put("001010",200);
        map.put("000110",100);

        map.put("0-11000",1200);
        map.put("0-10100",1100);
        map.put("0-10010",1000);
        map.put("00-1100",900);
        map.put("00-1010",800);
        map.put("000-110",700);

        map.put("01-1000",1800);
        map.put("010-100",1700);
        map.put("0100-10",1600);
        map.put("001-100",1500);
        map.put("0010-10",1400);
        map.put("0001-10",1300);

        map.put("0-1-1000",2400);
        map.put("0-10-100",2300);
        map.put("0-100-10",2200);
        map.put("00-1-100",2100);
        map.put("00-10-10",2000);
        map.put("000-1-10",1900);
        //三子
        map.put("011100",400000000);
        map.put("011010",300000000);
        map.put("010110",200000000);
        map.put("001110",100000000);

        map.put("011-100",80000);
        map.put("0110-10",70000);
        map.put("0101-10",60000);
        map.put("0011-10",50000);

        map.put("01-1100",40000);
        map.put("01-1010",30000);
        map.put("010-110",20000);
        map.put("001-110",10000);

        map.put("0-11100",90000);
        map.put("0-11010",100000);
        map.put("0-10110",110000);
        map.put("00-1110",120000);

        map.put("01-1-100",140000);
        map.put("01-10-10",130000);
        map.put("010-1-10",120000);
        map.put("001-1-10",110000);

        map.put("0-11-100",100000);
        map.put("0-110-10",100000);
        map.put("0-101-10",100000);
        map.put("00-11-10",100000);

        map.put("0-11-100",50000);
        map.put("0-110-10",50000);
        map.put("0-101-10",50000);
        map.put("00-11-10",50000);

        map.put("0-1-1-100",5000000);
        map.put("0-1-10-10",500000);
        map.put("0-10-1-10",500000);
        map.put("00-1-1-10",500000);
        //四子
        map.put("011110",900000000);

        map.put("0111-10",10000000);
        map.put("011-110",10000);
        map.put("01-1110",10000);
        map.put("0-11110",10000);

        map.put("011-1-10",10000);
        map.put("01-11-10",10000);
        map.put("0-111-10",10000);
        map.put("01-1-110",10000);
        map.put("0-11-110",10000);
        map.put("0-1-1110",10000);

        map.put("01-1-1-10",10000);
        map.put("0-11-1-10",10000);
        map.put("0-1-11-10",10000);
        map.put("0-1-1-110",100000);

        map.put("0-1-1-1-10",100000000);


        //对棋盘每个位置赋权值
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                if(Board[i][j]==0){
                    GiveValue(i,j);
                }
            }
        }


        //找出棋盘权值最大点
        int max=0;
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
//				System.out.print(ThinkBoard[i][j]+"+"+ Board[i][j]+"   ");
                if(ThinkBoard[i][j]>max && Board[i][j]==0){
                    max=ThinkBoard[i][j];
                    p.x=i;p.y=j;
                }
            }
//			System.out.println();
        }
//		System.out.println("px:"+p.x+"  py:"+p.y);
        return p;
    }
    /****************************************************/

}