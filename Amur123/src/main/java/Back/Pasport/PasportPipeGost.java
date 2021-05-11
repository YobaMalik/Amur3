package Back.Pasport;

import Back.OBRE.Docx.DataBaseObject;
import Back.Pasport.InterfacePasp.IPasportData;
import Back.Pasport.Part2.IPart2;
import Back.Pasport.Part2.Part2;
import Back.Pasport.Part3.IPart3;
import Back.Pasport.Part3.Part3;
import Back.Pasport.Part4.IPart4;
import Back.Pasport.Part4.Part4;
import Back.Pasport.Part5.Element.Elements;
import Back.Pasport.Part5.MaxDN;
import Back.Pasport.Part5.PaspInfoPart5;
import Back.Pasport.Part5.PaspInfoPart52;
import Back.Pasport.Part5.PaspInfoPart53;
import Back.Pasport.Part6.Part6;
import Back.Pasport.Titul.Titul;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


public class PasportPipeGost implements IPasportData {

    private IPart2 part2=new Part2();
    private IPart3 part3=new Part3();
    private IPart4 part4=new Part4();
    private MaxDN maxDNPart5=new MaxDN();

    private Part6 part6=new Part6();
    private Titul titul=new Titul();

    @Getter @Setter private Calendar date;
    @Getter @Setter private String pipename;
    @Getter @Setter private String fluidCode;
    @Getter @Setter private String Hazardcode;
    @Getter @Setter private String expHazard;

    @Getter @Setter private String groupGOST;
    @Getter @Setter private String katTPTC;
    @Getter @Setter private String katGOST;
    @Getter @Setter private String corrosionRate;
    @Getter @Setter private String operationPressure;

    @Getter @Setter private String operationTemp;
    @Getter @Setter private String designTemp;
    @Getter @Setter private String factoryName;
    @Getter @Setter private String testPressHydro;
    @Getter @Setter private String testPressPnevmo;

    @Getter @Setter private String minTemp;
    @Getter @Setter private String desL;
    @Getter @Setter private String billP;
    @Getter @Setter private String groupTPTC;
    @Getter @Setter private String numbOS;

    @Getter @Setter private String desingPressure;
    @Getter @Setter private String nameTitul;
    @Getter @Setter private String leakTest;
    @Getter @Setter private String weldInfo;
    @Getter @Setter private double maxDN;

    @Getter @Setter private List<String> DWGs;
    @Getter @Setter private String fileName;
    @Getter @Setter private String heatTreatment;
    @Getter @Setter private String pipePurpose;
    @Getter @Setter private List<RowfTable<String>> listFromPart3=new ArrayList<>();

    @Getter @Setter private Map<String, String> pipeMaterial;

    @Getter @Setter private Map<String, DataBaseObject> resultMapOBRE;
    @Getter @Setter private List<Elements> iRowsList;//elements from part 5.1
    @Getter @Setter private List<Elements> part53List;
    @Getter @Setter private List<Elements> part52List;
    @Getter @Setter private List<Elements> part3List;

    @Getter @Setter private List<String> pipeLine=new ArrayList<>();


    public PasportPipeGost(InputStream inStr, String filename) throws IOException, ParseException {
        Workbook excPasp=new XSSFWorkbook(inStr);
        this.part2.exctractData(excPasp);
        this.part3.exctractData(excPasp);

        PaspInfoPart5 part51 = new PaspInfoPart5(excPasp);
        this.setIRowsList(part51.fillTable("5.1"));

        PaspInfoPart53 part53 = new PaspInfoPart53(excPasp);
        this.setPart53List(part53.fillTable("5.3"));

        PaspInfoPart52 part52 = new PaspInfoPart52(excPasp);
        this.setPart52List(part52.fillTable("5.2"));

        this.part4.exctractData(excPasp);

        this.defineVariables(filename);
    /*    this.maxDNPart5.exctractData(excPasp);
        this.part6.exctractData(excPasp);
        this.titul.exctractData(excPasp);

     */
    }

