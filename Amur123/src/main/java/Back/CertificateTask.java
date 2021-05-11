package Back;

import Back.FolderManager.CompareIdentTOExcelList;
import Back.FolderManager.GetList;
import Back.FolderManager.Task.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CertificateTask {

    public void execute()  {
        ExecutorService pool = null;
        try {
            pool = Executors.newFixedThreadPool(10);
            GetList list = new GetList();
            CompareIdentTOExcelList comList = new CompareIdentTOExcelList();
            //CompareIdentTOExcelList comList,String number, Back.Pasport pasport
            List<Task> taskList = new ArrayList<>();
            list.resultMap.forEach((e,z) -> {
              if(!e.equals("")) taskList.add(new Task(comList,e,z));
            });
            pool.invokeAll(taskList);
            pool.shutdown();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (pool != null) {
                pool.shutdown();
            }
        }

    }
}
