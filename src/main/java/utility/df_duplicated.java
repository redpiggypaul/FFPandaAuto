package utility;

import LocalException.FFPandaException;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Component.Driver.ScreenShot.ScreenShot;


public class df_duplicated {
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

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String appInputFeedBackMode = "duplicatedMatch";
        String content4input = "20%_REX_80%";
        String content4check = "80% Travel_:REX:_20% Travel_:REX:_Dallas, Texas_:REX:_Northern NJ, New York Metro";
        StringBuffer result = new StringBuffer();
        boolean flag4match = false;
        ArrayList<String> extraList = new ArrayList<String>();
        ArrayList<String> duplicatedList = new ArrayList<String>();
        ArrayList<String> checkList = new ArrayList<String>();

        //     for (String temp : content4input.split("::")) {
        //        checkList.add(temp);
        //    }
        for (String temp : content4check.split("::")) {
            if (temp.contains("_:REX:_")) {
                duplicatedList.add(temp);
            } else {
                extraList.add(temp);
            }
        }
        // extra match first, delete the matched item from list, should follow the seque in the input list
        if (content4input.equals("")) {
            flag4match = true;
        } else {
            if (!content4input.contains("_REX_") && content4input.contains("::")) {
                for (String temp : content4input.split("::")) {
                    checkList.add(temp);
                }
            } else if (content4input.contains("_REX_") && !content4input.contains("::")) {
                for (String temp : content4input.split("_REX_")) {
                    checkList.add(temp);
                }
            } else if (content4input.contains("::") && content4input.contains("_REX_")) {
                ArrayList<String> tcheckList = new ArrayList<String>();
                for (String temp : content4input.split("::")) {
                    tcheckList.add(temp);
                }
                for (String temp : tcheckList) {
                    if (temp.contains("_REX_")) {
                        for (String subTemp : temp.split("_REX_")) {
                            checkList.add(subTemp);
                        }
                    } else {
                        checkList.add(temp);
                    }
                }
            }
            for (String temp : content4check.split("::")) {
                if (temp.contains("_:REX:_")) {
                    duplicatedList.add(temp);
                } else {
                    extraList.add(temp);
                }
            }
            boolean flag4del = false;
            boolean flag4restart = false;
            for (int ind = 0; ind < checkList.size(); ind++) {
                if (flag4restart == true) {
                    ind = 0;
                    flag4restart = false;
                }
                for (int exrind = 0; exrind < extraList.size(); exrind++) {
                    if (extraList.get(exrind).equals(checkList.get(ind))) {
                        extraList.remove(exrind);
                        flag4del = true;
                        break;
                    }
                }
                if (flag4del == true) {
                    checkList.remove(ind);
                    flag4restart = true;
                    flag4del = false;
                }
                if (checkList.size() == 0 || extraList.size() == 0) {
                    break;
                }
            }
            if (extraList.size() == 0) {
                flag4match = true;
            } else {
                result.append("Extra compare 4 duplicatedMatch failed : " + extraList.get(0));
            }
            duplicatedList = removeDuplicate(duplicatedList);
            ArrayList<String> fuzzyList = new ArrayList<String>();
            for (String temp : duplicatedList) {
                String[] tList = temp.split("_:REX:_");
                for (String t : tList) {
                    fuzzyList.add(t);
                }
            }
            // after extra list match, try the rest part from the remain input list, don't delete the dup list , only try next item in the remain input list
            fuzzyList = removeDuplicate(fuzzyList);
            for (int ind = 0; ind < checkList.size(); ) {
                for (int find = 0; find < fuzzyList.size(); ) {
                    if (fuzzyList.get(find).equals(checkList.get(ind)) || fuzzyList.get(find).contains(checkList.get(ind))) {
                        fuzzyList.remove(find);
                        flag4del = true;
                        find = 0;
                        break;
                    }
                    find++;
                }
                if (flag4del == true) {
                    checkList.remove(ind);
                    ind = 0;
                    flag4del = false;
                    ind = 0;
                } else {
                    ind++;
                }
                if (checkList.size() == 0 || fuzzyList.size() == 0) {
                    break;
                }

            }
            if (checkList.size() == 1 && fuzzyList.size() == 1) {
                if (checkList.get(0).equals(fuzzyList.get(0))) {
                    flag4match = true;
                } else {
                    flag4match = false;
                }
            } else if (checkList.size() == 0) {
                flag4match = true;
            } else {
                result.append("checkList is not empty, compare 4 duplicatedMatch failed : " + checkList.get(0));
            }
        }



