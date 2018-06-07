package utility;

import REXSH.REXAUTO.LocalException.REXException;
import org.openqa.selenium.By;
import utility.readProperity.readProperity;

import java.io.File;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static REXSH.REXAUTO.Component.Driver.ScreenShot.ScreenShot;
import static java.util.Collections.sort;


public class dfdf {
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

        String paddasca= "  a x 1234567890 sa 1e";
        System.out.println( filter4space(paddasca));
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
        String para1= "1234567890";
        para1 = new String(para1.substring(0,3)+"-"+para1.substring(3,6)+"-"+para1.substring(6,para1.length()));
        System.out.println(para1);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
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
        //  StringBuffer result = textContentCheck(new StringBuffer("teststart"));
        // System.out.println(result);
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

        int wide = Integer.parseInt(readProperity.getProValue("screenWide"));
        int length = Integer.parseInt(readProperity.getProValue("screenLength"));

        int start_y = (length / 5) * 4;
        int end_y = start_y - (length / 5) * 2;
        System.out.println("start y : " + start_y);
        System.out.println("end y   :" + end_y);

        System.out.println("~~~~~~~~~~~~~~~~~~");
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name"); //操作系统名称
        String osArch = props.getProperty("os.arch"); //操作系统构架
        System.out.println("name" + osName);
        System.out.println("arch" + osArch);


        String contentFilePath = ("\\PageXML\\And\\").replaceAll("\\\\", File.separator);
        System.out.println("contentFilePath" + contentFilePath);


        System.out.println("~~~~~~~~~~~~~~~~~~");
        String originalValue = "1.2";
        List<String> orgList = new ArrayList<String>();
        for (int ind = 0; ind < originalValue.split("\\.").length; ind++) {
            orgList.add(originalValue.split("\\.")[ind]);
        }
        System.out.println(orgList.size());


        System.out.println("~~~~~~~~~~~~~~~~~~");
        String eleName = "asdas";
        String[] tempPart = eleName.split("_");
        System.out.println(tempPart.length);

