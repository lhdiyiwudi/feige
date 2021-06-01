package example.testForJava;

import javax.swing.*;
import java.awt.*;


@SuppressWarnings("serial")
public class gobangMain extends JPanel {

    public Graphics gr;
    gobangListener gl = new gobangListener();

    public static void main(String[] args) {
        gobangMain gm = new gobangMain();
        gm.initUI();
    }

    /*****************************************************************/
    public void initUI() {


        JFrame frame = new JFrame();
        frame.setTitle("五子棋byHWJ");
        frame.setLocation(300, 100);
        frame.setSize(750, 600);
        frame.setResizable(false);//窗口大小不可调
        BorderLayout bl = new BorderLayout();//构造新的边框布局，组件之间没有缝隙
        frame.setLayout(bl);
        //左面板
		this.setBackground(Color.lightGray);
        this.setBackground(new Color(110, 200, 200));
        this.setPreferredSize(new Dimension(600, 600));//设置组件首选大小
        this.addMouseListener(gl);
        frame.add(this, BorderLayout.CENTER);
        //右面板
        JPanel eastPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(150, 600));//设置组件首选大小
        eastPanel.setBackground(frame.getBackground());
        frame.add(eastPanel, BorderLayout.EAST);

        String Array[] = {"开始新一局", "悔棋", "认输", "退出", "对战模式：", "人机对战", "人人对战", "（均为黑子先下）"};
        ButtonGroup bg = new ButtonGroup();

        for (int i = 0; i < Array.length; i++) {
            if (i < 4) {
                //3步
                JButton button = new JButton(Array[i]);
                button.setPreferredSize(new Dimension(140, 60));
                button.addActionListener(gl);
                eastPanel.add(button);
            } else if (i == 4) {//人机
                JLabel label = new JLabel(Array[i]);
                label.setPreferredSize(new Dimension(140, 60));
                eastPanel.add(label);
            } else if (i == 7) {//人人
                JLabel label = new JLabel(Array[i]);
                label.setPreferredSize(new Dimension(140, 60));
                eastPanel.add(label);
            } else {
                JRadioButton button = new JRadioButton(Array[i]);
                button.setPreferredSize(new Dimension(100, 60));
                button.setOpaque(true);//是否透明
                button.addActionListener(gl);
                eastPanel.add(button);

                bg.add(button);
                button.setSelected(true);// 设置默认选中一个单选按钮人机或者人人
            }
        }

        frame.setVisible(true);
        gr = this.getGraphics();//获得画笔
        gl.sendGraphics(gr);//listen里面得

        for (int i = 0; i < Board.length; i++) {
            //初始化两个棋盘数组
            for (int j = 0; j < Board[i].length; j++) {
                Board[i][j] = 0;//棋盘为空，未下子
                Match[i][j] = new Point(xl + d * i, yl + d * j);//将屏幕坐标与棋盘坐标对应起来
            }
        }
        gl.sendMatch(Match);
        gl.sendBoard(Board);
        gl.sendGobangMain(this);
        gl.sendCurrent(Current);
    }

    /***************************************************************************/
    //重写JPanel的paint方法
    int xl = 55, yl = 55;//棋盘左上角
    int d = 35;//间隔
    static int Board[][] = new int[15][15];//记录下子情况0未下子，1黑子，2白子
    static Point Match[][] = new Point[15][15];//将屏幕坐标与棋盘坐标对应起来
    static int Current = 1;//当前玩家（默认黑先行）（奇数是黑，偶数是白）

    public void paint(Graphics g) {
        super.paint(g);

        gr = (Graphics2D) g;
        ((Graphics2D) gr).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < Board.length; i++) {//画棋盘
            g.drawString(String.valueOf(i), xl + d * i - 5, yl - 30);
            g.drawLine(xl + d * i, yl, xl + d * i, yl + (Board.length - 1) * d);//竖线y坐标不含i
            g.drawString(String.valueOf(i), xl - 35, xl + d * i + 5);
            g.drawLine(xl, yl + d * i, xl + (Board.length - 1) * d, yl + d * i);//横线x坐标不含i
        }
        //填充颜色椭圆
        gr.setColor(Color.black);
        gr.fillOval(Match[3][3].x - 4, Match[3][3].y - 4, 8, 8);
        gr.fillOval(Match[3][11].x - 4, Match[3][11].y - 4, 8, 8);
        gr.fillOval(Match[11][3].x - 4, Match[11][3].y - 4, 8, 8);
        gr.fillOval(Match[11][11].x - 4, Match[11][11].y - 4, 8, 8);
        for (int i = 0; i < Board.length; i++) {
            for (int j = 0; j < Board[i].length; j++) {
                if (Board[i][j] % 2 == 1) {
                        gr.setColor(Color.black);
                    Put((Graphics2D) gr, Match[i][j].x, Match[i][j].y);
                } else if (Board[i][j] % 2 == 0 && Board[i][j] != 0) {
                    gr.setColor(Color.WHITE);
                    Put((Graphics2D) gr, Match[i][j].x, Match[i][j].y);
                }
            }
        }

    }

    public void Put(Graphics2D g, int x, int y) {//下子(x,y)为点击的坐标
        g.fillOval(x - 15, y - 15, 30, 30);
    }
}