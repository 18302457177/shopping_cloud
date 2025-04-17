package com.cart.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSONarrayUtil {

    public static List<Integer> ToList(String s){
        int len = s.length();
        String[] split = s.substring(1, len-1).split(",");
        ArrayList<Integer> integers = new ArrayList<>();
        for (String string : split) {
            integers.add(Integer.parseInt(string));
        }
        return integers;

    }

    public static String ToString(List<Integer> list){

        StringBuilder pre = new StringBuilder();
        pre.append("[");
        for (int i = 0; i < list.size(); i++) {
            String s = String.valueOf(list.get(i));
            if (i== list.size()-1){
                pre.append(s).append("]");
                break;
            }
            pre.append(s).append(",");
        }
        return pre.toString();

    }

}
