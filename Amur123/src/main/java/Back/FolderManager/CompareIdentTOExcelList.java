package Back.FolderManager;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CompareIdentTOExcelList {
    public CompareIdentTOExcelList() throws IOException {
        this.compare();
    }
    private  Map<String, Lines> identAndFileName = new HashMap<>();
    Map<String,String> fileList = new HashMap<>();
    public void compare() throws IOException {
        try (Workbook wb = new XSSFWorkbook(GetList.class.getResourceAsStream("/FolderManager/TempFile.xlsx"))) {

            this.getFileList(fileList,"R:\\Проекты\\ЭПБ (разреш. Ростехнадзора)\\9728 Велесстрой Сертификация_Паспортизация АПГЗ\\Паспортизация\\2. Документация\\1. ИД по линиям\\архив\\1\\Труба и фасонные изделия по ФАЗЕ 1");

            Sheet sheet = wb.getSheet("ReviewInfo");

            for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                String ident = sheet.getRow(i).getCell(0).toString().replace(".0","");

                String fileName = sheet.getRow(i).getCell(1).toString().replace(".0","");
              //  System.out.println(ident+" "+fileName);
                Lines code;

                if(getIdentAndFileName().containsKey(ident)){
                    code = getIdentAndFileName().get(ident);
                } else{
                    code = new Lines();
                }
                if(!fileName.equals("")){
                    code.ident.put(fileName,fileName);
                }


                getIdentAndFileName().put(ident,code);
            }
          /*  fileList.entrySet().forEach(e->{
                System.out.println(e.getValue());
            });
         /*   identAndFileName.forEach((e,z)->{
                System.out.println(e);
                z.ident.forEach((a,b)->{
                    System.out.println(a+" "+b);
                });
            });

          */
        }
    }

    public void getFileList(Map<String,String> fileList,String filePath){
        File[] files= new File(filePath).listFiles();
        Arrays.asList(files).forEach(e->{
            fileList.putIfAbsent(e.getName().replace(".pdf",""),e.getAbsolutePath());
        });
    }

    public Map<String, Lines> getIdentAndFileName() {
        return identAndFileName;
    }

    public void setIdentAndFileName(Map<String, Lines> identAndFileName) {
        this.identAndFileName = identAndFileName;
    }
}
