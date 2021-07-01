package Back.FolderManager;

import Back.OBRE.OBREdocuments;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetList {
    public static String lineAndZonePath;

    static {
        try {
            lineAndZonePath = OBREdocuments.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI().getPath()
                    .replace("Amur123.jar","");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public GetList() throws IOException {
        this.createList();
    }

    public Map<String,Pasport> resultMap = new HashMap<>();

    public void createList() throws IOException {
        try(InputStream in = GetList.class.getResourceAsStream("/FolderManager/TempFile.xlsx");
                Workbook wb = new XSSFWorkbook(in)){
            Sheet sheet = wb.getSheet("PaspData");
            for(int i = 0; i < sheet.getLastRowNum() + 1; i++){
                Cell cell = sheet.getRow(i).getCell(4);
                if(cell != null){
                    String cellValue = cell.toString();
                    if(!cellValue.contains("(SNiP)") || !cellValue.contains("Установка  1-50")||
                            !cellValue.contains("#Н/Д") || !cellValue.contains("Оборудование")||
                            !cellValue.contains("Паспорт №Р3")){
                        Pasport pasport ;
                        Lines lines;
                        String line = sheet.getRow(i).getCell(3).toString();
                        String ident = sheet.getRow(i).getCell(0).toString().replace(".0","");

                        if(resultMap.containsKey(cellValue)){
                            pasport = resultMap.get(cellValue);
                        } else{
                            pasport =new Pasport();
                            pasport.setNumber(cellValue);
                        }

                        if(pasport.lines.containsKey(line)){
                           lines = pasport.lines.get(line);
                        } else{
                            lines = new Lines();
                            lines.setLine(line);
                            pasport.lines.put(line,lines);
                        }

                        lines.ident.putIfAbsent(ident,ident);
                        resultMap.put(pasport.getNumber(),pasport);
                    }
                }

            }

        }
    }

    @Deprecated
    public void getSOMList() throws IOException {
        Map<String,String> som = new HashMap<>();
        try(InputStream in = GetList.class.getResourceAsStream("/FolderManager/SOM.xlsx");
            Workbook wb = new XSSFWorkbook(in)){
            Sheet sheet = wb.getSheet("Лист1");
            for(int i = 0; i < sheet.getLastRowNum() + 1; i++){
                Cell cell = sheet.getRow(i).getCell(0);
                som.putIfAbsent(cell.toString().toLowerCase(),cell.toString().toLowerCase());
            }

        }
        List<String> fileList = new ArrayList<>();
        this.getFileList("R:\\Проекты\\ЭПБ (разреш. Ростехнадзора)\\9728 Велесстрой Сертификация_Паспортизация АПГЗ\\Паспортизация\\2. Документация\\1. ИД по линиям\\ТестПак\\АЭ+св+исп\\2-40\\22.04.2021",
                fileList);
        fileList.forEach(e -> {
            File file = new File(e);
            String fileName = file.getName().toLowerCase().replace("-2.2-001.pdf","");
            if(som.containsKey(fileName)){
                try {
                    System.out.println(file.getName());
                    FileUtils.copyFile(file,new File("C:\\Users\\Dmitrij.Harko\\Desktop\\Св-ва\\"+file.getName()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    @Deprecated
    private void getFileList(String path, List<String> fileList){
        File[] files = new File(path).listFiles();
        for(File file: files){
            if(!file.isDirectory()){
                fileList.add(file.getPath());

            } else{
                getFileList(file.getPath(),fileList);
            }
        }
    }
}
