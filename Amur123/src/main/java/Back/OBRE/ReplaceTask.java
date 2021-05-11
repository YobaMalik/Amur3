package Back.OBRE;

import Back.OBRE.Docx.OBREapplication;
import Back.Pasport.InterfacePasp.IPasportData;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;

import java.io.*;
import java.util.Map;
import java.util.concurrent.Callable;

public class ReplaceTask implements Callable<Void>{
    @Getter @Setter
    private IPasportData pasport;
    @Getter @Setter
    private InputStream templatePath;
    @Getter @Setter
    private final String paspNumber;
    @Getter @Setter
    private Map<String, ByteArrayOutputStream> resultList;

    public ReplaceTask(String paspNumber,
                       InputStream templatePath,
                       IPasportData pasport,
                       Map<String,
                       ByteArrayOutputStream> resultList){
        this.pasport=pasport;
        this.templatePath=templatePath;
        this.paspNumber=paspNumber;
        this.resultList = resultList;
    }

    private void createDocsFromTemplate() {
        try(InputStream oTemplate = templatePath;
            XWPFDocument wDoc=new XWPFDocument(oTemplate)){
            OBREapplication wordDoc=new OBREapplication();

            wordDoc.repalceTextInParagrahps(wDoc,"Газпром переработка Благовещенск","ВЕЛЕССТРОЙ");
            wordDoc.replaceFooterFactory(wDoc,"ООО «ВЕЛЕССТРОЙ»");

           String fileRev2 = paspNumber.replace(".docx"," rev 2.docx").replace(".DOCX"," rev 2.DOCX");
            File file = new File(fileRev2);
            OutputStream out = new FileOutputStream(file);

            System.out.println(file.getName());
         //   wDoc.write(out);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Void call(){
        this.createDocsFromTemplate();
        return null;
    }
}
