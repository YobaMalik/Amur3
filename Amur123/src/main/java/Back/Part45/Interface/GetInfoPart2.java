package Back.Part45.Interface;

import org.apache.poi.ss.usermodel.*;

import java.util.Iterator;

public interface GetInfoPart2 {
    default String GetPressTemp(Workbook wb, int inVal) {
        String rVal = null;
        int pressFirstRow = -1;
        int tempFirstRow = -1;
        int tempRowNumb = -1;
        int tempColNumb = -1;
        int desTempRow = -1;
        int desPressRow = -1;

        Sheet iSheet = wb.getSheet("2");
//	ArrayList<CellRangeAddress> lst= (ArrayList<CellRangeAddress>) iSheet.getMergedRegions();
        //lst.get(0).isInRange(cell)
        Iterator<Row> row = iSheet.rowIterator();
        while(row.hasNext()) {
            Row r = row.next();
            Iterator<Cell> cell = r.cellIterator();
            while(cell.hasNext()) {
                Cell c = cell.next();
                String sValue = c.toString();
                if (sValue.toLowerCase().contains("давлен") && sValue.toLowerCase().contains("мпа") &&
                        !sValue.toLowerCase().contains("пробн")) {
                    pressFirstRow = c.getRowIndex();
                }
                if (sValue.toLowerCase().contains("температ") && !sValue.toLowerCase().contains("отрицательн")) {
                    tempFirstRow = c.getRowIndex();
                }
            }
        }

        if(pressFirstRow != -1 && tempFirstRow != -1) {
            for (int i = 0; i < iSheet.getRow(pressFirstRow).getLastCellNum(); i++) {
                if(iSheet.getRow(pressFirstRow).getCell(i) != null) {
                    Cell c = iSheet.getRow(pressFirstRow).getCell(i);
                    CellType cType = iSheet.getRow(pressFirstRow).getCell(i).getCellType();
                    String value = iSheet.getRow(pressFirstRow).getCell(i).toString();
                    if(cType != CellType.BLANK && value.contains("рабоч")) {
                        tempRowNumb = c.getRowIndex();
                        tempColNumb = c.getColumnIndex();
                        break;
                    }
                }
            }
        }

        for (int i = tempRowNumb; i < iSheet.getLastRowNum(); i++) {
            if(iSheet.getRow(i) != null && iSheet.getRow(i).getCell(tempColNumb) != null) {
                Cell cell = iSheet.getRow(i).getCell(tempColNumb);
                if (cell.toString().toLowerCase().contains("расчет")) {
                    if(cell.getRowIndex() >= pressFirstRow && cell.getRowIndex() < tempFirstRow) {
                        desPressRow = cell.getRowIndex();
                    }
                    if(cell.getRowIndex() > pressFirstRow && cell.getRowIndex() > tempFirstRow) {
                        desTempRow = cell.getRowIndex();
                    }
                }

            }
        }

        for(int i = tempColNumb+1; i < iSheet.getRow(desPressRow).getLastCellNum(); i++) {
            Cell cell = iSheet.getRow(desPressRow).getCell(i);
            CellType cType = cell.getCellType();
            if(cType != CellType.BLANK) {
                rVal = inVal == 0 ? cell.toString() :
                        iSheet.getRow(desTempRow).getCell(i).toString();
            }
        }

        return rVal;
    }
}
