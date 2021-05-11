package Back.Pasport.Part3;


import Back.Interface.ConvertString;
import Back.Pasport.Part5.Element.Elements;
import Back.Pasport.RowfTable;
import org.apache.poi.ss.usermodel.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Part3 implements IPart3, ConvertString {
    private Sheet sht;
    private int diams;
    private int linesANDzone;
    private int lineLength;
    private int fisrCol;
    private final List<RowfTable<String>> resLinePart3 = new ArrayList<>();
    private List<Elements> elementsPart3;
    private int firstLineIndex;


    private void getCoordinate(Workbook excPasp){
        Sheet iSheet = excPasp.getSheet("3");
        Iterator<Row> rIter = iSheet.rowIterator();
        while(rIter.hasNext()) {
            Iterator<Cell> cIter = rIter.next().cellIterator();
            while(cIter.hasNext()) {
                Cell cell = cIter.next();

                //	System.out.println("row "+cell.getRowIndex()+ " column "+cell.getColumnIndex());
                if(cell != null && cell.toString().toLowerCase().contains("наименов") &&
                        cell.getStringCellValue().toLowerCase().contains("участка")) {
                    this.linesANDzone = cell.getColumnIndex();
                    this.firstLineIndex = cell.getRowIndex() + 1;
                }

                if(cell != null && cell.toString().toLowerCase().contains("наружн") &&
                        cell.toString().toLowerCase().contains("диаметр")) {
                    this.diams = cell.getColumnIndex();
                }
                if(cell != null && cell.toString().toLowerCase().contains("участк") &&
                        cell.toString().toLowerCase().contains("трубопровод")) {
                    this.lineLength = cell.getColumnIndex();
                }

                if(cell != null && cell.toString().toLowerCase().contains("№")){
                    this.fisrCol = cell.getColumnIndex();
                }

            }
        }
    }

    private void getInfoPart3(Workbook excPasp){
        this.getCoordinate(excPasp);
            List<Elements> resultList = new ArrayList<>();
            sht = excPasp.getSheet("3");
                String tempLine = null;
                String pos = null;
                for (int i = this.firstLineIndex + 1; i < sht.getLastRowNum() + 1; i++) {
                    Elements element51 = new Elements();

                    if(sht.getRow(i) != null &&
                            sht.getRow(i).getCell(this.diams) != null &&
                            sht.getRow(i).getCell(this.diams).getCellType() != CellType.BLANK) {
                        element51.setElementName(sht.getRow(i).getCell(this.diams).toString());
                        element51.setDimension(sht.getRow(i).getCell(this.lineLength).toString());
                    }

                    if(sht.getRow(i) != null && sht.getRow(i).getCell(this.linesANDzone) != null &&
                            !sht.getRow(i).getCell(this.linesANDzone).toString().isEmpty() &&
                            !sht.getRow(i).getCell(this.linesANDzone).toString().equals("")
                    ){
                        tempLine = sht.getRow(i).getCell(this.linesANDzone).toString();
                        if(!sht.getRow(i).getCell(this.fisrCol).toString().equals("")){
                            pos = sht.getRow(i).getCell(this.fisrCol).toString();
                        }

                    }
                    element51.setLineNumber(tempLine);
                    element51.setNn(Integer.parseInt(pos.replace(".0","")));
                    if(sht.getRow(i) != null && sht.getRow(i).getCell(this.diams) != null &&
                            sht.getRow(i).getCell(this.lineLength) != null &&
                            !sht.getRow(i).getCell(this.diams).toString().equals("") &&
                            !sht.getRow(i).getCell(this.lineLength).toString().equals("")){
                        resultList.add(element51);
                    }
                    }
        elementsPart3 = resultList;
    }


    @Override
    public void exctractData(Workbook excPasp) {
        this.getInfoPart3(excPasp);
    }

    @Override
    public List<RowfTable<String>> getResLinePart3() {
        return resLinePart3;
    }

    @Override
    public void setResLinePart3(List<Elements> resLinePart3) {

    }

    @Override
    public List<Elements> getElementsPart3() {
        return elementsPart3;
    }

    @Override
    public void setElementsPart3(List<Elements> elementsPart3) {
        for (int i = this.firstLineIndex; i < this.firstLineIndex + elementsPart3.size(); i++){
            Row row = sht.getRow(i) == null ? sht.createRow(i): sht.getRow(i);
            Elements element = elementsPart3.get(i);
        }
    }
}
