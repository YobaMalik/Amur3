package ZululPackage;

import Back.Pasport.TPTC032;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CopyNTD implements TPTC032 {
    private final String path ="R:\\Проекты\\ЭПБ (разреш. Ростехнадзора)\\9728 Велесстрой Сертификация_Паспортизация АПГЗ" +
            "\\Сертификация\\Документы о квалификации персонала\\Квалификация\\Специалисты НК" +
            "\\Специалисты НК\\Корки НК";

    public void copySpec(){
        try(Workbook wb = new XSSFWorkbook("C:\\Users\\Dmitrij.Harko\\Desktop\\YobaMalik.xlsx")){
            List<String> fio = new ArrayList<>();

            wb.getSheet("Лист1").rowIterator().forEachRemaining(e->{
              fio.add(e.getCell(0).toString());
            });
                    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
