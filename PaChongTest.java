
package example;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
import java.io.FileWriter;
 
public class PaChongTest {
    public void test() {
        FileWriter fw =null;
        int a =1;
        try {
            String allUrl ="http://www.paoshuzw.com/";
            Document docAll = Jsoup.connect(allUrl).get();
            Elements urlAll = docAll.select(".time");
            Elements hrefAll = urlAll.select("a[href]");
            for (Element hr : hrefAll) {
                String url = hr.attr("abs:href");
                Document doc = Jsoup.connect(url).get();
                //获得文章标题
                Elements main = doc.getElementsByClass("title");
                Elements link = main.select("a[href]");
                for (Element hr1 : link) {
                    String href = hr1.attr("abs:href");
                    Document inDoc = Jsoup.connect(href).get();
                    Elements inMain = inDoc.getElementsByClass("blkContainer");
                    Elements h1 = inMain.select("h1");
                    Elements artInfo = inMain.select(".artInfo");
                    Elements blkContainerSblkCon = inDoc.getElementsByClass("blkContainerSblkCon");
                    Elements p = blkContainerSblkCon.select("p");
                    String title = h1.text();
                    String author = artInfo.select("#pub_date").text();
                    String source = artInfo.select("#media_name").text();
                    String content = "";
                    for (Element contxt : p) {
                        content += contxt.text();
                    }
                    fw = new FileWriter("F:\\读者\\"+hr.text()+".doc", true);
                    fw.write("标题："+title + "\r\n" + author + "\r\n" + source + "\r\n" + content);//这里向文件中输入结果123
                    fw.flush();
//                    System.out.println("标题:" + title);
//                    System.out.println(author);
//                    System.out.println(source);
//                    System.out.println("内容:" + content);
                    System.out.println("导出第"+a+++"个");
                }
                System.out.println("test");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public static void main(String[] args) {
        PaChongTest pt = new PaChongTest();
        pt.test();
 
 
    }
}	