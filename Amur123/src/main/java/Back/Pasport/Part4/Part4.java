package Back.Pasport.Part4;


import org.apache.poi.ss.usermodel.*;
import java.util.*;

public class Part4 implements IPart4 {
    private int column;
    private int firstColumn;
    private List<String> dwgList;
    private StringBuilder dwgs = new StringBuilder();
    private Calendar date = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
    private int dateCoordinate;


    private void getDWG(Workbook excPasp) {
        dwgList = new ArrayList<>();
        dwgList.add("Чертеж :");
    Sheet iSheet = excPasp.getSheet("4");
    Iterator<Row> rIter = iSheet.rowIterator();
    while(rIter.hasNext()) {
        Iterator<Cell> cIter = rIter.next().cellIterator();
        while(cIter.hasNext()) {
            Cell cell = cIter.next();

            //	System.out.println("row "+cell.getRowIndex()+ " column "+cell.getColumnIndex());
            if(cell != null &&
                    cell.toString().toLowerCase().contains("газпереработка")){
                this.column = cell.getColumnIndex();
            }

            if(cell != null&&cell.toString().toLowerCase().contains("наименован")&&
                    cell.toString().toLowerCase().contains("организац")){
                this.firstColumn = cell.getColumnIndex();
            }
        }
    }

    for(int i = 0; i < iSheet.getLastRowNum(); i++) {
        Cell cell = null;
        if(iSheet.getRow(i) != null) {
            cell = iSheet.getRow(i).getCell(firstColumn);
        }
     if(cell != null && cell.toString().toLowerCase().contains("номер") &&
             cell.toString().toLowerCase().contains("чертеж")) {
         this.fillDWGsList(this.dwgList,iSheet.getRow(i).getCell(column).toString());
         this.getMultipleDWGs(iSheet,i,column);
     }
        if(cell != null && cell.toString().toLowerCase().contains("дат") &&
                cell.toString().toLowerCase().contains("монтаж")) {
            dateCoordinate = cell.getRowIndex();
            try {
                CellType type = iSheet.getRow(i).getCell(column).getCellType();
                if (type.equals(CellType.NUMERIC)) {
                    Date format = iSheet.getRow(i).getCell(column).getDateCellValue();

                    date.setTime(format);
                }
                else {
                    date = this.setData(iSheet.getRow(i).getCell(column).toString());
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    }

    private void getMultipleDWGs(Sheet sheet, int startRow,int checkColumn) {

        for (int i = startRow+1; i < sheet.getLastRowNum();i++) {

            Cell cell = sheet.getRow(i).getCell(firstColumn);

            if(!cell.getCellType().equals(CellType.BLANK)){
                break;
            }

            Cell tempCell = sheet.getRow(i).getCell(checkColumn);
            if(tempCell != null &&
                    !sheet.getRow(i).getCell(checkColumn).getCellType().equals(CellType.BLANK))
                this.dwgList.add(this.removeDwgRevision(tempCell.toString()));
        }
    }

    private Calendar setData(String value) {
       // System.out.println(value);
      //  Calendar date = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
        if(value.contains("-")) {
            String[] arr = value.replace(" ","").split("-");
            String[] dmy = arr[0].split("\\.");
            date.set(Integer.parseInt(dmy[2]), Integer.parseInt(dmy[1])-1, Integer.parseInt(dmy[0]));
        } else {
            String[] dmy = value.replace(" ","").split("\\.");
            date.set(Integer.parseInt(dmy[2]), Integer.parseInt(dmy[1])-1, Integer.parseInt(dmy[0]));
        }

        return date;
    }

    private String removeDwgRevision(String value){
        String tempDwg = value;
        if(value.toLowerCase().contains("rev")){
            int revPos = value.toLowerCase().indexOf("rev");
            tempDwg = value.substring(0,revPos-1).trim();
        }

        if(value.toLowerCase().contains("_er")){
            int revPos = value.toLowerCase().indexOf("_er");
            tempDwg = value.substring(0,revPos-3).trim();
        }
        return tempDwg;
    }

    private void fillDWGsList(List<String> dwgList,String result) {
        Arrays.stream(result.replace(";", ",")
                .replace(" , ", ",")
                .replace(",\n", ",")
                .replace("\n", ",").split(",")).forEach(e->{
                    dwgList.add(this.removeDwgRevision(e.replace("\n","")));
        });
    }

    @Override
    public List<String> getDWGsList(){
        return this.dwgList;
    }

    @Override
    public void setDWGsList() {
    }

    @Override
    public void exctractData(Workbook excPasp) {
        this.getDWG(excPasp);
    }

    @Override
    public Calendar getDate() {
        return date;
    }

    @Override
    public void setDate(Calendar date) {

    }

}