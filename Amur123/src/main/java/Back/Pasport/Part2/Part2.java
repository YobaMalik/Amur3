package Back.Pasport.Part2;


import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class Part2 implements  IPart2 {

    private Sheet iSheet;
    private int cIndex;

    private int pipeName;
    private int fluidCode;
    private int hazardCode;
    private int expHazard;
    private int groupGOST;
    private int katTPTC;
    private int katGOST;
    private int corrosionRate;
    private int operationPressure;
    private int operationTemp;
    private int designTemp;
    private int factoryName;
    private int testPressHydro;
    private int testPressPnevmo;
    private int minTemp;
    private int desL;
    private int billP;
    private int groupTPTC;
    private int numbOS;
    private int desingPressure;
    private int pipePurpose;

    private String getCellStringValue(int row, int column) {
        return iSheet != null ? iSheet.getRow(row).getCell(column).toString():"н/д";
    }

    private void setCellStringValue(int row, int column, String value){
        iSheet.getRow(row).getCell(column).setCellValue(value);
    }

    @Override
    public String getPipePurpose() {
        return this.getCellStringValue(pipePurpose,cIndex);
    }

    @Override
    public void setPipePurpose(String pipePurpose) {
        this.setCellStringValue(this.pipePurpose,cIndex,pipePurpose);
    }

    @Override
    public String getPipename() {
        return this.getCellStringValue(pipeName,cIndex);
    }

    @Override
    public void setPipename(String pipeName) {
        this.setCellStringValue(this.pipeName,cIndex,pipeName);
    }

    @Override
    public String getFluidCode() {
        return this.getCellStringValue(fluidCode,cIndex);
    }

    @Override
    public void setFluidCode(String fluidCode) {
        this.setCellStringValue(this.fluidCode,cIndex,fluidCode);
    }

    @Override
    public String getHazardcode() {
        return this.getCellStringValue(hazardCode,cIndex);
    }

    @Override
    public void setHazardcode(String hazardCode) {
        this.setCellStringValue(this.hazardCode,cIndex,hazardCode);
    }

    @Override
    public String getExpHazard() {
        return this.getCellStringValue(expHazard,cIndex);
    }

    @Override
    public void setExpHazard(String expHazard) {
        this.setCellStringValue(this.expHazard,cIndex,expHazard);
    }

    @Override
    public String getGroupGOST() {
        return this.getCellStringValue(groupGOST,cIndex);
    }

    @Override
    public void setGroupGOST(String groupGOST) {
        this.setCellStringValue(this.groupGOST,cIndex,groupGOST);
    }

    @Override
    public String getKatTPTC() {
        return this.getCellStringValue(katTPTC,cIndex);
    }

    @Override
    public void setKatTPTC(String katTPTC) {
        this.setCellStringValue(this.katTPTC,cIndex,katTPTC);
    }

    @Override
    public String getKatGOST() {
        return this.getCellStringValue(katGOST,cIndex);
    }

    @Override
    public void setKatGOST(String katGOST) {
        this.setCellStringValue(this.katGOST,cIndex,katGOST);
    }

    @Override
    public String getCorrosionRate() {
        return this.getCellStringValue(corrosionRate,cIndex);
    }

    @Override
    public void setCorrosionRate(String corrosionRate) {
        this.setCellStringValue(this.corrosionRate,cIndex,corrosionRate);
    }

    @Override
    public String getOperationPressure() {
        return this.getCellStringValue(operationPressure,cIndex);
    }

    @Override
    public void setOperationPressure(String operationPressure) {
        this.setCellStringValue(this.operationPressure,cIndex,operationPressure);
    }

    @Override
    public String getOperationTemp() {
        return this.getCellStringValue(operationTemp,cIndex);
    }

    @Override
    public void setOperationTemp(String operationTemp) {
        this.setCellStringValue(this.operationTemp,cIndex,operationTemp);
    }

    @Override
    public String getDesignTemp() {
        return this.getCellStringValue(designTemp,cIndex);
    }

    @Override
    public void setDesignTemp(String designTemp) {
        this.setCellStringValue(this.designTemp,cIndex,designTemp);
    }

    @Override
    public String getFactoryName() {
        return this.getCellStringValue(factoryName,cIndex);
    }

    @Override
    public void setFactoryName(String factoryName) {
        this.setCellStringValue(this.factoryName,cIndex,factoryName);
    }

    @Override
    public String getTestPressHydro() {
        return this.getCellStringValue(testPressHydro,cIndex);
    }

    @Override
    public void setTestPressHydro(String testPressHydro) {
        this.setCellStringValue(this.testPressHydro,cIndex,testPressHydro);
    }

    @Override
    public String getTestPressPnevmo() {
        return this.getCellStringValue(testPressPnevmo,cIndex);
    }

    @Override
    public void setTestPressPnevmo(String testPressPnevmo) {
        this.setCellStringValue(this.testPressPnevmo,cIndex,testPressPnevmo);
    }

    @Override
    public String getMinTemp() {
        return this.getCellStringValue(minTemp,cIndex);
    }

    @Override
    public void setMinTemp(String minTemp) {
        this.setCellStringValue(this.minTemp,cIndex,minTemp);
    }

    @Override
    public String getDesL() {
        return this.getCellStringValue(desL,cIndex);
    }

    @Override
    public void setDesL(String desL) {
        this.setCellStringValue(this.desL,cIndex,desL);
    }

    @Override
    public String getBillP() {
        return this.getCellStringValue(billP,cIndex);
    }

    @Override
    public void setBillP(String billP) {
        this.setCellStringValue(this.billP,cIndex,billP);
    }

    @Override
    public String getGroupTPTC() {
        return this.getCellStringValue(groupTPTC,cIndex);
    }

    @Override
    public void setGroupTPTC(String groupTPTC) {
        this.setCellStringValue(this.groupTPTC,cIndex,groupTPTC);
    }

    @Override
    public String getNumbOS() {
        return this.getCellStringValue(numbOS,cIndex);
    }

    @Override
    public void setNumbOS(String numbOS) {
        this.setCellStringValue(this.numbOS,cIndex,numbOS);
    }

    @Override
    public String getDesingPressure() {
        return this.getCellStringValue(desingPressure,cIndex);
    }

    @Override
    public void setDesingPressure(String desingPressure) {
        this.setCellStringValue(this.desingPressure,cIndex,desingPressure);
    }

    private void getInfoPart2(Workbook excPasp)  {
        cIndex = new CellsCoordinate().getCoordinate(excPasp);
        iSheet = excPasp.getSheet("2");
        if (iSheet != null) {
            CellRangeAddress mergeadres;
            int mergRow;
            int mergCol;
            String adString;

            this.testPress(iSheet,cIndex);

            for(int j = 0; j < iSheet.getNumMergedRegions(); ++j) {

                mergeadres = iSheet.getMergedRegion(j);
                mergRow = mergeadres.getFirstRow();
                mergCol = mergeadres.getFirstColumn();
                adString = iSheet.getRow(mergRow).getCell(mergCol).toString().toLowerCase();


                if (iSheet.getRow(mergRow).getCell(cIndex)!=null) {

                    if (adString.contains("наименование") && adString.contains("трубопровода")) {
                        if(iSheet.getRow(mergRow).getCell(cIndex)!=null) {
                            this.pipeName = iSheet.getRow(mergRow).getCell(cIndex).getRowIndex();
                        }
                    }

                    if (adString.contains("цех") && adString.contains("установка")) {
                        if(iSheet.getRow(mergRow).getCell(cIndex)!=null) {
                            this.factoryName = iSheet.getRow(mergRow).getCell(cIndex).getRowIndex();
                        }
                    }

                    if (adString.contains("наименование") && adString.contains("среды")) {
                        this.fluidCode = iSheet.getRow(mergRow).getCell(cIndex).getRowIndex();
                    }

                    if (adString.contains("класс") && adString.contains("опасности") /*&& adString.contains("гост")*/) {
                        this.hazardCode = iSheet.getRow(mergRow).getCell(cIndex).getRowIndex();
                    }

                    if (adString.contains("взрывопожароопасность")) {
                        this.expHazard = iSheet.getRow(mergRow).getCell(cIndex).getRowIndex();
                    }

                    if (adString.contains("группа") && adString.contains("среды") && adString.contains("32569")) {
                        this.groupGOST = iSheet.getRow(mergRow).getCell(cIndex).getRowIndex();
                    }

                    if (adString.contains("трубопровода") && adString.contains("категория") && adString.contains("032/2013")) {
                        this.katTPTC = iSheet.getRow(mergRow).getCell(cIndex).getRowIndex();
                    }

                    if (adString.contains("трубопровода") && adString.contains("категория")/* && adString.contains("32569")*/) {
                        this.katGOST = iSheet.getRow(mergRow).getCell(cIndex).getRowIndex();
                    }

                    if (adString.contains("группа") && adString.contains("среды") && adString.contains("032/2013")) {
                        this.groupTPTC = iSheet.getRow(mergRow).getCell(cIndex).getRowIndex();
                    }

                    if (adString.contains("минимально")&& adString.contains("допустимая")) {
                        this.minTemp = iSheet.getRow(mergRow).getCell(cIndex).getRowIndex();
                    }

                    if (adString.contains("расчетный")&& adString.contains("ресурс")) {
                        this.desL = iSheet.getRow(mergRow).getCell(cIndex).getRowIndex();
                    }

                    if (adString.contains("расчетный")&& adString.contains("службы")) {
                        this.billP = iSheet.getRow(mergRow).getCell(cIndex).getRowIndex();
                    }

                    if (adString.contains("расчетный")&& adString.contains("ресурс")) {
                        this.desL = iSheet.getRow(mergRow).getCell(cIndex).getRowIndex();
                    }

                    if (adString.contains("расчётное")&& adString.contains("количество")) {
                        this.numbOS = iSheet.getRow(mergRow).getCell(cIndex).getRowIndex();
                    }

                    if (adString.contains("назначени")&& adString.contains("трубопрово")) {
                        pipePurpose = iSheet.getRow(mergRow).getCell(cIndex).getRowIndex();
                    }

                    if (adString.contains("температур")&& adString.contains("стенк" )&& !adString.contains("отрицат")) {
                        designTemp = iSheet.getRow(mergRow).getCell(cIndex).getRowIndex();
                    }

                    if (adString.contains("рабоч")&& adString.contains("давлен")) {
                        operationPressure = iSheet.getRow(mergRow).getCell(cIndex).getRowIndex();
                    }

                    if ((adString.contains("расчётн")||adString.contains("расчетн")) &&
                            adString.contains("давлен")) {
                        desingPressure = iSheet.getRow(mergRow).getCell(cIndex).getRowIndex();
                    }


                }

            }

        }

    }

    private void testPress(Sheet iSheet, int valueColumn){
        for(int i = 0; i < iSheet.getLastRowNum(); i++){
            Row row = iSheet.getRow(i);

            for(int j = 0; j < row.getLastCellNum(); j++){
                Cell cell = row.getCell(j);
                String pParameter = "";

                if(cell != null){
                    pParameter = cell.toString().toLowerCase();
                }

                if (pParameter.contains("гидравлич")){
                    testPressHydro = iSheet.getRow(i).getCell(valueColumn).getRowIndex();
                }

                if (pParameter.contains("пневматического")&& pParameter.contains("контрол") ){
                    testPressPnevmo = iSheet.getRow(i).getCell(valueColumn).getRowIndex();
                }

            }
        }
    }

    @Override
    public void exctractData(Workbook excPasp) {
        this.getInfoPart2(excPasp);
    }
}
