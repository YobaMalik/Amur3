package NDT.NDTSpecWelders;

import FrontOBRE.MainTask;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WeldersNKSpec {
    private static final String destPath="";
    private static final String weldersPath = "R:\\Проекты\\ЭПБ (разреш. Ростехнадзора)\\" +
            "9728 Велесстрой Сертификация_Паспортизация АПГЗ\\Сертификация\\" +
            "Документы о квалификации персонала\\Квалификация\\01.02. Сварщики";

    private void getWeldersFolders(String path, Map<String, File> resultFileMap) {
        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                resultFileMap.putIfAbsent(file.getName(), file);
             //   this.getWeldersFolders(file.getPath(), resultFileMap);
            }
        }
    }

    private LineWelders getWelderFromExcel(String zone, String line) throws IOException {
        LineWelders welders = new LineWelders();

        try(InputStream in = WeldersNKSpec.class.getResourceAsStream("/Specialist/WeldersList.xlsx");
                Workbook wb = new XSSFWorkbook(in)){
            Sheet sheet = wb.getSheet("Сварщики");

            for(int i=0;i< sheet.getLastRowNum();i++){
                String cZone = sheet.getRow(i).getCell(0).toString().replace(".0","");
                String cLine = sheet.getRow(i).getCell(1).toString().replace(".0","");
                String cIDCode = sheet.getRow(i).getCell(2).toString().replace(".0","");
                String cFIO = sheet.getRow(i).getCell(3).toString().replace(".0","");

                if(zone.equals(cZone)&&line.equals(cLine)){
                    if(welders.getLine()==null&&welders.getZone()==null){
                        welders.setLine(cZone);
                        welders.setLine(cLine);
                    }
                    welders.getIdCode().putIfAbsent(cIDCode,cFIO);
                }
            }
        }
        return welders;
    }


    public void compare(String zone,String line) throws IOException {
        Map<String, File> resultFileMap = new HashMap<>();
        System.out.println("полный путь к папкам с удостоверениями сваршиков");
        System.out.println(weldersPath);

        this.getWeldersFolders(weldersPath,resultFileMap);

        LineWelders welders = this.getWelderFromExcel(zone,line);
        for (Map.Entry<String, File> entry:resultFileMap.entrySet()){
            welders.getIdCode().forEach((e,z)->{
                if(entry.getKey().contains(e)){
                    try {
                        String resultFilePath = System.getProperty("user.home")+
                                File.separator+"Desktop"+File.separator+"Сварщики для "+zone+" "+line;
                        new File(resultFilePath+File.separator+entry.getKey()).mkdirs();
                        System.out.println(entry.getKey());
                        FileUtils.copyDirectory(entry.getValue(),new File(resultFilePath+File.separator+
                                entry.getKey()));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
        }
    }
}
