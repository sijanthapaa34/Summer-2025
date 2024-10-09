package CollectionPractice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class Iyslist {
    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add(12);
        list.add(13);
        list.add("sj");
        list.add("sijan");
        System.out.println(list);

        ArrayList list2 = new ArrayList();
        list2.add("sj");
        list2.add("sijan");
        list.addAll(list);

        list.stream()
                .forEach(System.out::println);
//        Iterator iterator = list.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }


        //System.out.println(list2);



//        LinkedList l1 = new LinkedList();
//        l1.add("sijan");
//        l1.add("sam");
//        l1.add("sam");
//        l1.add(12);
//
//        Collections.sort(l1);
//        System.out.println(l1);


    }
}
