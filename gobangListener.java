package example.testForJava;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class gobangListener extends MouseAdapter implements ActionListener{
    int mode=2;//2为人人对战1为人机对战
    int x0=0,y0=0,xp,yp;
    Color color1=Color.black;
    Color color2=Color.WHITE;
    static int Current=1;//当前玩家（默认黑先行）（奇数是黑，偶数是白）
    public void sendCurrent(int C){
        Current=C;
    }
    Graphics2D g;


    public void sendGraphics(Graphics g){
        this.g=(Graphics2D)g;
        this.g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }
    /*******************点的是按钮****************************************************/
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("开始新一局")){
            Restart();
        }
        else if(e.getActionCommand().equals("悔棋")){
            int n=1;
            if(mode==1)n++;
            for(int k=0;k<n;k++)
            {
                if(Current>1){
                    Current--;
                    for(int i=0;i<Board.length;i++)
                    {
                        for(int j=0;j<Board[i].length;j++)
                        {
                            if(Board[i][j]==Current){
                                xp=i;yp=j;
                            }
                        }
                    }
                    Board[xp][yp]=0;
                    gm.paint(g);
                }
            }

        }
        else if(e.getActionCommand().equals("认输")){//人人对战需要
            JFrame jf=new JFrame();
            if(Current%2==1){
                JOptionPane.showMessageDialog(jf, "黑子认输");
                Restart();
            }
            else {
                JOptionPane.showMessageDialog(jf, "白子认输");
                Restart();
            }
        }
        else if(e.getActionCommand().equals("退出")){
            System.exit(0);
        }
        else if(e.getActionCommand().equals("人人对战")){
//			 System.out.println("人人对战");
            mode=2;
        }
        else if(e.getActionCommand().equals("人机对战")){
//			 System.out.println("人机对战");
            mode=1;
        }
    }
    /*****************点的是棋盘*******************************************************/
    public void mouseClicked(MouseEvent e)
    {
        Correct(e.getX(),e.getY());//修正坐标
        if(x0!=0&&y0!=0&&Board[xp][yp]==0){//可以下子
            if(Current%2==1){//下黑子
                g.setColor(color1);
                Put(g,x0,y0);
//				 Board[xp][yp]=Current;
                g.setColor(color2);
            }
            else{//下白子
                g.setColor(color2);
                Put(g,x0,y0);
//				 Board[xp][yp]=Current;
                g.setColor(color1);
            }
            if(Judger()==1){//当前玩家胜
                JFrame jf=new JFrame();
                if(Current%2==1){
                    JOptionPane.showMessageDialog(jf, "黑子胜");
                    Restart();
                }
                else {
                    JOptionPane.showMessageDialog(jf, "白子胜");
                    Restart();
                }
            }
            else{
                Current++;//换玩家
                if(mode==1){//如果是人机模式则电脑下一子
                    robotQZ r=new robotQZ();
                    Point p=r.Think(Board,Current);
                    xp=p.x;yp=p.y;
                    Put(g,Match[p.x][p.y].x,Match[p.x][p.y].y);
//					 Board[p.x][p.y]=Current;
                    if(Judger()==1){//当前玩家胜
                        JFrame jf=new JFrame();
                        if(Current%2==1){
                            JOptionPane.showMessageDialog(jf, "黑子胜");
                            Restart();
                        }
                        else {
                            JOptionPane.showMessageDialog(jf, "白子胜");
                            Restart();
                        }
                    }
                    else{
                        Current++;//电脑下完没赢
                    }
                }
            }

        }
        x0=0;y0=0;//坐标归0
    }
    /********重开一局*****************************************************/
    gobangMain gm;
    public void sendGobangMain(gobangMain gmm){
        gm=gmm;
    }
    public void Restart()
    {
        for(int i=0;i<Board.length;i++){
            for(int j=0;j<Board[i].length;j++){
                Board[i][j]=0;//棋盘为空，未下子
            }
        }
        Current=1;
        gm.paint(g);
    }
    /********判断输赢返回赢家*****************************************************/
    public int Judger()
    {
        int count = 1;
        //竖直
        for (int i = 1; i < 5; i++)
        {
            if(xp-i>=0&&Board[xp-i][yp]%2==Current%2&&Board[xp-i][yp]!=0)count++;
            else break;
        }
        for (int i = 1; i < 5; i++)
        {
            if(xp+i<=20&&Board[xp+i][yp]%2==Current%2&&Board[xp+i][yp]!=0)count++;
            else break;
        }
        if (count == 5) return 1;
        //水平
        count = 1;
        for (int i = 1; i < 5; i++)
        {
            if(yp-i>=0&&Board[xp][yp-i]%2==Current%2&&Board[xp][yp-i]!=0)count++;
            else break;
        }
        for (int i = 1; i < 5; i++)
        {
            if(yp+i<=20&&Board[xp][yp+i]%2==Current%2&&Board[xp][yp+i]!=0)count++;
            else break;
        }
        if (count == 5) return 1;
        //左上到右下
        count = 1;
        for (int i = 1; i < 5; i++)//右下
        {
            if(xp-i>=0&&yp-i>=0&&Board[xp-i][yp-i]%2==Current%2&&Board[xp-i][yp-i]!=0)count++;
            else break;
        }
        for (int i = 1; i < 5; i++)//左下
        {
            if(xp+i<=20&&yp+i<=20&&Board[xp+i][yp+i]%2==Current%2&&Board[xp+i][yp+i]!=0)count++;
            else break;
        }
        if (count == 5) return 1;
        //右上到左下
        count = 1;
        for (int i = 1; i < 5; i++)//左下
        {
            if(xp-i>=0&&yp+i<=20&&Board[xp-i][yp+i]%2==Current%2&&Board[xp-i][yp+i]!=0)count++;
            else break;
        }
        for (int i = 1; i < 5; i++)//右上
        {
            if(xp+i<=20&&yp-i>=0&&Board[xp+i][yp-i]%2==Current%2&&Board[xp+i][yp-i]!=0)count++;
            else break;
        }
        if (count == 5)  return 1;

        return 0;
    }


    /********下子*****************************************************/
    public void Put(Graphics2D g,int x,int y){//下子(x,y)为点击的坐标
        g.fillOval(x-15, y-15, 30, 30);
        Board[xp][yp]=Current;
    }

    /********修正坐标*****************************************************/
    int Board[][]=new int[15][15];//记录下子情况0未下子，1黑子，2白子
    Point Match[][]=new Point[15][15];//将屏幕坐标与棋盘坐标对应起来
    public void sendMatch(Point[][] M){
        Match=M;
    }
    public void sendBoard(int[][] B){
        Board=B;
    }
    private void Correct(int x,int y)//在周围10个像素点内 将鼠标粗略的坐标修正为和棋盘对应
    //屏幕坐标返回到x0,y0,棋盘返回到xp,yp
    {
        int  p=10;//修正精度
        int xx=x,yy=y;
        for(int i=0;i<p;i++)//先向左修正x
        {
            xx=x-i;
            for(int j=0;j<Board.length;j++)
            {
                if(Match[j][0].x==xx){
                    x0=xx;xp=j;break;
                }
            }
        }
        for(int i=1;i<p;i++)//向右修正x
        {
            xx=x+i;
            for(int j=0;j<Board.length;j++)
            {
                if(Match[j][0].x==xx){
                    x0=xx;xp=j;break;
                }
            }
        }
        for(int i=0;i<p;i++)//先向上修正y
        {
            yy=y-i;
            for(int j=0;j<Board.length;j++)
            {
                if(Match[0][j].y==yy){
                    y0=yy;yp=j;break;
                }
            }
        }
        for(int i=1;i<p;i++)//先向下修正y
        {
            yy=y+i;
            for(int j=0;j<Board.length;j++)
            {
                if(Match[0][j].y==yy){
                    y0=yy;yp=j;break;
                }
            }
        }
    }
}