package Back.FolderManager;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FilesCopyTOFolders {
    public static String resultFolder = "R:\\Проекты\\ЭПБ (разреш. Ростехнадзора)\\" +
            "9728 Велесстрой Сертификация_Паспортизация АПГЗ\\Паспортизация\\2. Документация\\" +
            "1. ИД по линиям\\Раскидано по паспортам 2-40";

    private final CompareIdentTOExcelList comList;
    private final String number;
    private final Pasport pasport;

    public FilesCopyTOFolders( CompareIdentTOExcelList comList,String number, Pasport pasport) {
        this.comList = comList;
        this.number = number;
        this.pasport = pasport;
    }

    public void copy() {
           // System.out.println(number);
            pasport.lines.forEach((line, codes) ->{
                new File(resultFolder+"\\"+number+"\\"+line).mkdirs();
                codes.ident.forEach((key, value) -> {
                    if (comList.getIdentAndFileName().containsKey(value)) {
                        comList.getIdentAndFileName().get(value).ident.forEach((code1, code2) -> {
                            if (comList.fileList.containsKey(code1)) {
                                //  System.out.println(resultFolder+"\\"+number+"\\"+line+"\\"+new File(comList.fileList.get(code1)).getName());
                                try {
                                    File source = new File(comList.fileList.get(code1));
                                    File dest = new File(resultFolder + "\\" + number + "\\" + line + "\\"
                                            + new File(comList.fileList.get(code1)).getName());
                                    if (!dest.exists()) {
                                        FileUtils.copyFile(source, dest);
                                        System.out.println(line + " " + dest.getName());
                                        //   Thread.sleep(1000);
                                    }

                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                        });
                    }
                });
            } );

    /*
        pipeLineList.resultMap.forEach((number, pasport) ->{
            System.out.println(number);
            pasport.lines.forEach((line, codes) ->{
                new File(resultFolder+"\\"+number+"\\"+line).mkdirs();
                codes.ident.entrySet().forEach(e->{
                 if(comList.getIdentAndFileName().containsKey(e.getValue())){
                     comList.getIdentAndFileName().get(e.getValue()).ident.forEach((code1,code2)->{
                         if(comList.fileList.containsKey(code1)){

                             System.out.println(resultFolder+"\\"+number+"\\"+line+"\\"+new File(comList.fileList.get(code1)).getName());
                             try {
                                 FileUtils.copyFile(new File(comList.fileList.get(code1)),
                                         new File(resultFolder+"\\"+number+"\\"+line+"\\"+new File(comList.fileList.get(code1)).getName()));
                             } catch (IOException ioException) {
                                 ioException.printStackTrace();
                             }

                         }
                     });
                 }

                });
            } );
        } );
     */
    }
}
