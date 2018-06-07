package utility;

import java.util.*;

/**
 * Created by appledev131 on 9/4/16.
 */
public class MyLinkHashMap {
    private static final int MAX_ENTRIES = 15;

    public static void main(String[] args) {
        LinkedHashMap lhm = new LinkedHashMap(MAX_ENTRIES + 1, .75F, false) {

            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > MAX_ENTRIES;
            }
        };
        lhm.put(0, "H");
        lhm.put(1, "E");
        lhm.put(2, "L");
        lhm.put(3, "L");
        lhm.put(4, "O");
        lhm.put(2, "Lad");
        System.out.println("" + lhm);
        Set set = lhm.entrySet();

        if (checkContains(set, "E") == true) {
        } else {
            lhm.put(5, "E");
        }

        System.out.println("" + lhm);


        LinkedHashMap lhm2 = new LinkedHashMap();
        lhm2.put(0, "a");
        lhm2.put(1, "b");
        lhm2.put(2, "c");
        lhm2.put(3, "d");
        LinkedHashMap lhm3 = new LinkedHashMap();
        lhm2.put(0, "c");
        lhm2.put(1, "d");
        lhm2.put(2, "e");
        lhm2.put(3, "f");

        String a = "<1><2><a><3><4><5><6>";
        String[] extG1 = a.replaceFirst("<", "").split("><");


        LinkedHashMap axl = new LinkedHashMap();
        for (int i = 0; i < extG1.length; i++) {
            System.out.println("" + extG1[i]);
            axl.put(i, extG1[i]);
        }

        String b = "<1><2><3><4><3><4><5><6>";
        String[] extG2 = b.replaceFirst("<", "").split("><");
        LinkedHashMap bxl = new LinkedHashMap();
        for (int i = 0; i < extG2.length; i++) {
            System.out.println("" + extG2[i]);
            bxl.put(i, extG2[i]);
        }

        StringBuilder neMap = cbContains(extG1, extG2);
        System.out.println("" + neMap);
    }

    public static boolean checkContains(Set inSet, String tValue) {
        boolean result = false;
        for (Iterator iter = inSet.iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iter.next();
            String value = (String) entry.getValue();
            if (tValue.equals(value)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static LinkedHashMap cContains(LinkedHashMap lhm1, LinkedHashMap lhm2) {
        LinkedHashMap result = null;
        for (int i = 0; i < lhm1.size(); i++) {
            boolean flag4quit = false;
            for (int j = 0; j < lhm2.size(); j++) {
                if (lhm1.get(i).equals(lhm2.get(j))) {
                    lhm2.remove(j);
                    i = 0;
                    j = 0;
                } else {
                    flag4quit = true;
                    break;
                }
            }
            if (flag4quit == true) {
                lhm1.putAll(lhm2);
                break;
            }
        }

        return lhm1;
    }

    public static StringBuilder cbContains(String[] lhm1, String[] lhm2) {
        StringBuilder result = new StringBuilder();
        boolean flag4quit = false;
        int endi = 0;
        int end4o = 0;

        boolean flag4diffg = false;
        boolean flag4sameg = false;
        for (int i = 0; i < lhm1.length; i++) {
            boolean flag4match = false;
            boolean flag4dismatch = false;

            for (int j = endi; j < lhm2.length; j++) {
                if (!lhm1[i].equals(lhm2[j])) {     //  不同
                    flag4dismatch = true;     //  标记不同
                    if (flag4match == false) {      //  若一直都不同,继续
                        if (j == lhm2.length - 1) {
                            flag4diffg = false;
                        }
                    } else {        //  若曾经相同
                        if (flag4diffg == true && flag4sameg == true) {
                            flag4quit = true;   //  标记准备删除
                            break;      //  退出
                        }
                    }

                } else {        //  相同
                    if (flag4dismatch == true) {      //  若曾经不同
                        flag4match = true;      //  标记相同
                        lhm2[j] = new String("");       //  删除相同
                        endi = j;
                    }
                }
                if (j == lhm2.length - 1) {
                    if (flag4match == true) {
                        flag4sameg = true;
                    }
                }
            }
            if (flag4quit == true) {
                end4o = i;
                break;
            }
        }
        if (flag4quit == true) {        //  开始删除
            for (int newi0 = 0; newi0 < end4o + 1; newi0++) {
                result.append(lhm1[newi0]);
            }
            for (int newi = endi; newi < lhm2.length; newi++) {
                if (!lhm2[newi].equals("")) {
                    result.append(lhm2[newi]);
                }
            }
        }
        return result;
    }

    public static LinkedHashMap checkContains(LinkedHashMap lhm1, LinkedHashMap lhm2) {
        Set inSet = lhm1.entrySet();
        Set inSet2 = lhm2.entrySet();
        LinkedHashMap result = new LinkedHashMap();
        for (Iterator iter = inSet.iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iter.next();
            String value = (String) entry.getValue();
            for (Iterator iter2 = inSet2.iterator(); iter2.hasNext(); ) {
                Map.Entry entry2 = (Map.Entry) iter2.next();
                String value2 = (String) entry2.getValue();
                if (value.equals(value2)) {

                }
            }
        }
        return result;
    }


}
