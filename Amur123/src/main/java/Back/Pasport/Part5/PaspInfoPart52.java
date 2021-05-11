package Back.Pasport.Part5;


import Back.Pasport.Part5.Element.Elements;
import org.apache.poi.ss.usermodel.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PaspInfoPart52 extends AbstractPart5 {


    private int nameElem;
    private int idCode;
    private int eThikness;
    private int flangeSteel;
    private int flangeGost;
    private int boltSteel;
    private int boltGost;
    private int gost;
    private int linePos;
    private int rowIndex;
    private int desPress;

    private String[] paspPart;

    public PaspInfoPart52(Workbook wb) {
        super(wb);
    }

    private boolean PasreCellValue(Cell cell) {
        try {
            cell.getStringCellValue();
            return true;
        } catch(IllegalStateException e) {
            return false;
        }

    }
    private int getLinePos(Sheet iSheet,int rowIndex,int colIndex){
        int result = 0;
        for (int i = rowIndex;i<iSheet.getLastRowNum();i++){
            Cell cell = iSheet.getRow(i).getCell(colIndex);
            if(cell.toString().equals("1.0")){
                result = i;
                break;
            }

        }
        return result;
    }

    @Override
    protected void getInfoPart5(String paspPart) {
        int tempFlange = 0;
        int tempBolt = 0;
        Sheet iSheet=this.wb.getSheet(paspPart);
        Iterator<Row> rIter=iSheet.rowIterator();
        while(rIter.hasNext()) {
            Iterator<Cell> cIter=rIter.next().cellIterator();
            while(cIter.hasNext()) {
                Cell cell=cIter.next();

                if(cell != null){
                    String cellValue = cell.toString().toLowerCase().replace("-","");
                    if(cellValue.contains("наименов") && cellValue.contains("name")) {
                        this.nameElem=cell.getColumnIndex();
                    }
                    if(cellValue.replace(" ","").contains("п/п")) {

                        this.rowIndex=cell.getColumnIndex();
                        this.linePos=this.getLinePos(iSheet,rIter.next().getRowNum(),rowIndex);

                    }

                    if(cellValue.contains("номинал") && cellValue.contains("диаметр")) {
                        this.eThikness=cell.getColumnIndex();
                    }
                    if(cellValue.contains("pressur") && cellValue.contains("номинальн")) {
                        this.desPress=cell.getColumnIndex();

                    }
                    if(cellValue.contains("гост") && cellValue.contains("фланц")){
                        this.gost = cell.getColumnIndex();
                    }

                    if(cellValue.equals("6.0") && cell.getRowIndex() == linePos){
                        this.flangeSteel = cell.getColumnIndex();
                    }
                    if(cellValue.equals("7.0") && cell.getRowIndex() == linePos){
                        this.flangeGost = cell.getColumnIndex();
                    }
                    if(cellValue.equals("8.0") && cell.getRowIndex() == linePos){
                        this.boltSteel = cell.getColumnIndex();
                    }
                    if(cellValue.equals("9.0") && cell.getRowIndex() == linePos){
                        this.boltGost = cell.getColumnIndex();
                    }
                }
            }
        }
    }

    @Override
    public List<Elements> fillTable(String... paspPart) throws ParseException {
        List<Elements> resultList =new ArrayList<>();
        this.paspPart=paspPart;
        for(int z=0;z<this.paspPart.length;z++) {
            this.getInfoPart5(this.paspPart[z]);
            Sheet sht = this.wb.getSheet(this.paspPart[z]);
            String tempLine = "";

            for (int i = this.linePos + 1; i < sht.getLastRowNum() + 1; i++) {
                Elements element = new Elements();
                if (sht.getRow(i) != null &&
                        sht.getRow(i).getCell(this.nameElem) != null &&
                        sht.getRow(i).getCell(this.nameElem).getCellType() != CellType.BLANK&&
                        sht.getRow(i).getCell(this.nameElem).toString()!=null)
                {



                    element.setElementName(sht.getRow(i).getCell(this.nameElem).toString());
                    element.setDimension(sht.getRow(i).getCell(this.eThikness).toString());
                    element.setNn(this.convToInt(sht.getRow(i).getCell(this.rowIndex).toString()));
                    element.setDesPress(sht.getRow(i).getCell(this.desPress).toString());
                    element.setIdCode(sht.getRow(i).getCell(this.idCode).toString());
                    element.setGost(sht.getRow(i).getCell(this.gost).toString());
                    element.setGrade(sht.getRow(i).getCell(this.flangeSteel).toString());
                    element.setFGost(sht.getRow(i).getCell(this.flangeGost).toString());
                    element.setAElementGost(sht.getRow(i).getCell(this.boltGost).toString());
                    element.setAElementGrade(sht.getRow(i).getCell(this.boltSteel).toString());
                }
                else {
                    if(	!sht.getRow(i).getCell(this.rowIndex).toString().isEmpty()){
                        tempLine = sht.getRow(i).getCell(this.rowIndex).toString()!=null?
                                sht.getRow(i).getCell(this.rowIndex).toString():"Ошибка";
                    }
                }

                element.setLineNumber(tempLine);
                if(!sht.getRow(i).getCell(this.rowIndex).toString().equals("")&&
                        !sht.getRow(i).getCell(this.nameElem).toString().equals("")) {
                    resultList.add(element);

                }
            }

        }
        return resultList;
    }
}
