package example.testForJava;

import com.mchange.v2.collection.MapEntry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapTest {
    public static void main(String[] args) {


        Map<Integer, String> map = new HashMap<Integer, String>();
        for (Integer i = 0; i < 10000; i++) {
            map.put(i,"wer"+i);
        }
        map.put(12, "qweqwe");
        map.put(1, "sdfsd");
        map.put(1,"wer");
       long a= System.currentTimeMillis();
        for (Integer key : map.keySet()) {
            System.out.println(key + "  " + map.get(key));
        }
        long aa=System.currentTimeMillis();
        System.out.println(aa-a);
        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            System.out.println(entry.getKey() + entry.getValue());
        }
        long b=System.currentTimeMillis();
        System.out.println(b-aa);
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + entry.getValue());
        }
        for (String s : map.values()) {
            System.out.println(s);
        }
    }
}