package utility;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.*;

/**
 * Created by appledev131 on 4/22/16.
 */
public class dfdf2 {
    public static Object[][] profileChooseGroup() {
        return new Object[][]{
                //     {"648412480@qq.com", "Pwcwelcome1", "addEducation"},
                //   {"648412480@qq.com", "Pwcwelcome1", "addCertification"},
                //    {"648412480@qq.com", "Pwcwelcome1", "addLicense"},
                //     {"648412480@qq.com", "Pwcwelcome1", "addLanguage"},
                //    {"648412480@qq.com", "Pwcwelcome1", "addPatent"},
                //       {"648412480@qq.com", "Pwcwelcome1", "addAwardOrArch"},
                //   {"648412480@qq.com", "Pwcwelcome1", "addReference"},
                //  {"heliupwc@126.com", "Pwcwelcome1", "1234567890::switch2Profile::addSkills", "regular"}, // last para can be regular / "Error;NO RESULT" "err" "part1;part2 split the except err info"
                {"648412480@qq.com", "Pwcwelcome1", "name::switch2Profile::addSkills", "regular"}, // last para can be regular / "Error;NO RESULT" "err" "part1;part2 split the except err info"

                //     {"648412480@qq.com", "Pwcwelcome1", "eng", "res"}
                // sylar.wan@hotmail.com"
                // rextest_3749@sina.com
                // rextest3749@sina.com
                // 648412480@qq.com
                // wanchen2mp@163.com
        };
    }

    private static final String[] titles = {
            "TestCase Name", "Time", "input Para", "Except Result Mode", "Reality Result", "Status"};

    //sample data to fill the sheet.
    private static final String[][] data = {
            {"", "", "", "", "", ""},
            {"邓超", "跑了", "跑了", "没跑", "跑了", "跑了"},
            {"王祖蓝", "没跑", "没跑", "没跑", "跑了", "跑了"},
            {"王宝强", "跑了", "跑了", "跑了", "跑了", "跑了"},
            {"郑恺", "跑了", "跑了", "跑了", "跑了", "跑了"}
    };


    public static void main(String[] args) throws Exception {


        System.out.println(profileChooseGroup().length);
        ArrayList[][] al = new ArrayList[profileChooseGroup().length][6];
        List<String> x = new ArrayList<String>(Arrays.asList("xyz", "abc"));
        ArrayList<String> list4result = new ArrayList<String>();
        list4result.add("1" + ":" + "2" + ":" + "3" + ":" + "4" + ":" + "5" + ":" + "6");
        list4result.add("1ad" + ":" + "2asd" + ":" + "3" + ":" + "4" + ":" + "5" + ":" + "6");
        //   al[0]= new ArrayList<String>(Arrays.asList("ab","cd","ef","","",""));
        Iterator<String> t = list4result.iterator();
        // String[] list4resultL = list4result[0];
        // System.out.println(list4resultL.length);
        String resultL = list4result.get(0).toString();
        String[] list4resultL = resultL.split(":");
        Workbook wb;

        if (args.length > 0 && args[0].equals("-xls")) wb = new HSSFWorkbook();
        else wb = new XSSFWorkbook();

        Sheet sheet = wb.createSheet("Runnin ");

        Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(12.75f);
        for (int i = 0; i < titles.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(titles[i]);
        }

        Row row;
        Cell cell;
        int rownum = 1;
        row = sheet.createRow(rownum);
        for (int ind = 0; ind < list4resultL.length; ind++) {

            cell = row.createCell(ind);
            cell.setCellValue(list4resultL[ind]);
        }

        resultL = list4result.get(1).toString();
        list4resultL = resultL.split(":");
        rownum = 2;
        row = sheet.createRow(rownum);
        for (int ind = 0; ind < list4resultL.length; ind++) {

            cell = row.createCell(ind);
            cell.setCellValue(list4resultL[ind]);
        }
        /*

        for (int i = 0; i < data.length; i++, rownum++) {
            row = sheet.createRow(rownum);
            if(data[i] == null) continue;

            for (int j = 0; j < data[i].length; j++) {
                cell = row.createCell(j);
                cell.setCellValue(data[i][j]);
            }
        }*/


        // Write the output to a file
        String file = "/Users/appledev131/Documents/REXAUTO4/logs/RunResult.xls";
        if (wb instanceof XSSFWorkbook) file += "x";
        FileOutputStream out = new FileOutputStream(file);
        wb.write(out);
        out.close();
    }


}
