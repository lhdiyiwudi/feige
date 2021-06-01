package example.testForJava;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GradientRamp extends JPanel {
    private int height = 700;
    private int width = 1200;
    private BufferedImage bufferedImage = null;


    public GradientRamp() {
        this.setPreferredSize(new Dimension(width, height));
    }

    private void initColor() {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics graphics = bufferedImage.getGraphics();
        int w = width / 6;//w=100
        for (int i = 0; i < w; i++) {
            int x = 0;
            int d = (int) (i * (255.0 / w));//d=2,4,6……
            graphics.setColor(new Color(255, d, 0));
            graphics.drawLine(i + w * x, 0, i + w * x++, height);

            graphics.setColor(new Color(255 - d, 255, 0));
            graphics.drawLine(i + w * x, 0, i + w * x++, height);

            graphics.setColor(new Color(0, 255, d));
            graphics.drawLine(i + w * x, 0, i + w * x++, height);

            graphics.setColor(new Color(0, 255 - d, 255));
            graphics.drawLine(i + w * x, 0, i + w * x++, height);

            graphics.setColor(new Color(d, 0, 255));
            graphics.drawLine(i + w * x, 0, i + w * x++, height);

            graphics.setColor(new Color(255, 1, 255 - d));
            graphics.drawLine(i + w * x, 0, i + w * x++, height);
            try {
                Thread.currentThread().sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("begin paint cut-off rule");
        graphics.setColor(Color.BLACK);
        for (int i = 1; i < w; i++) {
            graphics.drawLine(i * w, 0, i * w, height);
        }
        repaint();

    }

    public void paint(Graphics graphics) {
        graphics.drawImage(bufferedImage, 0, 0, null);
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("gradientramp result");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GradientRamp gradientRamp = new GradientRamp();

        jFrame.getContentPane().add(gradientRamp);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        gradientRamp.initColor();
    }
}
