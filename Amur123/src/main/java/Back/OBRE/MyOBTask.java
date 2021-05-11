package Back.OBRE;

import Back.OBRE.Docx.OBREapplication;
import Back.OBRE.TableInterfaces.GetTableNumber;
import Back.Pasport.InterfacePasp.IPasportData;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;

import java.io.*;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.Callable;

public class MyOBTask implements GetTableNumber, Callable<Void> {
    @Getter @Setter
    private IPasportData pasport;
    @Getter @Setter
    private InputStream templatePath;
    @Getter @Setter
    private final String paspNumber;
    @Getter @Setter
    private Map<String, ByteArrayOutputStream> resultList;
    public MyOBTask(String paspNumber, InputStream templatePath,
                    IPasportData pasport, Map<String, ByteArrayOutputStream> resultList){
        this.pasport=pasport;
        this.templatePath=templatePath;
        this.paspNumber=paspNumber;
        this.resultList = resultList;
    }
    private void replaceDocsFromTemplate() throws XmlException, IOException {
        try(InputStream oTemplate = templatePath;
            XWPFDocument wDoc = new XWPFDocument(oTemplate))
        {
            String test = pasport.getFileName().toLowerCase()
                    .replaceAll("паспорт","").replaceAll(" ","")
                    .replaceAll("№","").replaceAll(".xlsx","");
            //wordDoc.replaceFooterText(wDoc,templatePath,"1",1);

            if(pasport.getFileName().toLowerCase().contains("рев")){
                test = pasport.getFileName().toLowerCase().split("рев")[0];
                test = test.replaceAll("паспорт","").replaceAll(" ","")
                        .replaceAll("№","").replaceAll(".xlsx","");
            } else{
                pasport.getFileName().toLowerCase()
                        .replaceAll("паспорт","").replaceAll(" ","")
                        .replaceAll("№","").replaceAll(".xlsx","");
            }
            GetTables tables = new GetTables(wDoc);
            OBREapplication wordDoc=new OBREapplication();

            wordDoc.fTablle2(tables.getTablePart2(),pasport);
            wordDoc.fillTable51(tables.getTablePart51(),pasport);
            wordDoc.fillTable53(tables.getTablepart53(),pasport);
            wordDoc.fillTable52(tables.getTablepart52(),pasport);
            wordDoc.fillTable3(tables.getTablepart3(),pasport);
            wordDoc.fillTable5OB(tables.getTable5OB(),pasport);
            wordDoc.fillTitulTable(tables.getTitul(),pasport);

            wordDoc.repalceTextInParagrahps(wDoc,"valuef",pasport.getPipename());
            wordDoc.repalceTextInParagrahps(wDoc,"valuesob",pasport.getResultMapOBRE().get(test).getObNumber());
            wordDoc.repalceTextInParagrahps(wDoc,"valuesre",pasport.getResultMapOBRE().get(test).getReNumber());

            String purposeLCase = pasport.getPipePurpose().length() > 0 ? pasport.getPipePurpose().substring(0,1)
                    .toLowerCase()+pasport.getPipePurpose().substring(1) : "";

            wordDoc.repalceTextInParagrahps(wDoc,"valuet",purposeLCase);
            wordDoc.repalceTextInParagrahps(wDoc,"2018",Integer.toString(pasport.getDate().get(Calendar.YEAR)));

            wordDoc.replaceFooterText(wDoc,pasport.getResultMapOBRE().get(test).getObNumber(),pasport);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wDoc.write(out);

            resultList.put( paspNumber+".docx",out);

        } catch (Exception e){
            e.printStackTrace();
            System.out.println(pasport.getFileName());
        }
    }

    @Override
    public Void call() throws Exception {
        this.replaceDocsFromTemplate();
        return null;
    }
}
