package Back.Pasport;

import Back.Pasport.InterfacePasp.IPasportData;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;

public class MyTask implements Callable<Void> {
    private Map<String, IPasportData> pasportDataMap;
    private InputStream inputFile;
    private final String filename;
    private Queue<RowfTable<String>> allTable;
    public MyTask(String filename, InputStream inputFile, Queue<RowfTable<String>> allTable1, Map<String,
            IPasportData> pasportDataMap){
        this.allTable = allTable1;
        this.inputFile = inputFile;
        this.filename = filename;
        this.pasportDataMap = pasportDataMap;
    }
		public Void call() {
        try{
            IPasportData pasportPipeGost = new PasportPipeGost(inputFile,filename);
        //    newDocument.fillResultQueue(filename,allTable);
            pasportDataMap.putIfAbsent(filename,pasportPipeGost);

        } catch (Exception e){
            e.printStackTrace();
            System.out.println(filename);
        }
            return null;
        }
    }


