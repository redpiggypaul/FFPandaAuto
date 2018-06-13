package utility;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
/**
 * Created by zhuhr on 2016/9/22.
 */


/**
 *
 * 功能描述：创建TXT文件并进行读、写、修改操作
 *
 * @author <a href="mailto:zhanghhui@126.com">KenZhang</a>
 * @version 1.0
 * Creation date: 2007-12-18 - 下午06:48:45
 */
public class TXTAddPresent {

    public static void main(String[] s) throws Exception {
        StringBuilder cPath = new StringBuilder().append(System.getProperty("user.dir")).append(File.separator).append("logs");
        XSSFWorkbook XLSXwb;
        System.out.println("*******beforeMethod********");
        String nameOfMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        XLSXwb = new XSSFWorkbook();
        String testMethod4XLSX = null;

        String filePath = "F:/needPer.txt";
        String targetfilePath = "F:/afterPer.txt";
        System.out.println(filePath);
        ArrayList<String> list4result = new ArrayList<String>();
        //  list4result.add("3239M  137M 4469M  139M");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filePath)));

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                //     System.out.println(line);
                list4result.add(line);
            }
            br.close();
            System.out.println("So many rows:" + list4result.size());

        }catch (Exception e)
        {e.printStackTrace();}

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
            try {
                for (int ind = 0; ind < list4result.size(); ind++) {
                    String resultL = list4result.get(ind).toString();
                    if(!resultL.endsWith("%"))
                    {
                        method1(targetfilePath,resultL+"%");
                    }

                }
                globalRowId = globalRowId + list4result.size();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
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
}
