package example;
import javax.imageio.*;//for ImageIO.read
import java.awt.*;//for JFrame and JLabel etc
import javax.swing.*;//for JFrame and JLabel etc

import java.io.*;//for catch (IOException e),File,InputStream, BufferedInputStream,and FileInputStream ect
public class FileDemo
{
   public static void main (String[] args){
   Image image = null;
    try {
        // Read from a file
        File sourceimage = new File("F://TestFile/car.png");  //source.gif图片要与HelloJava.java同在一目录下
        image = ImageIO.read(sourceimage);
    /*
        // Read from an input stream
        InputStream is = new BufferedInputStream(
           new FileInputStream("mid.jpg"));  //mid.jpg图片要与HelloJava.java同在一目录下
        image = ImageIO.read(is);
    
        // Read from a URL
        URL url = new URL("http://www.javaworld.com/images/012407-tipsbox.jpg");
        image = ImageIO.read(url);
        */
    } catch (IOException e) {
    }
    
    // Use a label to display the image
    JFrame frame = new JFrame();
    JLabel label = new JLabel(new ImageIcon(image));
    frame.getContentPane().add(label, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true); 
    //关闭窗口--退出调试
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     }
}