package NDT;

import Back.Pasport.TPTC032;
import FrontOBRE.MyFrame;
import NDT.ReportObjects.ReportResponsible;
import NDT.ReportObjects.Reports;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ReportList implements TPTC032 {

    public void checkWbLists() {

        ExecutorService pool = null;
        try {
            pool = Executors.newFixedThreadPool(10);
            List<String> listF = new ArrayList<>();
            MyFrame frame = new MyFrame();
            frame.setInputFilePath();
            String testFolder = frame.getInputFilePath();

            this.getFileList(testFolder,listF);

            Map<String, Reports> resultMap = new ConcurrentHashMap<>();
            List<NtdTreadTask> taskList = new ArrayList<>();
            for (String s : listF) {
                taskList.add(new NtdTreadTask(s,new File(s).getName(),resultMap));
            }

            pool.invokeAll(taskList);
            this.resultTable(resultMap);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (pool != null) {
                pool.shutdown();
            }
        }

    }

    private void resultTable(  Map<String, Reports>  reportsList) throws IOException {
        try(Workbook wb = new XSSFWorkbook();
        OutputStream out = new FileOutputStream(new File(System.getProperty("user.home")+File.separator+"Desktop"+
                File.separator+"Yo.xlsx"))){
            Sheet sheet = wb.createSheet("Yoba");
            AtomicInteger rowCount = new AtomicInteger();
            reportsList.forEach((key, reports) -> {
                Row row = sheet.createRow(rowCount.get());
                int delta = 9;
                row.createCell(0).setCellValue(reports.getReportID());
                for (int i = 0; i < reports.getAllReports().size() + 1; i++) {
                    ReportResponsible e = reports.getAllReports().get(i);
                    row.createCell(i * delta + 1).setCellValue(e.getNtdType());
                    row.createCell(i * delta + 2).setCellValue(e.getTestedBy());
                    row.createCell(i * delta + 3).setCellValue(e.getConclusionIssued());
                    row.createCell(i * delta + 4).setCellValue(e.getHeadOfLaboratory());
                    row.createCell(i * delta + 5).setCellValue(e.getConclusionAccept());
                    row.createCell(i * delta + 6).setCellValue(e.getLine());
                    row.createCell(i * delta + 7).setCellValue(e.getZone());
                    row.createCell(i * delta + 8).setCellValue(e.getReportNumber());
                }
                rowCount.getAndIncrement();
            });
            wb.write(out);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