        System.out.println("~~~~~~~~~~~~~~~~~~");
        // Pattern pattern = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
        Pattern pattern = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))?$");
        String xdig = "1";
        Matcher matcher = pattern.matcher(xdig);
        System.out.println(matcher.matches());

        System.out.println("~~~~~~~~~~~~~~~~~~");
        String olddig = "102";
        String newDig = finicalCheckAddZero(olddig);
        System.out.println(newDig);


        System.out.println("~~~~~~~~~~~~~~~~~~");
        String row = "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.support.v4.widget.DrawerLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[2]/android.widget.LinearLayout[1]/android.support.v4.view.ViewPager[1]/android.widget.LinearLayout[1]/android.widget.ScrollView[1]/android.widget.LinearLayout[1]/android.widget.ListView[1]/android.widget.LinearLayout[1]/android.widget.RelativeLayout[02]/android.widget.GridView[1]/android.widget.LinearLayout[311]/android.widget.TextView[1]";
        String erx = "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.support.v4.widget.DrawerLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[2]/android.widget.LinearLayout[1]/android.support.v4.view.ViewPager[1]/android.widget.LinearLayout[1]/android.widget.ScrollView[1]/android.widget.LinearLayout[1]/android.widget.ListView[1]/android.widget.LinearLayout[1]/android.widget.RelativeLayout[:REXindex:]/android.widget.GridView[1]/android.widget.LinearLayout[:REXrecord:]/android.widget.TextView[1]";
        System.out.println(erx.indexOf(":REXindex:"));
        int startRex1 = erx.indexOf(":REXindex:");
        System.out.println(row.indexOf("]", startRex1 - 1));
        int endRex1 = row.indexOf("]", startRex1 - 1);
        String dig4REXindex = null;
        if (startRex1 <= endRex1) {
            dig4REXindex = row.substring(startRex1, endRex1);
            System.out.println(dig4REXindex);
        }
        erx = new String(erx.replaceFirst(":REXindex:", dig4REXindex));
        System.out.println(erx.indexOf(":REXrecord:"));
        int startRex2 = erx.indexOf(":REXrecord:");
        System.out.println(row.indexOf("]", startRex2 - 1));
        int endRex2 = row.indexOf("]", startRex2 - 1);
        String dig4REXrecord = null;
        if (startRex2 <= endRex2) {
            dig4REXrecord = row.substring(startRex2, endRex2);
            System.out.println(dig4REXrecord);
        }
        String finalStr = new String(erx.replaceFirst(":REXrecord:", dig4REXrecord));
        System.out.println(finalStr);

        System.out.println("~~~~~~~~~~~~~~~~~~");
        String x = "adasfsadf[AAA]";
        for (int i = 0; i < 9; i++) {
            final By t = By.xpath(x.replaceFirst("AAA", String.valueOf(i)));
            System.out.println(t.toString());
        }

        System.out.println("~~~~~~~~~~~~~~~~~~");

        String a1 = "love23next234csdn3423javaeye";
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(a1);
        System.out.println(m.replaceAll("").trim());
        System.out.println("~~~~~~~~~~~~~~~~~~");

        String a = "love23next234csdn3423javaeye";
        List<String> ss = new ArrayList<String>();
        List<Integer> sint = new ArrayList<Integer>();
        for (String sss : a.replaceAll("[^0-9]", ",").split(",")) {
            if (sss.length() > 0) {
                ss.add(sss);
                sint.add(Integer.parseInt(sss));
            }
        }
        System.out.print(ss);
        System.out.print(sint);
        System.out.println("~~~~~~~~~~~~~~~~~~");
        String a2 = "07/01/2016  06/28/2016   06/27/2016 06/02/2016";
        List<String> ss2 = new ArrayList<String>();
        List<Integer> sint2 = new ArrayList<Integer>();
        List<Integer> nsint2 = new ArrayList<Integer>();
        for (String sss : a2.replaceAll("/", "").replaceAll("[^0-9]", ",").split(",")) {
            if (sss.length() > 0) {
                ss2.add(sss.replaceFirst("0", ""));
                sint2.add(Integer.parseInt(sss));
                nsint2.add(Integer.parseInt(sss) * -1);
            }
        }
        System.out.println(ss2);
        System.out.println(sint2);
        System.out.println(nsint2);
        sort(sint2);
        for (int i = 0; i < sint2.size(); i++)
            System.out.println(sint2.get(i) + "  ");
        sort(nsint2);
        for (int i = 0; i < nsint2.size(); i++)
            System.out.println(nsint2.get(i) + "  ");
        for (int i = 0; i < nsint2.size(); i++)
            System.out.println(nsint2.get(i) * -1 + "  ");
        System.out.println("~~~~~~~~~~~~~~~~~~");

        int[] p1 = {1, 2, 3};
        int[] p2 = {1, 2, 3};
        for (int i = 0; i < p1.length; i++) {
            if (p1[i] == p2[i]) {
                System.out.println("int [] equal");
            } else {
                System.out.println("int [] not equal");
            }
        }

        System.out.println("~~~~~~~~~~~~~~~~~~dup");
        String content4check = "20%_REX_80%";
        String content4input = "80% Travel_:REX:_20% Travel_:REX:_Dallas, Texas_:REX:_Northern NJ, New York Metro";
        ArrayList<String> extraList = new ArrayList<String>();
        ArrayList<String> duplicatedList = new ArrayList<String>();
        ArrayList<String> checkList = new ArrayList<String>();
        boolean flag4del = false;
        boolean flag4match = false;
        if (content4input.startsWith("::"))

        {
            content4input = new String(content4input.replaceFirst("::", ""));
        }

        if (content4input.endsWith("::"))

        {
            content4input = new String(content4input.substring(0, content4input.length() - 2));
        }

        if (content4check.startsWith("::"))

        {
            content4check = new String(content4check.replaceFirst("::", ""));
        }

        if (content4check.endsWith("::"))

        {
            content4check = new String(content4check.substring(0, content4input.length() - 2));
        }

        for (
                String temp
                : content4input.split("::"))

        {
            checkList.add(temp);
        }

        for (
                String temp
                : content4check.split("::"))

        {
            if (temp.contains("_:REX:_")) {
                duplicatedList.add(temp);
            } else {
                extraList.add(temp);
            }
        }

        System.out.println("");
        for (
                int ind = 0;
                ind < checkList.size(); ind++)

        {

            for (int exrind = 0; exrind < extraList.size(); exrind++) {
                if (extraList.get(exrind).equals(checkList.get(ind))) {
                    extraList.remove(exrind);
                    flag4del = true;
                    break;
                }
            }
            if (flag4del == true) {
                checkList.remove(ind);
                ind = 0;
                flag4del = false;
            }
            if (checkList.size() == 0 || extraList.size() == 0) {
                break;
            }
        }

        System.out.println(extraList.size());
        System.out.println(checkList.size());

        duplicatedList =

                removeDuplicate(duplicatedList);

        System.out.println(duplicatedList.size());
        ArrayList<String> fuzzyList = new ArrayList<String>();
        for (
                String temp
                : duplicatedList)

        {
            String[] tList = temp.split("_:REX:_");
            for (String t : tList) {
                fuzzyList.add(t);
            }
        }

        fuzzyList =

                removeDuplicate(fuzzyList);

        for (
                int ind = 0;
                ind < checkList.size(); ind++)

        {
            for (int find = 0; find < fuzzyList.size(); find++) {
                if (fuzzyList.get(find).equals(checkList.get(ind))) {
                    fuzzyList.remove(find);
                    flag4del = true;
                    break;
                }
            }
            if (flag4del == true) {
                checkList.remove(ind);
                ind = 0;
                flag4del = false;
            }
            if (checkList.size() == 0 || fuzzyList.size() == 0) {
                break;
            }
        }

        if (checkList.size() == 1 && fuzzyList.size() == 1)

        {
            if (checkList.get(0).equals(fuzzyList.get(0))) {
                flag4match = true;
            } else {
                flag4match = false;
            }
        }

        System.out.println(fuzzyList.size());
        System.out.println(checkList.size());

        if (extraList.size() != 0 || checkList.size() != 0)

        {
            System.out.println("failed!!!!");
        }

        System.out.println("~~~~~~~~~~~~~~~~~~");
        String content4check2 = "sjif23sf::afgdsg45fafa::fg_:REX:_06/01/2016/_:REX:_06/02/2016_:REX:_05/31/2016/_:REX:_akf::asdfasf";


        ArrayList<String> extraList2 = new ArrayList<String>();
        ArrayList<String> duplicatedList2 = new ArrayList<String>();

        for (String temp : content4check2.split("::")) {
            if (temp.contains("_:REX:_")) {
                duplicatedList2.add(temp);
            } else {
                extraList2.add(temp);
            }
        }
        duplicatedList2 = removeDuplicate(duplicatedList2);
        ArrayList<String> fuzzyList2 = new ArrayList<String>();

        for (String temp : duplicatedList2) {
            String[] tList = temp.split("_:REX:_");
            for (String t : tList) {
                if (t != "") {
                    fuzzyList2.add(t);
                } else {
                }
            }
        }
        //  fuzzyList2 = removeDuplicate(fuzzyList2);
        if (fuzzyList2.size() == 1) {
            flag4match = true;
        } else {
            ArrayList<String> rowStr = new ArrayList<String>();
            ArrayList<Integer> rowDig = new ArrayList<Integer>();
            ArrayList<Integer> pSort = new ArrayList<Integer>();
            ArrayList<Integer> nSort = new ArrayList<Integer>();
            for (String subRow : fuzzyList2) {
                String[] newsubRow = subRow.replaceAll("/", "").replaceAll("[^0-9]", ",").split(",");
                for (String digRow : newsubRow) {
                    if (digRow != "" && digRow.length() > 0) {
                        rowStr.add(digRow.replaceFirst("0", ""));
                        rowDig.add(Integer.parseInt(digRow));
                        pSort.add(Integer.parseInt(digRow));
                        nSort.add(Integer.parseInt(digRow) * -1);
                    }
                }
            }

            sort(pSort);
            sort(nSort);
            ArrayList<Integer> finalNSort = new ArrayList<Integer>();
            for (int i = 0; i < nSort.size(); i++) {
                finalNSort.add(nSort.get(i) * -1);
            }
            if (compareSortList(rowDig, pSort) || compareSortList(rowDig, finalNSort)) {
                flag4match = true;
            } else {
                flag4match = false;
            }
            System.out.println(flag4match);

            System.out.println(rowDig);
            System.out.println(pSort);
            System.out.println(finalNSort);
        }


        System.out.println("~~~~~~~~~~~~~~~~~~");
        HashMap<String, By> hashMap = new HashMap<String, By>();
        hashMap.put("cn", By.id("中国"));
        hashMap.put("us", By.id("米国"));
        hashMap.put("en", By.id("英国"));

        System.out.println(hashMap);
        System.out.println("cn" + hashMap.get("cn"));
        System.out.println(hashMap.containsKey("cn"));
        System.out.println(hashMap.keySet());
        System.out.println(hashMap.isEmpty());

        //hashMap.remove("cn");
        //System.out.println(hashMap.containsKey("cn"));

        //使用Iterator遍历HashMap
        Iterator it = hashMap.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            System.out.println("key:" + key);
            System.out.println("value:" + hashMap.get(key));
        }


        By ttt = hashMap.get("cc");
        if (ttt == null) {
            System.out.println("null");
            System.out.println(ttt);
        } else {
            System.out.println("non-null");

        }
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
      //  Pattern test = Pattern.compile("(^[A-Za-z0-9]+)(\\()(\\d+)(\\))$");
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