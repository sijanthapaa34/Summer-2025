package CollectionPractice;

import java.util.*;

public class Hashsetdemo {
    public static void main(String[] args) {
//        ArrayList<> set = new ArrayList<>();
////        HashSet set = new HashSet();
//        set.add(1);
//        set.add("Sijan");
//        set.add(2.255);
//        set.add("Sijan");
////        System.out.println(set);
//        Iterator iterator = set.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());

        TreeMap map = new TreeMap();
        map.put(1,"sijan");
        map.put(2,"sam");
        map.put(3,"raam");
        map.put(4,"krishna");
        System.out.println(map);

        map.replace(3,  "Harii");
       // System.out.println(map.containsKey(3));
      //  Set set = map.entrySet();
      //  System.out.println(set);

//        ArrayList list6 =  map.entrySet();
//        System.out.println(list6);
//        Iterator iterator = set.iterator();
//        while (iterator.hasNext()) {
//            //System.out.println(iterator.next());
//            Map.Entry entry = (Map.Entry)iterator.next();
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }
//        Iterator itr = map.keySet().iterator();
//        while(itr.hasNext()) {
//            Object key = itr.next();
//            Object value = map.get(key);
//            System.out.println(key +" "+value);
//        }
        System.out.println(map.subMap(1 ,3));

        }
    }

