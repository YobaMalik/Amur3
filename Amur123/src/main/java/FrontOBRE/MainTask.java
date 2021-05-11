package FrontOBRE;

import Back.OBRE.OBREdocuments;
import Back.Pasport.GetInfoPasp;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainTask {
    public static final String homePath = System.getProperty("user.home")+File.separator+"Desktop"+
            File.separator+"Об и РЭ от "+
            new SimpleDateFormat("dd MM yyyy").

    format(Calendar.getInstance().getTime());

    public void execute(String filePath) {
        Map<String, ByteArrayOutputStream> fileInput = new ConcurrentHashMap<>();
        Map<String, ByteArrayOutputStream> resultList = new ConcurrentHashMap<>();

        ExecutorService pool = null;
        try {

            pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            GetInfoPasp amur = new GetInfoPasp();
            this.getInputFuleList(fileInput, filePath);
            amur.createDocs(fileInput, resultList, pool);


            OBREdocuments OBRE = new OBREdocuments(amur.getMap());

            OBRE.createDocs(fileInput, resultList, pool);

            pool.shutdown();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (pool != null) {
                pool.shutdown();
            }
        }
        new File(homePath).mkdirs();
        this.saveResultToDesktop(resultList);
    }

    public void chandeFactoryName(String filePath) throws IOException {
        Map<String, InputStream> fileInput = new ConcurrentHashMap<>();
        Map<String, ByteArrayOutputStream> resultList = new ConcurrentHashMap<>();
    //    this.getInputFuleList(fileInput);
        this.getInputFuleList1(fileInput, filePath);
        ExecutorService pool = null;
        try {
            pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            OBREdocuments OBRE = new OBREdocuments();
            OBRE.replaceDocs(fileInput,resultList,pool);
            new File(homePath).mkdirs();
            this.saveResultToDesktop(resultList);
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

    public void chandeFactoryNameInFolders() throws IOException {
        Map<String, InputStream> fileInput = new ConcurrentHashMap<>();
        this.getInputFuleList(fileInput);
        ExecutorService pool = null;
        try {
            pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            OBREdocuments OBRE = new OBREdocuments();
            OBRE.replaceDocs(fileInput,null,pool);
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



    private void getInputFuleList(Map<String, ByteArrayOutputStream> fileInput,String filePath) throws IOException {
        File[] files = new File(filePath).listFiles();
        for (File file : files) {
            if(!file.getName().contains("~")) {
                try (InputStream in = new FileInputStream(file)) {
                    byte[] bytes = IOUtils.toByteArray(in);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    out.write(bytes, 0, bytes.length);
                    fileInput.put(file.getName(), out);
                    out.close();
                }
            }
        }

    }

    private void getInputFuleList1(Map<String, InputStream> fileInput,String filePath) throws IOException {
        File[] files = new File(filePath).listFiles();
        for (File file : files) {
            if(!file.getName().contains("~")) {
                try (InputStream in = new FileInputStream(file)) {
                    InputStream in1 = new ByteArrayInputStream(in.readAllBytes());
                    fileInput.put(file.getName(), in1);
                }
            }
        }
    }

    private void getInputFuleList(Map<String, InputStream> fileInput) throws IOException {
        try (Workbook wb = new XSSFWorkbook("C:\\Users\\Dmitrij.Harko\\Desktop\\Yolo.xlsx")){
            Sheet sheet = wb.getSheet("files");
            for (int i = 0; i < sheet.getLastRowNum()+1; i++){
                Row row = sheet.getRow(i);
                String filepath = row.getCell(0).toString();
            if(!filepath.contains("~"))fileInput.putIfAbsent(filepath,new FileInputStream(new File(filepath)));
            }
        }
    }
    private void saveResultToDesktop(Map<String, ByteArrayOutputStream> resultList){
        resultList.forEach((e, z) -> {
            try {
                OutputStream out = new FileOutputStream(new File(homePath+
                        // File.separator+"Result"+
                        File.separator+ e));
                z.writeTo(out);
                out.close();
                System.out.println(e.replaceAll(".docx","")+" готов");
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
    }

}
