package Back.Pasport;



import Back.Interface.IResultDocs;
import Back.Pasport.InterfacePasp.IPasportData;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;


public class GetInfoPasp implements IResultDocs {


    private Queue<RowfTable<String>> allTable1=new ConcurrentLinkedQueue<>();
    private Map<String, IPasportData> pasportDataMap = new ConcurrentHashMap<>();
    private List<RowfTable<String>> resList=new ArrayList<>();
    private String tString="zulul";
    ExecutorService pool;

    private void getTable(Map<String, ByteArrayOutputStream> fileInput,
                          Map<String, ByteArrayOutputStream> resultList) throws IOException {
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("Result");
        Row row = sheet.createRow(0);
        String[] head={"Линия","Рабочая давление","рабочая температура","расчетное давление","расчетная температура","диаметр и толщина","отбрак","длина","наименование трубопровода","класс опасности по гост",
                "наименование рабочей среды","пожаровзрывоопасность","группа рс по гост","кат по тр тс","кат по гост","скорость коррозии","название файла","рабочее давление раздел 2","расчетное давление раздел 2","рабочая температура раздел 2",
                "расчетная температура раздел 2","чертежи","сварка","давление испытаний 2","герметичность","что-то","№ паспортца","макс диаметр паспорта","материал труб, требуется сверка с паспортом в случае пустого значения"};
        for (int i=0;i<head.length;i++){
            row.createCell(i).setCellValue(head[i]);
        }

        try( ByteArrayOutputStream outputStream =  new ByteArrayOutputStream()) {
            List<Callable<Void>> tasks = new ArrayList<>();
            for(Map.Entry<String, ByteArrayOutputStream> entry:fileInput.entrySet()){
                InputStream in = new ByteArrayInputStream(entry.getValue().toByteArray());
                tasks.add(new MyTask(entry.getKey(),in,  allTable1,pasportDataMap));
            }
            try {
                pool.invokeAll(tasks);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
           List<RowfTable<String>> resultTable= Lists.newArrayList(allTable1.iterator());
            resultTable.sort((arg0, arg1) -> Integer.compare(arg0.getValue(26).compareTo(arg1.getValue(26)), 0));

            this.resList=resultTable;
            int sheetIndexRow=1;
            for (RowfTable<String> n1 : resultTable) {
                Row newr = sheet.createRow(sheetIndexRow);
                for (int i = 0; i < n1.getSize(); i++) {
                    if (!(n1.getValue(i) == null)) {
                        newr.createCell(i).setCellValue(n1.getValue(i));
                    } else {
                        newr.createCell(i).setCellValue("");
                    }
                }
                sheetIndexRow++;
            }

            book.write(outputStream);
         //   resultList.put("ResultTable.xlsx",outputStream);
            this.allTable1.clear();

        }

    }


    @Override
    public void createDocs(Map<String, ByteArrayOutputStream> fileInput,
                           Map<String, ByteArrayOutputStream> resultList,
                           ExecutorService newTask) throws IOException {

        this.pool = newTask;
        this.getTable(fileInput,resultList);
    }

    public Map<String, IPasportData> getMap(){
        return this.pasportDataMap;
    }

    public Map<String,RowfTable<String>> getQueue(){
        Map<String,RowfTable<String>> rows = new HashMap<>();
        allTable1.forEach(e->{
            rows.putIfAbsent(e.getValue(16),e);
        });
        return rows;
    }
}


