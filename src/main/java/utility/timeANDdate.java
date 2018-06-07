package utility;

import REXSH.REXAUTO.LocalException.REXException;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.sort;


public class timeANDdate {
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


    public static boolean assertTestResult(String testResult, String exceptedResult) throws REXException {
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
            throw new REXException("BaseAction : textContentCheck : " + e.getMessage());
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


// TODO Auto-generated method stubD
        boolean r = assertTestResult("sadas:: aagood sd sadas safe e ; REXdd sfas ", "  safe REX good ");
        System.out.println(r);
        TimeZone tz = TimeZone.getTimeZone("America/Chicago");

        DateFormat theMonth = new SimpleDateFormat("MMM", Locale.ENGLISH);
        theMonth.setTimeZone(tz);
        DateFormat theYear = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        theYear.setTimeZone(tz);
        System.out.println("new m :" + theMonth.format(new Date()));
        System.out.println("new y :" + theYear.format(new Date()));

        DateFormat df = new SimpleDateFormat("MMM", Locale.ENGLISH);
        df.setTimeZone(tz);
        System.out.println(df.format(new Date()));

        DateFormat year = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        year.setTimeZone(tz);
        System.out.println(year.format(new Date()));

        DateFormat day = new SimpleDateFormat("dd", Locale.ENGLISH);
        day.setTimeZone(tz);
        System.out.println(day.format(new Date()));


        Calendar cl = Calendar.getInstance(tz, Locale.US);
        System.out.println(cl.get(Calendar.DAY_OF_MONTH));

        DateFormat xxx = DateFormat.getDateTimeInstance();

        System.out.println(xxx.format(cl.getTime()));
        System.out.println("~~~~~~~~~~~~~~~~~~");
        cl.add(Calendar.MONTH, +2);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        String lastMonthName = sdf.format(cl.getTime());
        System.out.println(lastMonthName);
        System.out.println("~~~~~~~~~~~~~~~~~~");
        sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        Date curDate = java.sql.Date.valueOf("2016-5-24");
        calendar.setTime(curDate);
        //取得现在时间
        System.out.println(sdf.format(curDate));
        //取得上一个时间
        calendar.set(Calendar.MONDAY, calendar.get(Calendar.MONDAY) - 1);
        //取得上一个月的下一天
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        System.out.println(sdf.format(calendar.getTime()));

        calendar.roll((Calendar.DAY_OF_MONTH) - 1, -1);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));

        System.out.println("~~~~~~~~~~~~~~~~~~");

        Calendar newcalendar = Calendar.getInstance();
        int month = newcalendar.get(Calendar.MONTH);
        newcalendar.set(Calendar.MONTH, month - 1);
        newcalendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date strDateTo = newcalendar.getTime();
        DateFormat newFy = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        System.out.println(newFy.format(newcalendar.getTime()));


        System.out.println("~~~~~~~~~~~~~~~~~~");
        long t1=System.currentTimeMillis();
        String t = String.valueOf(t1);

        String x = t.substring(t.length()-4,t.length());
        System.out.println(t1);
        System.out.println(t1+1);
        System.out.println(x);
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