        if (flag4match == true) {
            if (appInputFeedBackMode.equalsIgnoreCase("sortMatch") && flag4match == true) {
                result.append("teststartk");
            } else {
                result.append("inRes");
            }
        } else {
            //    result.append("Element content failed to match input");

            if (flag4match != true || !result.toString().equals("teststart")) {
          /*  if (appInputFeedBackMode.equalsIgnoreCase("sortMatch") && flag4match == true) {
                result.append("teststartk");
            } else */
                if ((content4check != null) && (content4check != "")) {
                    result.append(content4check);
                } else if ((content4input != null) && (content4input != "")) {
                    result.append("check content line missing in operation XML");
                } else {
                    result.append("inRR=e=s");
                }
            }
        }
        System.out.println("result in Sub end : " + result);
    }

    public static boolean compareSortList(ArrayList row, ArrayList target) {
        boolean result = false;
        if (row.size() != target.size()) {
            result = false;
        } else {
            for (int i = 0; i < row.size(); i++) {
                if (row.get(i).equals(target.get(i))) {
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }


    public static ArrayList<String> removeDuplicate(ArrayList arlList) {
        LinkedHashSet<String> set = new LinkedHashSet<String>(arlList);
        //Constructing listWithoutDuplicateElements using set
        arlList.clear();
        arlList = new ArrayList<String>(set);
        System.out.println("+++++++" + arlList.size());
        return arlList;
    }

    private static String finicalCheckDeleteZeroAndSymbol(String extPara) {
        String result = null;
        String temp = null;
        if (extPara.startsWith("$")) {
            temp = new String(extPara.substring(1, extPara.length()));
        } else {
            temp = extPara;
        }
        System.out.println(temp);
        Pattern pattern2dig = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){2})?$");
        Pattern pattern1dig = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1})?$");
        Pattern patternEntry = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))?$");
        Pattern patternDot = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.)?$");
        //  Matcher matcher = pattern.matcher("10.11");
        Matcher matcher2dig = pattern2dig.matcher(temp);
        Matcher matcher1dig = pattern1dig.matcher(temp);
        Matcher matcherEntry = patternEntry.matcher(temp);
        Matcher matcherEntryDot = patternDot.matcher(temp);
        //  System.out.println(matcher.matches());
        if (matcherEntry.matches()) {
            result = temp;
        } else if (matcherEntryDot.matches()) {
            result = new String(temp.substring(0, temp.length() - 1));
        } else if (matcher2dig.matches()) {
            if (temp.endsWith(".00")) {
                //  result = new String (temp.replace(".00",""));
                result = new String(temp.substring(0, temp.length() - 3));
            } else if (temp.endsWith("0")) {
                result = new String(temp.substring(0, temp.length() - 1));
            } else {
                result = temp;
            }
        } else if (matcher1dig.matches()) {
            if (temp.endsWith("0")) {
                result = new String(temp.substring(0, temp.length() - 2));
            } else {
                result = temp;
            }
        }
        return result; //TODO
    }


    private static String finicalCheckAddZero(String extPara) {
        String localPara = new String(extPara);
        String result = null;
        Pattern pattern2dig = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){2})?$");
        Pattern pattern1dig = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1})?$");
        Pattern patternEntry = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))?$");
        Pattern patternEntryDot = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.)?$");
        //  Matcher matcher = pattern.matcher("10.11");
        Matcher matcher2dig = pattern2dig.matcher(localPara);
        Matcher matcher1dig = pattern1dig.matcher(localPara);
        Matcher matcherEntry = patternEntry.matcher(localPara);
        Matcher matcherEntryDot = patternEntryDot.matcher(localPara);
        //  System.out.println(matcher.matches());
        if (matcherEntry.matches()) {
            result = new String(localPara + ".00");
        } else if (matcherEntryDot.matches()) {
            result = new String(localPara + "00");
        } else if (matcher2dig.matches()) {
            result = localPara;
        } else if (matcher1dig.matches()) {
            result = new String(localPara + "0");
        }

        return result; //TODO
    }
}

