package utility;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhuhr on 2016/10/10.
 */
public class id_name_phone_Generator {


    public static void main(String[] s) throws Exception {
        try {
            String inFileName = "F:/ROWDATA/rLocation.txt";   // for location original id
            String outFileName = "F:/NEWDATA/newLocation.txt";  // for location id
          //  String targetFile = "F:/NEWDATA/newID.txt";
            String targetFile = "F:/NEWDATA/newPhnoe_ID.txt";
            rowLocationFileHandle(inFileName, outFileName);
            String dataStr4ID = dateHandle();
            //caculateANDsave(dataStr4ID, outFileName, targetFile, false, true, false, 20000);
            caculateANDsave(dataStr4ID, outFileName, targetFile, true, true, false, 20000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void caculateANDsave(String data, String filepath, String file4Record, boolean flag4phone, boolean flag4id, boolean flag4user, int range) throws Exception {
        if (flag4id == true && flag4phone == false && flag4user == false) {  // only id
            ArrayList<StringBuilder> newIDList = idNum(new StringBuilder(data), filepath, range);
            System.out.println(newIDList.size() + " : is the final size for ID");
            String targetfilePath = new String(file4Record); //  "F:/NEWDATA/newID.txt";
            writeFileWithList(targetfilePath, newIDList);
        } else if (flag4id == true && flag4phone == true && flag4user == false) {
            ArrayList<StringBuilder> newIDList = idNum(new StringBuilder(data), filepath, range);
            System.out.println(newIDList.size() + " : is the final size for ID");

            ArrayList<StringBuilder> newPhoneList = phoneNum(new StringBuilder(data), filepath, range);
            System.out.println(newIDList.size() + " : is the final size for Phone");
            ArrayList<StringBuilder> finalList = new ArrayList<StringBuilder>();
            if (newIDList.size() == newPhoneList.size()) {
                for (int ind = 0; ind < newIDList.size(); ind++) {
                    finalList.add(new StringBuilder(newPhoneList.get(ind) + "," + newIDList.get(ind)));
                }
            }
            String targetfilePath = new String(file4Record); //  "F:/NEWDATA/newID.txt";
            writeFileWithList(targetfilePath, finalList);

        }


    }

    public static ArrayList<StringBuilder> phoneNum(StringBuilder data, String file, int range) throws Exception {
        ArrayList<StringBuilder> result = new ArrayList<StringBuilder>();
        StringBuilder localData =new StringBuilder( data);
        try {
            String filePath = new String(file); //  "F:/NEWDATA/newLocation.txt";
            System.out.println(filePath);
            File tFile = new File(filePath);

            if (!tFile.exists()) {  // 不存在返回 false
                //    return flag;
            } else {
                // 判断是否为文件
                if (tFile.isFile()) {  // 为文件时调用删除文件方法
                    //      deleteFile(filePath);
                } else {  // 为目录时调用删除目录方法
                    //return deleteDirectory(sPath);
                }
            }
            StringBuilder birthSeq = new StringBuilder("");
            birthSeq.delete(0, birthSeq.length());

            for (int ind = 1; ind <= range; ind++) {
                int ind4last5num = ind;
                if (String.valueOf(ind4last5num).length() == 5) {
                    birthSeq.append(ind4last5num);
                } else if (String.valueOf(ind4last5num).length() == 4) {
                    birthSeq.append("0" + ind4last5num);
                } else if (String.valueOf(ind4last5num).length() == 3) {
                    birthSeq.append("00" + ind4last5num);
                } else if (String.valueOf(ind4last5num).length() == 2) {
                    birthSeq.append("000" + ind4last5num);
                } else if (String.valueOf(ind4last5num).length() == 1) {
                    birthSeq.append("0000" + ind4last5num);
                }else {
                    throw new Exception("wrong range for phone NUM " + ind4last5num);
                }
                result.add(new StringBuilder(localData.append(birthSeq)));
                //  System.out.println(ind4last3num);
                if (ind4last5num == 99999) {
                    localData = new StringBuilder(data);
                    birthSeq.delete(0, birthSeq.length());
                    ind4last5num = 1;
                    localData=new StringBuilder(Integer.parseInt(localData.toString())+100000);
                } else {
                    localData = new StringBuilder(data);
                    birthSeq.delete(0, birthSeq.length());
                    ind4last5num++;
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }


    public static ArrayList<StringBuilder> idNum(StringBuilder data, String file, int range) throws Exception {
        ArrayList<StringBuilder> result = new ArrayList<StringBuilder>();
        try {

            String filePath = new String(file); //  "F:/NEWDATA/newLocation.txt";
            System.out.println(filePath);
            File tFile = new File(filePath);

            if (!tFile.exists()) {  // 不存在返回 false
                //    return flag;
            } else {
                // 判断是否为文件
                if (tFile.isFile()) {  // 为文件时调用删除文件方法
                    //      deleteFile(filePath);
                } else {  // 为目录时调用删除目录方法
                    //return deleteDirectory(sPath);
                }
            }
            //  createFileUtil.createFile(filePath);
            ArrayList<String> list4result = new ArrayList<String>();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        new FileInputStream(filePath)));

                for (String line = br.readLine(); line != null; line = br.readLine()) {
                    line = new String(line.replaceAll(" +", " "));
                    line = new String(line.split(" ")[0]);

                    list4result.add(line);
                }
                br.close();
                System.out.println("So many rows:" + list4result.size());

            } catch (Exception e) {
                e.printStackTrace();
            }
            StringBuilder birthSeq = new StringBuilder("");
            birthSeq.delete(0, birthSeq.length());
            int targetLength = range;

            for (int index = 1, index4pre = 0, ind4last3num = 1; index <= targetLength; index++) {
                if (String.valueOf(ind4last3num).length() == 3) {
                    birthSeq.append(ind4last3num);
                } else if (String.valueOf(ind4last3num).length() == 2) {
                    birthSeq.append("0" + ind4last3num);
                } else if (String.valueOf(ind4last3num).length() == 1) {
                    birthSeq.append("00" + ind4last3num);
                } else {
                    throw new Exception("wrong range for birth NUM " + ind4last3num);
                }

                String tempStr17ID = new String(list4result.get(index4pre) + data + birthSeq);
                System.out.println("New ID with 17 digit : " + tempStr17ID);
                // int num = 31022719830818005;
                //  int temp17ID = Integer.getInteger(tempStr17ID);

                String[] rowCheck = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                String[] rowWeight = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};
                String[] list4tempStr = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
                ArrayList<String> checkNUM = new ArrayList<String>();
                for (String temp : rowCheck) {
                    checkNUM.add(temp);
                }
                ArrayList<String> weightNUM = new ArrayList<String>();
                for (String ntemp : rowWeight) {
                    weightNUM.add(ntemp);
                }
                ArrayList<String> tempNUM = new ArrayList<String>();

                int temp4getcheckNUM = 0;
                if (tempStr17ID.length() == 17) {
                    for (int ind = 0; ind < tempStr17ID.length(); ind++) {
                        //   System.out.println(tempStr17ID.substring(ind,ind+1) +  " : s");
                        list4tempStr[ind] = (tempStr17ID.substring(ind, ind + 1));
                    }
                    for (String ntemp : list4tempStr) {
                        tempNUM.add(ntemp);
                    }

                    for (int ind = 0; ind < tempStr17ID.length(); ind++) {
                        int x = Integer.parseInt(weightNUM.get(ind).toString());
                        //      System.out.println(weightNUM.get(ind) + " : w");
                        int y = Integer.parseInt(tempNUM.get(ind).toString());
                        //   System.out.println(tempNUM.get(ind) + " : d");

                        //      System.out.println( list4tempStr[ind].toString() + " d");
                        //    System.out.println(Integer.getInteger(tempStr17ID.substring(ind,1)) +  " : s");
                        temp4getcheckNUM = temp4getcheckNUM + (x * y);
                    }
                    // System.out.println(temp4getcheckNUM % 11);
                    //  System.out.println(checkNUM.get(temp4getcheckNUM % 11));
                    result.add(new StringBuilder(tempStr17ID + checkNUM.get(temp4getcheckNUM % 11)));
                    //  System.out.println(ind4last3num);
                    if (ind4last3num == 999) {
                        birthSeq.delete(0, birthSeq.length());
                        ind4last3num = 1;
                        index4pre++;
                    } else {
                        birthSeq.delete(0, birthSeq.length());
                        ind4last3num++;
                    }

                } else {
                    throw new Exception("Wrong format for temp 17 digital");
                }
                // result.append(checkNUM);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }


    public static StringBuilder usrName() throws Exception {
        StringBuilder result = new StringBuilder("");
        try {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static void method1(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(conent);
            out.write("\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param sPath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean DeleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                //   return deleteDirectory(sPath);
                return true;
            }
        }
    }

    public static void writeFileWithList(String file, ArrayList<StringBuilder> content) {
        BufferedWriter out = null;
        try {
            File tFile = new File(file);

            if (!tFile.exists()) {  // 不存在返回 false
                //    return flag;
            } else {
                // 判断是否为文件
                if (tFile.isFile()) {  // 为文件时调用删除文件方法
                    deleteFile(file);
                } else {  // 为目录时调用删除目录方法
                    //return deleteDirectory(sPath);
                }
            }
            createFileUtil.createFile(file);

            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            for (StringBuilder current : content) {
                out.write(current.toString());

                out.write("\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String dateHandle() throws Exception {
        String result = null;
        try {
            Properties prop = new Properties();// 属性集合对象
            //  JsonObject object = (JsonObject) parser.parse(new FileReader(System.getProperty("user.dir") + "/src/main/java/utility/test.json"));
            //FileInputStream fis = new FileInputStream(("\\Users\\appledev131\\Documents\\csvloader\\src\\main\\java\\com\\csv\\config_template.properties").replaceAll("\\\\", File.separator));// 属性文件输入流
            FileInputStream fis = new FileInputStream("F:/ROWDATA/" + "config_temp.properties");
            //todo change for windows
            prop.load(fis);// 将属性文件流装载到Properties对象中
            fis.close();// 关闭流


            TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
            Calendar cl = Calendar.getInstance(tz, Locale.US);
            String defaultDay = String.valueOf(cl.get(Calendar.DAY_OF_MONTH));
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String str1 = dateFormat.format(now);
            System.out.println("Today is : " + str1);
            cl.add(Calendar.YEAR, -30);
            Date the30yago = cl.getTime();
            String the30 = dateFormat.format(the30yago);
            result = new String(the30);
            System.out.println("30 years of Today is : " + the30);
            //  int lastDayInt = extDateHandle.getLastDayOfMonth(new Date(), "Asia/Shanghai");
/*
        if (Integer.parseInt(defaultDay) > 2 && Integer.parseInt(defaultDay) != lastDayInt) {
            int yesterdayInt = Integer.parseInt(defaultDay) - 2;
        } else {
            cl.add(Calendar.MONTH, -1);
            int lastDay = cl.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
    if (Integer.parseInt(defaultDay) != lastDayInt) {
    } else {
    }*/
            prop.setProperty("today", str1);
            prop.setProperty("the30yearagotoday", the30);
    /*prop.setProperty("tomorrow", tomorrowString);
    prop.setProperty("normal_startdate", monkeydayString);
    prop.setProperty("normal_enddate", yesterdayString);
    prop.setProperty("abnormal_startdate", yesterdayString);
    prop.setProperty("abnormal_enddate", monkeydayString);
*/
            FileOutputStream fos = new FileOutputStream("F:/NEWDATA/" + "config_temp.properties");
            prop.store(fos, "Copyright (c) Paul ZHU's Studio");

            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static void rowLocationFileHandle(String inFile, String outFile) throws Exception {
        StringBuilder cPath = new StringBuilder().append(System.getProperty("user.dir")).append(File.separator).append("logs");
        XSSFWorkbook XLSXwb;
        System.out.println("*******beforeMethod********");
        String nameOfMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        XLSXwb = new XSSFWorkbook();
        String testMethod4XLSX = null;

        String filePath = new String(inFile);  //  "F:/ROWDATA/ROWLocation.txt";
        String targetfilePath = new String(outFile);  // "F:/NEWDATA/newLocation.txt";
        File tFile = new File(targetfilePath);

        if (!tFile.exists()) {  // 不存在返回 false
            //    return flag;
        } else {
            // 判断是否为文件
            if (tFile.isFile()) {  // 为文件时调用删除文件方法
                deleteFile(targetfilePath);
            } else {  // 为目录时调用删除目录方法
                //return deleteDirectory(sPath);
            }
        }
        createFileUtil.createFile(targetfilePath);

        System.out.println(filePath);
        ArrayList<String> list4result = new ArrayList<String>();
        //  list4result.add("3239M  137M 4469M  139M");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filePath)));

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                //     System.out.println(line);
                line = new String(line.replaceAll(" +", " "));
                line = new String(line.split(" ")[0]);

                list4result.add(line);
            }
            br.close();
            System.out.println("So many rows:" + list4result.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        final String[] titles = {
                "used", "buff", "cach", "free"};
        FileOutputStream out = null;
        int globalRowId = 0;
        int rownum = 1;

        // set head row
        globalRowId = 1;
        try {
            testMethod4XLSX = Thread.currentThread().getStackTrace()[1].getMethodName();
            System.out.println("*******aftermethod********");
            String[] templist4r = null;
            ArrayList<StringBuilder> list4new = new ArrayList<StringBuilder>();
            try {
                for (int ind = 0; ind < list4result.size(); ind++) {
                    String resultL = list4result.get(ind).toString();
                    if (!resultL.endsWith("00")) {
                        //method1(targetfilePath,resultL);
                        list4new.add(new StringBuilder(resultL));
                    }


                }
                writeFileWithList(targetfilePath, list4new);
                globalRowId = globalRowId + list4result.size();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
        }
    }


}
