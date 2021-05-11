package NDT;

import NDT.ReportObjects.ReportResponsible;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.*;

public class ListScanner {
    @Getter @Setter
    private ReportResponsible report = new ReportResponsible();

    public void scan(Sheet iSheet){

            for(int i = 0;i < iSheet.getLastRowNum(); i++){
                Row row = iSheet.getRow(i);
                if(row!=null){
                    for(int j = 0; j < row.getLastCellNum(); j++){
                        Cell cell = iSheet.getRow(i).getCell(j);
                        if(cell!=null && !cell.getCellType().equals(CellType.BLANK) && cell.toString() != null){
                            String cellValue = cell.toString().toLowerCase();
                            if(cellValue.contains("контрол")&&cellValue.contains("провел")){
                                report.setTestedBy(this.setSurname(row,j));
                                break;
                            }

                            if(cellValue.contains("заключ")&&cellValue.contains("выдал")){
                                report.setConclusionIssued(this.setSurname(row,j));
                                break;
                            }

                            if(cellValue.contains("начальник")&&cellValue.contains("лаборат")){
                                report.setHeadOfLaboratory(this.setSurname(row,j));
                                break;
                            }

                            if(cellValue.contains("заключ")&&cellValue.contains("получил")){
                                report.setConclusionAccept(this.setSurname(row,j));
                                break;
                            }

                            if(cellValue.contains("номер")&&cellValue.contains("линии")){
                                report.setLine(this.setValue(row,j));
                                break;
                            }

                            if(cellValue.contains("номер")&&cellValue.contains("зон")){
                                report.setZone(this.setValue(row,j));
                                break;
                            }
                            if(iSheet.getSheetName().toLowerCase().trim().equals("вик")&&
                                    cellValue.contains("акт")&&cellValue.contains("№")){
                                report.setReportNumber(this.setValue(row,j));
                                break;
                            }
                            if(!iSheet.getSheetName().toLowerCase().trim().equals("вик")&&
                                    cellValue.contains("заключ")&&cellValue.contains("№")){
                                report.setReportNumber(this.setValue(row,j));
                                break;
                            }
                        }
                    }
                }
            }


    }

    private String setSurname(Row row,int start) {
            String surname = "null";
            for (int i = start + 1; i < row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i);
                if (cell != null && !cell.getCellType().equals(CellType.BLANK)) {
                    if (cell.getCellType().equals(CellType.FORMULA)&&!cell.toString().contains("[") &&
                            !cell.toString().contains("]")) {
                        FormulaEvaluator evaluator1 =
                                row.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                        surname = evaluator1.evaluate(cell).getStringValue();
                    } else {
                        surname = cell.getRichStringCellValue().toString();
                    }
                    break;
                }
            }
            return surname.split("/")[0].trim();
        }

    private String setNumber(Row row,int start) {
        String surname = "null";
        for (int i = start + 1; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && !cell.getCellType().equals(CellType.BLANK)) {
                if (cell.getCellType().equals(CellType.FORMULA)&&!cell.toString().contains("[") &&
                        !cell.toString().contains("]")) {
                    FormulaEvaluator evaluator1 =
                            row.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                    surname = evaluator1.evaluate(cell).getStringValue();
                } else {
                    surname = cell.toString();
                }
                break;
            }
        }
        return surname;
    }

    private String setValue(Row row,int start) {
        String surname = "null ";
        for (int i = start + 1; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);

            if (cell != null && !cell.getCellType().equals(CellType.BLANK)) {

                if (cell.getCellType().equals(CellType.FORMULA)) {
                    if(!cell.toString().toLowerCase().contains("xlsm") &&
                     !cell.toString().contains("[") &&
                            !cell.toString().contains("]")) {
                        FormulaEvaluator evaluator1 =
                                row.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                        surname = evaluator1.evaluate(cell).getStringValue();
                        if (surname == null) surname = Double.toString(evaluator1.evaluate(cell).getNumberValue());
                    } else{
                        if(cell.getCellType().equals(CellType.NUMERIC))
                            surname = Double.toString(cell.getNumericCellValue());
                        if(cell.getCellType().equals(CellType.STRING)||
                                cell.getCellType().equals(CellType.FORMULA))
                            surname = cell.getRichStringCellValue().toString();
                    }
                } else {
                    surname = cell.toString();
                }
                break;
            }
        }
        return surname;
    }

}

