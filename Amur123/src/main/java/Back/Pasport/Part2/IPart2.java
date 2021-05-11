package Back.Pasport.Part2;

import Back.Pasport.InterfacePasp.IExtractData;

public interface IPart2 extends IExtractData {


    String getPipePurpose();
    void setPipePurpose(String pipePurpose);

    String getPipename();
    void setPipename(String pipeName);

    String getFluidCode();
    void setFluidCode(String fluidCode);

    String getHazardcode();
    void setHazardcode(String hazardCode);

    String getExpHazard();
    void setExpHazard(String expHazard);
    //-----------------------------------------

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
    //-------------------------------------

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
    //------------------------------------------------------

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
    //-------------------------------------

    String getDesingPressure();
    void setDesingPressure(String desingPressure);


}