    private void defineVariables(String fileName){
        this.setFileName (fileName);
        //define from part 2
        this.setPipename(this.part2.getPipename());
        this.setFluidCode(this.part2.getFluidCode());
        this.setHazardcode(this.part2.getHazardcode());
        this.setExpHazard(this.part2.getExpHazard());
        this.setGroupGOST(this.part2.getGroupGOST());
        this.setKatTPTC(this.part2.getKatTPTC());
        this.setKatGOST(this.part2.getKatGOST());
        this.setCorrosionRate(this.part2.getCorrosionRate());
        this.setOperationPressure(this.part2.getOperationPressure());
        this.setOperationTemp(this.part2.getOperationTemp());
        this.setDesignTemp(this.part2.getDesignTemp());
        this.setFactoryName(this.part2.getFactoryName());
        this.setTestPressHydro(this.part2.getTestPressHydro());
        this.setTestPressPnevmo(this.part2.getTestPressPnevmo());
        this.setMinTemp(this.part2.getMinTemp());
        this.setDesL(this.part2.getDesL());
        this.setBillP(this.part2.getBillP());
        this.setGroupTPTC(this.part2.getGroupTPTC());
        this.setNumbOS(this.part2.getNumbOS());
        this.setDesingPressure(this.part2.getDesingPressure());
        this.setPipePurpose(this.part2.getPipePurpose());
        //from titul
        this.setNameTitul(this.titul.getNumbTitul());
        //System.out.println( this.nameTitul+" "+this.titul.getNumbTitul());
        //from part6
        this.setLeakTest(this.part6.getLeakTest());

        // from part 3 and 5
        this.setMaxDN(this.maxDNPart5.getMaXDN());
        this.setPipeMaterial(this.maxDNPart5.getPipeMaterial());

        //from part 4
     //   this.weldInfo=this.part4.getWeldInfo();
        this.setDWGs(this.part4.getDWGsList());
        this.setDate(this.part4.getDate());
     //   this.heatTreatment=this.part4.getHeatTreatment();
        this.setPart3List(this.part3.getElementsPart3());
    }

    public void fillResultQueue(String fileName, Queue<RowfTable<String>> allTable)  {
        this.defineVariables(fileName);

        this.getListFromPart3().forEach(singleRow->{
            singleRow.addValue(8,this.getPipename());
            singleRow.addValue(9,this.getHazardcode());
            singleRow.addValue(10,this.getFluidCode());//error
            singleRow.addValue(11,this.getExpHazard());
            singleRow.addValue(12,this.getGroupGOST());//error
            singleRow.addValue(13,this.getKatTPTC());
            singleRow.addValue(14,this.getKatGOST());
            singleRow.addValue(15,this.getCorrosionRate());
            singleRow.addValue(16,this.getFileName());
            singleRow.addValue(17,this.getOperationPressure());
            singleRow.addValue(18,this.getDesingPressure());
            singleRow.addValue(19,this.getOperationTemp());
            singleRow.addValue(20,this.getDesignTemp());
            singleRow.addValue(21,String.join(",",this.getDWGs().toArray(String[]::new)));
            singleRow.addValue(22,this.getWeldInfo());
            singleRow.addValue(23, this.getTestPressHydro());
            singleRow.addValue(24, this.getTestPressPnevmo());
            singleRow.addValue(25, this.getFactoryName());
            singleRow.addValue(26,this.getNameTitul());
            singleRow.addValue(27, Double.toString(this.getMaxDN()));
            singleRow.addValue(28, this.getPipeMaterial().
                    getOrDefault(singleRow.getValue(5), "требуется проверка"));
            singleRow.addValue(29,this.getMinTemp());
            singleRow.addValue(30, this.getBillP());
            singleRow.addValue(31, this.getDesL());
            singleRow.addValue(32, this.getNumbOS());
            singleRow.addValue(33, this.getGroupTPTC());
            singleRow.addValue(34,this.getPipePurpose());
            allTable.add(singleRow);
        });
    }


    private void sort(){
       List<Elements> arr = this.getPart3List().stream()
               .filter(z->z.getElementName().contains("Труб")).collect(Collectors.toList());
       arr.forEach(System.out::println);
    }




    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        PasportPipeGost data = (PasportPipeGost) obj;
        boolean reslut = false;
        return reslut;
    }
}
