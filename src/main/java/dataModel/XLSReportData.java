package dataModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class XLSReportData {
    private static Date date = new Date();
    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String time = format.format(date);

    private static StringBuilder cPath = new StringBuilder().append(System.getProperty("user.dir")).append(File.separator).append("logs").append(File.separator).append(TargetMobileData.getMobileOS()).append(File.separator);
    private static File pfile = new File(cPath.toString());
    private static Workbook XLSXwb = null;
    private static Sheet XLSXsheet = null;
    private String testMethod4XLSX = null;
    private static Row headerRow = null;
    private Row row4TCsingle;
    private Cell cell4TCsingle;
    private ArrayList<String> list4result = new ArrayList<String>();
    private static FileOutputStream theFileOutStream = null;

    private static int globalRowId = 0;
    private static int rownum = 1;

    public static Date getDate() {
        return date;
    }

    public static String getTime() {
        return time;
    }

    public static File getPfile() {
        return pfile;
    }

    public static StringBuilder getcPath() {
        return cPath;
    }

    public static FileOutputStream getTheFileOutStream() {
        try {
            XLSXwb = new XSSFWorkbook();
            XLSXsheet = XLSXwb.createSheet("Test Result");
            final String[] titles = {
                    "TestCase Name", "Time", "input Para", "Except Result Mode", "Reality Result", "Status"};
            headerRow = XLSXsheet.createRow(0);
            Row row4TCsingle;
            Cell cell4TCsingle;
            ArrayList<String> list4result = new ArrayList<String>();
            FileOutputStream out = null;
            globalRowId = 0;
            rownum = 1;

            // set head row
            headerRow.setHeightInPoints(12.75f);
            for (int i = 0; i < titles.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(titles[i]);
            }
            globalRowId = 1;
            String XLSXfile = cPath + File.separator + "Test_Result.xlsx";
            theFileOutStream = new FileOutputStream(XLSXfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return theFileOutStream;
        }

    }

    public static FileOutputStream FlushTCResult2File(FileOutputStream extFileOutStream, ArrayList<String> list4result, int extROWID4TC) {
        try {
            String[] templist4r = null;
            for (int ind = 0; ind < list4result.size(); ind++) {
                String resultL = list4result.get(ind).toString();
                templist4r = resultL.split("#");
                Row row;
                Cell cell;
              //  row = XLSXsheet.createRow(rownum + globalRowId);
                row = XLSXsheet.createRow(rownum + extROWID4TC);
                for (int index = 0; index < templist4r.length; index++) {
                    cell = row.createCell(index);
                    cell.setCellValue(templist4r[index]);
                }
                rownum++;
            }
           // globalRowId = globalRowId + list4result.size();
            XLSXwb.write(extFileOutStream);
            XLSXwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
              return theFileOutStream;
        }
    }
}
