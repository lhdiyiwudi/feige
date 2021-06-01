package example.testForJava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ListTest {
    public static void main(String[] args) {
        MyTask e = new MyTask(12);
        MyTask e2 = new MyTask(2);
        List<MyTask> listt = new ArrayList<MyTask>();
        listt.add(e);
        listt.add(e2);
        for (MyTask myTask : listt) {
            System.out.println(myTask);
        }
        List<String> list = new ArrayList<String>();
        list.add(0, "wqeqweqe");
        list.add(1, "asdasd");
        list.add(2, "zxczxc");
        Collections.sort(list);
        for (String s : list) {
            System.out.println(s);
        }
        Object o = ((ArrayList<String>) list).clone();
        System.out.println(o.toString());
        String[] a = new String[list.size()];
        list.toArray(a);
        for (String s : a) {
            System.out.println(s);
        }
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {

            System.out.println(iterator.next());
        }
    }
}
