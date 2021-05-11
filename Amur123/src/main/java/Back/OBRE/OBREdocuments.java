package Back.OBRE;


import Back.Interface.IResultDocs;
import Back.OBRE.Docx.DataBaseObject;
import Back.OBRE.Template.OBRETemplate;
import Back.Pasport.InterfacePasp.IPasportData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

public class OBREdocuments implements IResultDocs{
    private final Map<String, IPasportData> pasportDataMap;
    public OBREdocuments(){
        pasportDataMap = null;
    }
    public OBREdocuments(Map <String, IPasportData> pasportDataMap) {
        this.pasportDataMap = pasportDataMap;
    }

    public static String templatePath;


    private static final String OB_TEMPLATE_WITH_FLANGES
            = "/Template/WithFlanges/OB.DOCX";
    private static final String RE_TEMPLATE_WITH_FLANGES
            = "/Template/WithFlanges/RE.DOCX";

    private static final String OB_TEMPLATE_WITH_OUT_FLANGES
            = "/Template/WithOutFlanges/OB.DOCX";
    private static final String RE_TEMPLATE_WITH_OUT_FLANGES
            = "/Template/WithOutFlanges/RE.DOCX";

    private static final String OB_TEMPLATE_WITH_OUT_FLANGES_AND_VALVES
            = "/Template/WithOutFlangesAndValves/OB.DOCX";
    private static final String RE_TEMPLATE_WITH_OUT_FLANGES_AND_VALVES
            = "/Template/WithOutFlangesAndValves/RE.DOCX";

    private static final String OB_TEMPLATE_WITH_FLANGES_AND_WITH_OUT_VALVES
            = "/Template/WithFlangesAndWithoutValves/OB.DOCX";
    private static final String RE_TEMPLATE_WITH_FLANGES_AND_WITH_OUT_VALVES
            = "/Template/WithFlangesAndWithoutValves/RE.DOCX";

    private static Map<String, DataBaseObject> resultMapOBRE;

    static {
        try {
           // System.out.println(OBREdocuments.class.getResource("/Шаблоны/Без фланцев/ОБ.docx").getPath());
            templatePath = OBREdocuments.class.getProtectionDomain().
                getCodeSource().getLocation().toURI().getPath()
                .replace("Amur.jar","");
            resultMapOBRE = getDatas();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }



    private static Map<String, DataBaseObject> getDatas() {
        Map<String, DataBaseObject> resultMapOBRE = new ConcurrentHashMap<>();
        try (InputStream in = OBREdocuments.class.getResourceAsStream("/Template/DataBase.xlsx");
             Workbook wb = new XSSFWorkbook(in)) {
            Sheet sheet = wb.getSheet("Лист1");

            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String paspNumber = row.getCell(0).toString();
                DataBaseObject datas;
                datas = new DataBaseObject();
                datas.setPaspNumber(paspNumber);
                datas.setObNumber(row.getCell(2).toString());
                datas.setReNumber(row.getCell(3).toString());
                datas.setAip(row.getCell(1).toString());
                resultMapOBRE.put(datas.getPaspNumber(), datas);

            }
            wb.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMapOBRE;
    }

    public void replaceDocs(Map<String, InputStream> fileInput,
                           Map<String, ByteArrayOutputStream> resultList,
                           ExecutorService newTask ){
        List<ReplaceTask> replaceTask = new ArrayList<>();
        for(Map.Entry<String, InputStream> entry:fileInput.entrySet()){
            replaceTask.add(new ReplaceTask(entry.getKey(),entry.getValue(),null,resultList));
        }
        try {
            newTask.invokeAll(replaceTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createDocs(Map<String, ByteArrayOutputStream> fileInput,
                           Map<String, ByteArrayOutputStream> resultList,
                           ExecutorService newTask ) {
        List<MyOBTask> obreTask = new ArrayList<>();
        for(Map.Entry<String, IPasportData> entry:pasportDataMap.entrySet()){
            entry.getValue().setResultMapOBRE(OBREdocuments.resultMapOBRE);
            String test;
            if(entry.getValue().getFileName().toLowerCase().contains("рев")){
                test = entry.getValue().getFileName().toLowerCase().split("рев")[0];
                test = test.replaceAll("паспорт","").replaceAll(" ","")
                        .replaceAll("№","").replaceAll(".xlsx","");
            } else{
                test = entry.getValue().getFileName().toLowerCase()
                        .replaceAll("паспорт","").replaceAll(" ","")
                        .replaceAll("№","").replaceAll(".xlsx","");
            }
            DataBaseObject datas = OBREdocuments.resultMapOBRE.get(test);

                obreTask.add(new MyOBTask(datas.getObNumber(),this.templateToTask(entry.getValue()).getOb(),
                        entry.getValue(),resultList));
                obreTask.add(new MyOBTask(datas.getReNumber(),this.templateToTask(entry.getValue()).getRe(),
                        entry.getValue(),resultList));
        }
          try {
                newTask.invokeAll(obreTask);
          } catch (InterruptedException e) {
                e.printStackTrace();
          }
    }

    private OBRETemplate templateToTask(IPasportData pasportData){
        OBRETemplate paths = new OBRETemplate();

            if (pasportData.getPart52List().size() > 0 && pasportData.getPart53List().size() > 0) {
                paths.setOb(OBREdocuments.class.getResourceAsStream(OBREdocuments.OB_TEMPLATE_WITH_FLANGES));
                paths.setRe(OBREdocuments.class.getResourceAsStream(OBREdocuments.RE_TEMPLATE_WITH_FLANGES));
            }

            if (pasportData.getPart52List().size() == 0 && pasportData.getPart53List().size() > 0) {
                paths.setOb(OBREdocuments.class.getResourceAsStream(OBREdocuments.OB_TEMPLATE_WITH_OUT_FLANGES));
                paths.setRe(OBREdocuments.class.getResourceAsStream(OBREdocuments.RE_TEMPLATE_WITH_OUT_FLANGES));
            }

            if (pasportData.getPart52List().size() > 0 && pasportData.getPart53List().size() == 0) {
                paths.setOb(OBREdocuments.class.getResourceAsStream(OBREdocuments.OB_TEMPLATE_WITH_FLANGES_AND_WITH_OUT_VALVES));
                paths.setRe(OBREdocuments.class.getResourceAsStream(OBREdocuments.RE_TEMPLATE_WITH_FLANGES_AND_WITH_OUT_VALVES));
            }


            if (pasportData.getPart52List().size() == 0 && pasportData.getPart53List().size() == 0) {
                paths.setOb(OBREdocuments.class.getResourceAsStream(OBREdocuments.OB_TEMPLATE_WITH_OUT_FLANGES_AND_VALVES));
                paths.setRe(OBREdocuments.class.getResourceAsStream(OBREdocuments.RE_TEMPLATE_WITH_OUT_FLANGES_AND_VALVES));
            }
        return paths;
    }
}
