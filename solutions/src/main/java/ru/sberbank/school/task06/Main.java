package ru.sberbank.school.task06;

import java.sql.SQLOutput;
import java.util.*;

public class Main {
    public static void main(String[] args) {
//        List<Integer> list = new ArrayList<>(Arrays.asList(8, 1, 3, 5, 6, 4));
//        for (Integer l : list){
//            System.out.println(l);
//        }
//
//        System.out.println("Sort: " + CollectionUtils.range(list, 3,6, Comparator.naturalOrder()));

        String key1 = "fuck";
        String key2 = "fuc";
        String key3 = "fuck";

        CountMap<String> map = new CountMapImpl<>();

//        map.add(key1);
//        map.add(key2);
//        map.add(key3);

//        System.out.println(map.getCount(key3));
//        System.out.println(map.size());
//        System.out.println(map.remove(key1));
        System.out.println(map.size());
//        System.out.println(map.toString());
    }
}