/*


new String[]{"login", "switch2Profile", "clearProfile", "addReferenceOnly_Name", "reference_profileSaveback2Profile", "addReferenceOnly_Name_Check"}

addReferenceOnly_Company
new String[]{"login", "switch2Profile", "clearProfile", "addReferenceOnly_Company", "reference_profileSaveback2Profile", "addReferenceOnly_Company_Check"}

addReferenceOnly_Position
new String[]{"login", "switch2Profile", "clearProfile", "addReferenceOnly_Position", "reference_profileSaveback2Profile", "addReferenceOnly_Position_Check"}

 addReferenceOnly_Email
 new String[]{"login", "switch2Profile", "clearProfile", "addReferenceOnly_Email", "reference_profileSaveback2Profile", "addReferenceOnly_Email_Check"}

 addReferenceOnly_Phone
 new String[]{"login", "switch2Profile", "clearProfile", "addReferenceOnly_Phone", "reference_profileSaveback2Profile", "addReferenceOnly_Phone_Check"}

 addReferenceFocus_Name
 new String[]{"login", "switch2Profile", "clearProfile", "addReferenceFocus_Name", "reference_profileSaveback2Profile", "addReferenceFocus_Name_Check"}

 addReferenceFocus_Company
 new String[]{"login", "switch2Profile", "clearProfile", "addReferenceFocus_Company", "reference_profileSaveback2Profile", "addReferenceFocus_Company_Check"},

 addReferenceFocus_Position
 new String[]{"login", "switch2Profile", "clearProfile", "addReferenceFocus_Position", "reference_profileSaveback2Profile", "addReferenceFocus_Position_Check"}

 addReferenceFocus_Email
 new String[]{"login", "switch2Profile", "clearProfile", "addReferenceFocus_Email", "reference_profileSaveback2Profile", "addReferenceFocus_Email_Check"}

 addReferenceFocus_Phone
new String[]{"login", "switch2Profile", "clearProfile", "addReferenceFocus_Phone", "reference_profileSaveback2Profile", "addReferenceFocus_Phone_Check"}


 addReference_CheckAll_Focus_Name
 new String[]{"login", "switch2Profile", "clearProfile", "addReference_CheckAll_Focus_Name", "reference_profileSaveback2Profile", "addReference_CheckAll_Focus_Name_Check"}

 addReference_CheckAll_Focus_Company

 new String[]{"login", "switch2Profile", "clearProfile", "addReference_CheckAll_Focus_Company", "reference_profileSaveback2Profile", "addReference_CheckAll_Focus_Company_Check"}

 addReference_CheckAll_Focus_Position
 new String[]{"login", "switch2Profile", "clearProfile", "addReference_CheckAll_Focus_Position", "reference_profileSaveback2Profile", "addReference_CheckAll_Focus_Position_Check"}


 addReference_CheckAll_Focus_Mail
 new String[]{"login", "switch2Profile", "clearProfile", "addReference_CheckAll_Focus_Mail", "reference_profileSaveback2Profile", "addReference_CheckAll_Focus_Mail_Check"}

 addReference_CheckAll_Focus_Phone
 new String[]{"login", "switch2Profile", "clearProfile", "addReference_CheckAll_Focus_Phone", "reference_profileSaveback2Profile", "addReference_CheckAll_Focus_Phone_Check"}
 */