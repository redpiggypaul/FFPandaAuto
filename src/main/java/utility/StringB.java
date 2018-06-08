package utility;


import LocalException.FFPandaException;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.sort;


public class StringB {
    public static String filter4space(String s) {
        int i = s.length();// 字符串最后一个字符的位置
        int j = 0;// 字符串第一个字符
        int k = 0;// 中间变量
        char[] arrayOfChar = s.toCharArray();// 将字符串转换成字符数组
        while ((j < i) && (arrayOfChar[(k + j)] <= ' '))
            ++j;// 确定字符串前面的空格数
        while ((j < i) && (arrayOfChar[(k + i - 1)] <= ' '))
            --i;// 确定字符串后面的空格数
        return (((j > 0) || (i < s.length())) ? s.substring(j, i) : s);// 返回去除空格后的字符串
    }


    public static boolean assertTestResult(String testResult, String exceptedResult) throws FFPandaException {
        boolean result = true;
        testResult = testResult.toLowerCase();
        String sampleResult = testResult;
        exceptedResult = filter4space(exceptedResult).toLowerCase();
        if (!exceptedResult.equalsIgnoreCase("ingnore")) {
            if (!exceptedResult.equalsIgnoreCase("regular")) {
                if (!sampleResult.equals("teststart")) {
                    String[] excResult = exceptedResult.split(";");
                    for (int ind = 0; (ind < excResult.length) && result == true; ind++) {
                        sampleResult = testResult;
                        String[] singlePartResult = excResult[ind].split(" ");
                        for (int dind = 0; (dind < singlePartResult.length) && result == true; dind++) {
                            if (!sampleResult.contains(singlePartResult[dind])) {
                                result = false;
                                break;
                            } else {
                                System.out.println("start : " + sampleResult.indexOf(singlePartResult[dind]));
                                System.out.println("end : " + sampleResult.length());
                                sampleResult = sampleResult.substring(sampleResult.indexOf(singlePartResult[dind]), sampleResult.length() - 1);
                                System.out.println(sampleResult);
                            }
                        }
                    }
                }
            } else if (exceptedResult.equalsIgnoreCase("regular")) {
                if (sampleResult.equals("teststart")) {
                    result = true;
                } else if (testResult.contains("err")) {
                    result = false;
                } else {
                    System.out.println("");
                    result = false;
                }
            }
        } else {
            result = true;
        }
        return result;
    }


    public static StringBuffer textContentCheck(StringBuffer inRes) throws Exception {
        String content4input = "";
        String content4check = "";
        StringBuffer result = new StringBuffer();
        //     result.delete(0, result.length());
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("*** in textContentCheck ***");
        try {
            if ((content4input != null) && (content4input != "") && (content4check != null) && (content4check != "")) {
                if (content4check.equals(content4input)) {
                    result.append(inRes);
                    System.out.println("result in SB : " + result);
                } else {
                    result.append("Element content failed to match input");
                }
            } else if ((content4check != null) && (content4check != "")) {
                result.append(content4check);
            } else if ((content4input != null) && (content4input != "")) {
                result.append("checkcontent line missing in operation XML");
            } else {
                result.append(inRes);
            }
            System.out.println("result in SB end : " + result);
            return result;
        } catch (Exception e) {
            result.append("Exception appear during element content compare");
            throw new FFPandaException("BaseAction : textContentCheck : " + e.getMessage());
        }
    }


    public void change(final String str) {
        Class<?> v = str.getClass();
        try {
            Field field = v.getDeclaredField("value"); //修改值
            Field field1 = v.getDeclaredField("count"); //修改长度属性
            field1.setAccessible(true);
            field.setAccessible(true);
            Object object = field.get(str);
            char[] charValue = {'s', 'u', 'c', 'c', 'e', 's', 's'};
            /*for (int i = 0; i < charValue.length; i++) {
                charValue[i] = 'a';
            }*/
            field1.set(str, charValue.length);
            field.set(str, charValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkStartwithNumInd(String extPara) {
        boolean result = false;
        String temp = null;

        //   extPara = new String(extPara.replaceAll(" +",""));
        System.out.println(extPara);
        Pattern test = Pattern.compile("(^(\\d+)[A-Za-z\\s]+)$");
        //  Matcher matcher = pattern.matcher("10.11");
        Matcher matcher2dig = test.matcher(extPara);
        //  System.out.println(matcher.matches());
        if (matcher2dig.matches()) {
            System.out.println("Match the Start with Num ind");
            result = true;
        }
        return result; //TODO
    }

    public static String getNum(String str) {
        String dest = "";
        if (str != null) {
            dest = str.replaceAll("[^0-9]", "");
        }
        return dest;
    }

    public static String getFirstNumFromStr(String str) {
        String dest = "";

        if (str != null && checkStartwithNumInd(str)) {
            dest = str.replaceAll("[^0-9]", "");
        }

        return dest;
    }


    public static ArrayList<String> removeDuplicate(ArrayList arlList) {
        LinkedHashSet<String> set = new LinkedHashSet<String>(arlList);
        arlList.clear();
        arlList = new ArrayList<String>(set);
        System.out.println("");
        return arlList;
    }

    public static ArrayList<String> removeNeighbourDuplicate(ArrayList arlList) {
        ArrayList<String> result = new ArrayList<String>();
        for (Object singleStr : arlList) {
            if (result.size() == 0) {
                result.add(singleStr.toString());
            } else {
                if (result.get(result.size() - 1).equals(singleStr)) {
                } else {
                    result.add(singleStr.toString());
                }
            }
        }


        // LinkedHashSet<String> set = new LinkedHashSet<String>(arlList);
        //arlList.clear();
        //arlList = new ArrayList<String>(set);
        //System.out.println("");
        return result;
    }


    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        StringBuilder paddasca = new StringBuilder("  a x 1234567890 sa 1e");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(paddasca.length());
        paddasca.delete(0, paddasca.length());
        System.out.println(paddasca);
        System.out.println(paddasca.toString());
        System.out.println(paddasca.length());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
        paddasca.append("1");
        System.out.println(paddasca.toString());
        System.out.println(paddasca.length());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(checkStartwithNumInd("13 result"));
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(getNum("13 result"));
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(getFirstNumFromStr("13 result"));

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
        ArrayList<String> xstr = new ArrayList<String>();
        xstr.add("a");
        xstr.add("b");
        xstr.add("a");
        xstr.add("c");
        xstr.add("c");
      //  System.out.println(removeDuplicate(xstr));

       // System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println(removeNeighbourDuplicate(xstr));
    }
}
