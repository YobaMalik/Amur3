package Back.Pasport.InterfacePasp;


import Back.OBRE.Docx.DataBaseObject;
import Back.Pasport.Part5.Element.Elements;
import Back.Pasport.RowfTable;
import java.util.*;

public interface IPasportData {

    Calendar getDate();
    void setDate(Calendar date);

    String getPipename();
    void setPipename(String pipename);

    String getFluidCode();
    void setFluidCode(String fluidCode);

    String getHazardcode();
    void setHazardcode(String Hazardcode);

    String getExpHazard();
    void setExpHazard(String expHazard);

    //----------------------------------

    String getGroupGOST();
    void setGroupGOST(String groupGOST);

    String getKatTPTC();
    void setKatTPTC(String katTPTC);

    String getKatGOST();
    void setKatGOST(String katGOST);

    String getCorrosionRate();
    void setCorrosionRate(String corrosionRate);

    String getOperationPressure();
    void setOperationPressure(String operationPressure);

    //----------------------------------------------------

    String getOperationTemp();
    void setOperationTemp(String operationTemp);

    String getDesignTemp();
    void setDesignTemp(String designTemp);

    String getFactoryName();
    void setFactoryName(String factoryName);

    String getTestPressHydro();
    void setTestPressHydro(String testPressHydro);

    String getTestPressPnevmo();
    void setTestPressPnevmo(String testPressPnevmo);

    //----------------------------------------------

    String getMinTemp();
    void setMinTemp(String minTemp);

    String getDesL();
    void setDesL(String desL);

    String getBillP();
    void setBillP(String billP);

    String getGroupTPTC();
    void setGroupTPTC(String groupTPTC);

    String getNumbOS();
    void setNumbOS(String numbOS);

    //------------------------------

    String getDesingPressure();
    void setDesingPressure(String desingPressure);

    String getNameTitul();
    void setNameTitul(String nameTitul);

    String getLeakTest();
    void setLeakTest(String leakTest);

    String getWeldInfo();
    void setWeldInfo(String weldInfo);

    double getMaxDN();
    void setMaxDN(double maxDN);

    //-----------------------------

    List<String> getDWGs();
    void setDWGs(List<String> dwgs);

    String getFileName();
    void setFileName(String fileName);

    String getHeatTreatment();
    void setHeatTreatment(String heatTreatment);

    String getPipePurpose();
    void setPipePurpose(String pipePurpose);

    List<RowfTable<String>> getListFromPart3();
    void setListFromPart3(List<RowfTable<String>> listFromPart3);

    //------------------------------------------
    @Deprecated
    Map<String, String> getPipeMaterial();
    @Deprecated
    void setPipeMaterial(Map<String, String> pipeMaterial);


    Map<String, DataBaseObject> getResultMapOBRE();
    void setResultMapOBRE(Map<String, DataBaseObject> resultMapOBRE);

    List<Elements> getIRowsList();
    void setIRowsList(List<Elements> iRowsList);

    List<Elements> getPart53List();
    void setPart53List(List<Elements> part53List);

    List<Elements> getPart52List();
    void setPart52List(List<Elements> part52List);

    List<Elements>  getPart3List();
    void setPart3List(List<Elements>  part3List);

    //----------------------------------------

    List<String> getPipeLine();
    void setPipeLine(List<String> pipeLine);


    //------------------------------------

    /*
    String get();
    void set(String );
     */


}
