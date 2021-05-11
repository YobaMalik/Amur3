package Back.Pasport.Part2;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class CellsCoordinate {
    public int getCoordinate(Workbook excPasp){
        int cIndex = -1;
        Sheet iSheet = null;
        for(int j = 0; j < excPasp.getNumberOfSheets(); ++j) {
            if (excPasp.getSheetName(j).replaceAll(" ","").equals("2")) {
                 iSheet = excPasp.getSheetAt(j);
                break;
            }
        }

        for(int j = 0; j < iSheet.getNumMergedRegions(); ++j) {
            CellRangeAddress mergeadres;
            mergeadres = iSheet.getMergedRegion(j);
            int mergRow = mergeadres.getFirstRow();
            int mergCol = mergeadres.getFirstColumn();
            String adString = iSheet.getRow(mergRow).getCell(mergCol).toString().toLowerCase();
            if (adString.contains("наименование")  && adString.contains("предприятия") ) {
                for(int i = mergCol + 1; i < iSheet.getRow(mergRow).getLastCellNum(); ++i) {
                    CellType checkType = iSheet.getRow(mergRow).getCell(i).getCellType();
                    if (checkType != CellType.BLANK) {
                        cIndex = i;
                        break;
                    }
                }
            }
        }
        return cIndex;
    }
}
