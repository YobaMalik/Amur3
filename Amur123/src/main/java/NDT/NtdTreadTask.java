package NDT;

import NDT.ReportObjects.Reports;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;

public class NtdTreadTask implements Callable<Void> {

    private String fileName;
    private String in;
    private Map<String, Reports> resultMap;

    public NtdTreadTask(String in, String fileName, Map<String, Reports> resultMap){
        this.in = in;
        this.fileName = fileName;
        this.resultMap = resultMap;
    }

    private void analyizeWorkBooks()  {
        try (Workbook wb = new XSSFWorkbook(in)) {
            Reports reports = new Reports();
            reports.setReportID(fileName);
            wb.sheetIterator().forEachRemaining(e -> {
                ListScanner listScanner = new ListScanner();
                String sheetName = e.getSheetName().toLowerCase().trim();

                if (sheetName.equals("вик") || sheetName.equals("рк") ||
                        sheetName.equals("ук") || sheetName.equals("пвк")) {
                    try {
                        listScanner.scan(e);
                    } catch (Exception z){
                        System.out.println(fileName);
                    }
                    listScanner.getReport().setNtdType(sheetName);
                    reports.setResponsible(listScanner.getReport(), sheetName);

                }
            });
            this.resultMap.putIfAbsent(fileName,reports);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Void call() {
        this.analyizeWorkBooks();
        return null;
    }
}
