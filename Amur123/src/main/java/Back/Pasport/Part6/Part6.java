package Back.Pasport.Part6;


import Back.Pasport.InterfacePasp.IExtractData;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class Part6 implements IPart6 {
    @Getter @Setter
    private String leakTest;

    private void getInfoPart6(Workbook excPasp){
        Sheet iSheet = null;
        for(int j = 0; j < excPasp.getNumberOfSheets(); ++j) {
            if (excPasp.getSheetName(j).equals("6")) {
                iSheet = excPasp.getSheetAt(j);
                break;
            }
        }
        if (iSheet != null) {
            int leakTestRow=0;
            CellRangeAddress mergeAdres;
            int mergRow;
            int mergCol;

            for(int j = 0; j < iSheet.getNumMergedRegions(); ++j) {
                mergeAdres = iSheet.getMergedRegion(j);
                mergRow = mergeAdres.getFirstRow();
                mergCol = mergeAdres.getFirstColumn();
                String adString = iSheet.getRow(mergRow).getCell(mergCol).toString().toLowerCase();
                if (adString.contains("герметичность") ) {
                    leakTestRow = mergRow;
                }
            }

            for(int i = 0; i < iSheet.getRow(leakTestRow).getLastCellNum(); i++) {
                CellType checkType = iSheet.getRow(leakTestRow).getCell(i).getCellType();

                if (checkType != CellType.BLANK) {
                    String adString = iSheet.getRow(leakTestRow).getCell(i).toString().toLowerCase();
                    if (!adString.contains("герметичность")||!adString.contains("испытаний")){
                        this.leakTest = iSheet.getRow(leakTestRow).getCell(i).toString();
                    }
                }
            }
        }
    }

    @Override
    public void exctractData(Workbook excPasp) {
        this.getInfoPart6(excPasp);
    }

}
