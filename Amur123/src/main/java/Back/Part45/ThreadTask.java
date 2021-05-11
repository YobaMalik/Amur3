package Back.Part45;

import org.apache.poi.ss.usermodel.Workbook;
import Back.Interface.IRows;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class ThreadTask implements Callable<Void> {
    private InputStream fFile;
    private final String fileName;
    Map<String, ByteArrayOutputStream> fileStengthCalc;
    public ThreadTask(String fileName, InputStream fFile, Map<String, ByteArrayOutputStream> fileStengthCalc){
        this.fFile=fFile;
        this.fileName=fileName;
        this.fileStengthCalc=fileStengthCalc;
    }



    @Override
    public Void call() throws Exception {
        try(Workbook wb=new XSSFWorkbook(fFile);
            Workbook resultWb=new XSSFWorkbook();
            ByteArrayOutputStream bStream=new ByteArrayOutputStream()
        )
        {
            List<IRows<String>> resultList=new ArrayList<>();
            PaspInfoPart45 wtf=new PaspInfoPart45(wb,this.fileName);
            wtf.FillTable(resultList,"5.1","5.4");

            SplitResultInfo result=new SplitResultInfo(resultWb,resultList);
            result.fillResultDocs();

            resultWb.write(bStream);
            fileStengthCalc.putIfAbsent(fileName,bStream);
        }
        return null;
    }
}
