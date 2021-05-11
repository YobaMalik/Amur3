package Back.Pasport.Part5;

import Back.Pasport.Part5.Element.Elements;
import org.apache.poi.ss.usermodel.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PaspInfoPart53 extends AbstractPart5 {

    private int nameElem;
    private int idCode;
    private int eThikness;
    private int eSteel;
    private int eGost;
    private int linePos;
    private int rowIndex;
    private int desPress;

    private String[] paspPart;

    public PaspInfoPart53(Workbook wb) {
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


    @Override
    protected void getInfoPart5(String paspPart) {
        Sheet iSheet=this.wb.getSheet(paspPart);
        Iterator<Row> rIter=iSheet.rowIterator();
        while(rIter.hasNext()) {
            Iterator<Cell> cIter=rIter.next().cellIterator();
            while(cIter.hasNext()) {
                Cell cell=cIter.next();

                //	System.out.println("row "+cell.getRowIndex()+ " column "+cell.getColumnIndex());
                if(cell!=null&&cell.toString().toLowerCase().contains("наименов")&&
                        cell.getStringCellValue().toLowerCase().contains("name")) {
                    this.nameElem=cell.getColumnIndex();
                }
                if(cell!=null&&
                        cell.toString().toLowerCase().replace(" ","").contains("п/п")) {
                    this.linePos=cell.getRowIndex()+1;
                    this.rowIndex=cell.getColumnIndex();

                }

                if(cell!=null&& cell.toString().toLowerCase().contains("номинал") &&
                        cell.toString().toLowerCase().contains("диаметр")) {
                    this.eThikness=cell.getColumnIndex();
                }

                if(cell!=null&&cell.toString().toLowerCase().contains("марк")
                        &&cell.getStringCellValue().toLowerCase().contains("материал")) {
                    this.eSteel=cell.getColumnIndex();
                }

                if(cell!=null&&cell.toString().toLowerCase().contains("гост")&&
                        cell.getStringCellValue().toLowerCase().contains("ту")) {
                    this.eGost=cell.getColumnIndex();
                }

                if(cell!=null&& cell.toString().toLowerCase().contains("pressure") &&
                        cell.getStringCellValue().toLowerCase().contains("давлен")) {
                    this.desPress=cell.getColumnIndex();
                }
                if(cell!=null&& cell.toString().toLowerCase().contains("обознач") &&
                        cell.getStringCellValue().toLowerCase().contains("каталог")) {
                    this.idCode=cell.getColumnIndex();
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
                    element.setGrade(sht.getRow(i).getCell(this.eSteel).toString());
                    element.setGost(sht.getRow(i).getCell(this.eGost).toString());
                    element.setNn(this.convToInt(sht.getRow(i).getCell(this.rowIndex).toString()));
                    element.setDesPress(sht.getRow(i).getCell(this.desPress).toString());
                    element.setIdCode(sht.getRow(i).getCell(this.idCode).toString());

                }
                else {
                    if(	!sht.getRow(i).getCell(this.rowIndex).toString().isEmpty()){
                        tempLine = sht.getRow(i).getCell(this.rowIndex).toString();
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
