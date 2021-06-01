package example.testForJava;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class PropertiesDemo {
    public static void main(String[] args) {
        Properties properties=new Properties();
        Set status;
        String string;
        properties.put("sdfd","dfsdf");
        properties.put("123","132");
        properties.put("123","sdfdf");
        properties.put("Florida","132");
        status=properties.keySet();
        Iterator iterator=status.iterator();
        while (iterator.hasNext()){
            string= (String) iterator.next();
            System.out.println(string+"   "+properties.getProperty(string));

        }
        string = properties.getProperty("Florida", "Not Found");
        System.out.println("The capital of Florida is "
                + string + ".");

    }
}
