package utility;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhupx on 2016/11/28.
 */
public class compareHashMapObj {

    public static boolean compareMapByKeySet(Map<String, String> map1, Map<String, String> map2) {
        if (map1.size() != map2.size()) {
            return false;
        }
        String tmp1;
        String tmp2;
        boolean b = false;
        for (String key : map1.keySet()) {
            if (map2.containsKey(key)) {
                tmp1 = map1.get(key);
                tmp2 = map2.get(key);
                if (null != tmp1 && null != tmp1) {

                    if (tmp1.equals(tmp2)) {
                        b = true;
                        continue;
                    } else {
                        b = false;
                        break;
                    }
                } else if (null == tmp1 && null == tmp2) {
                    b = true;
                    continue;
                } else {
                    b = false;
                    break;
                }
            } else {
                b = false;
                break;
            }
        }
        return b;
    }


    public static boolean compareMapByEntrySet(Map<String, String> map1, Map<String, String> map2) {
        if (map1.size() != map2.size()) {
            return false;
        }
        String tmp1;
        String tmp2;
        boolean b = false;
        for (Map.Entry<String, String> entry : map1.entrySet()) {
            if (map2.containsKey(entry.getKey())) {
                tmp1 = entry.getValue();
                tmp2 = map2.get(entry.getKey());
                if (null != tmp1 && null != tmp1) {   //都不为null
                    if (tmp1.equals(tmp2)) {
                        b = true;
                        continue;
                    } else {
                        b = false;
                        break;
                    }
                } else if (null == tmp1 && null == tmp2) {  //都为null
                    b = true;
                    continue;
                } else {
                    b = false;
                    break;
                }
            } else {
                b = false;
                break;
            }
        }
        return b;
    }


    public static void main(String[] args) {

        Map<String, String> hs1 = new HashMap<String, String>();
        Map<String, String> hs2 = new HashMap<String, String>();

        hs1.put("001key", "001value");
        hs1.put("002key", "002value");
        hs1.put("003key", "003value");
        hs1.put("004key", "004value");
        hs1.put("005key", "005value");
        hs1.put("006key", "006value");
        hs1.put("007key", "007value");

        hs2.put("001key", "001value");
        hs2.put("002key", "002value");
        hs2.put("006key", "006value");
        hs2.put("005key", "005value");
        hs2.put("007key", "007value");
        hs2.put("004key", null);
        hs2.put("003key", "003value");


        long st1 = System.currentTimeMillis();
        boolean b1 = compareMapByKeySet(hs1, hs2);
        long et1 = System.currentTimeMillis();

        System.out.println("使用keySet方法比较的结果是: " + b1 + ", 耗时是: " + (et1 - st1));

        long st2 = System.currentTimeMillis();
        boolean b2 = compareMapByEntrySet(hs1, hs2);
        long et2 = System.currentTimeMillis();

        System.out.println("使用entrySet方法比较的结果是: " + b2 + ", 耗时是: " + (et2 - st2));


/*
        long starttime1=System.currentTimeMillis();
        for(String key:hs1.keySet()){
            System.out.println(hs1.get(key));
        }
        long endtime1=System.currentTimeMillis();

        System.out.println(endtime1-starttime1);

        long starttime2=System.currentTimeMillis();
        for(Map.Entry<String, String> entry:hs1.entrySet()){
            System.out.println(entry.getValue());
        }
        long endtime2=System.currentTimeMillis();

        System.out.println(endtime2-starttime2);
        */
    }
}
