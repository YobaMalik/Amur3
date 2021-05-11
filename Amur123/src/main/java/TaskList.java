import Back.CertificateTask;
import Back.FolderManager.DoubleCommander;
import Back.FolderManager.PDFMerge.MergeFiles;
import FrontOBRE.MainTask;
import FrontOBRE.MyFrame;
import NDT.NDTSpecWelders.WeldersNKSpec;
import NDT.ReportList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.Scanner;

public class TaskList {
    public void execute() throws IOException {
        System.out.println("1 - сделать рэ/об");
        System.out.println("2 - собрать данные по удостоверениям сварщиков");
        System.out.println("3 - список файлов из папки и подпапок на рабочий стол в Result.txt");
        System.out.println("4 - Замена Газпром переработка на Велесстрой");
   //     System.out.println("3 - сортировка сертификатов 1-50");
   //     System.out.println("4 - анализ актов НК");
        Scanner scanner = new Scanner(System.in);
        int incValue =0;

        if(scanner.hasNext()){
            incValue = scanner.nextInt();
        }
        MyFrame frame = new MyFrame();
        switch (incValue){
            case 1:
                frame.setInputFilePath();
                if(frame.getInputFilePath()!=null) new MainTask().execute(frame.getInputFilePath()); //ob and re
                break;
            case 2:
                System.out.println("введите зону и линию через пробел");
                Scanner scanner1 = new Scanner(System.in);
                String incStr = scanner1.nextLine();
                String[] pipelineInfo = incStr.split(" ");
                if(pipelineInfo.length==2) {
                    new WeldersNKSpec().compare(pipelineInfo[0], pipelineInfo[1].trim());
                } else{
                    System.out.println("неправильный формат введенных данных");
                }
                break;
            case 3:
                frame.setInputFilePath();
                if(frame.getInputFilePath()!=null) new DoubleCommander().getFileList(frame.getInputFilePath());
                break;
            case 4:
               frame.setInputFilePath();
                if(frame.getInputFilePath()!=null) new MainTask().chandeFactoryName(frame.getInputFilePath());
            case 5:
                frame.setInputFilePath();
             //   new MergeFiles().merge();//.merge(frame.getInputFilePath()); //ob and re
                if(frame.getInputFilePath()!=null)new MergeFiles().merge(frame.getInputFilePath()); 
                break;
    /*        case 6:
                new CertificateTask().execute();
                break;
       /*     case 5:
                new MainTask().chandeFactoryNameInFolders();//frame.getInputFilePath()); //ob and re
                break;
/*
            case 3:
                new CertificateTask().execute();
                break;
            case 4:
                System.out.println("Oh my");
                new ReportList().checkWbLists();
                break;

 */
        }
    }
    public void execute(int i) throws IOException {
        MyFrame frame = new MyFrame();
        frame.setInputFilePath();
        if(frame.getInputFilePath()!=null) new MainTask().execute(frame.getInputFilePath());
    }
}
