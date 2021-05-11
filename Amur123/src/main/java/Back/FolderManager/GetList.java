package Back.FolderManager;

import Back.OBRE.OBREdocuments;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
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
}
