package Back.FolderManager.PDFMerge;

import Back.Pasport.TPTC032;
import org.apache.commons.compress.archivers.zip.ScatterZipOutputStream;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MergeFiles implements TPTC032 {

    public void merge() throws IOException {
        List<String> pasportNumbers = this.pasportNumbers();
        pasportNumbers.forEach(e -> {
            try {
                this.merge(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    public void merge(String filePath) throws IOException {
        List<String> fileList = new ArrayList<>();
        List<String> notPDF = new ArrayList<>();
        this.getFileListPdf(filePath,fileList);
        PDFMergerUtility ut = new PDFMergerUtility();

        fileList.forEach(e -> {
            try {
               if(!e.toLowerCase().contains("thumbs.db") &&
                       !e.toLowerCase().contains(".doc") &&
                       !e.toLowerCase().contains(".xls") &&
                       !e.toLowerCase().contains(".jpg") &&
                       !e.toLowerCase().contains(".txt")){
                   ut.addSource(e);
                   System.out.println(e);
               } else{
                   notPDF.add(e);
               }
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        ut.setDestinationFileName(filePath + File.separator + "Сертификаты качества.pdf");
        try {
            ut.mergeDocuments();
        } catch (Exception e){
            e.printStackTrace();
        }

//            this.writeToTxt(filePath,fileList,"Список файлов в папке");
  //          this.writeToTxt(filePath,notPDF,"Вручную перевести");
        System.out.println("Done ежжи да?");
    }
    private void getFileListPdf(String path, List<String> fileList){
        File[] files = new File(path).listFiles();
        for(File file: files){
            if(!file.isDirectory()){
                    fileList.add(file.getPath());
            }
        }
        for(File file: files){
            if(file.isDirectory()){
                getFileListPdf(file.getPath(),fileList);
            }
        }

    }
    private void writeToTxt(String path,List<String> files, String result){
        try(Writer writer = new FileWriter(path + File.separator + result + ".txt")){
            files.forEach(e -> {
                try {
                    writer.write(e);
                    writer.write("\n");
                    writer.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> pasportNumbers() throws IOException {
        List<String> filePath = new ArrayList<>();

        try(Workbook wb = new XSSFWorkbook("C:\\Users\\Dmitrij.Harko\\Desktop\\Лист Microsoft Excel (2).xlsx")){
            Sheet sheet = wb.getSheet("Лист2");
            for (int i = 0; i < sheet.getLastRowNum() + 1; i++){
                Cell cell = sheet.getRow(i).getCell(0);
                filePath.add(cell.toString());
            }
        }
        return filePath;
    }
}
