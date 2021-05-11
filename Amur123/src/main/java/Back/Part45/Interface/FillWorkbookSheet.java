package Back.Part45.Interface;


import Back.Interface.IRows;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface FillWorkbookSheet {

    default List<String> GetDiamAndThik(String outDiam) {
        List<String> elemSize = new ArrayList<>();
        outDiam = outDiam.toLowerCase().replace(" ", "");
        outDiam = outDiam.replace("х", "x");
        outDiam = outDiam.replace("/n","");
        outDiam = outDiam.replace("/","-");
        String[] splitDD = outDiam.split("-");
        for (String s : splitDD) {
            String[] arr = s.split("x");
            elemSize.addAll(Arrays.asList(arr));
        }
        return elemSize;
    }

    default double getReducerType(String elemName) {
        return elemName.toLowerCase().contains("конц") ? 2.0 : 1.0;
    }

    default double getRejThick(String oDiam) {
        double rejThick=0;
        int outDiam = (int) Double.parseDouble(oDiam);
        if(outDiam <= 25.0) rejThick = 1.0;
        if(outDiam > 25.0 && outDiam <= 57.0) rejThick = 1.5;
        if(outDiam > 57.0 && outDiam <= 114.0) rejThick = 2.0;
        if(outDiam > 114.0 && outDiam <= 219.0) rejThick = 2.5;
        if(outDiam > 219.0 && outDiam <= 325.0) rejThick = 3.0;
        if(outDiam > 325.0 && outDiam <= 377.0) rejThick = 3.5;
        if(outDiam > 377.0 && outDiam <= 426.0) rejThick = 4.0;
        if(outDiam > 426.0) rejThick = 4.0;
        return rejThick;
    }

    default void FillSheet(Sheet iSheet, List<IRows<String>> resultList, String[] head, CellStyle style)  {

        int cellIndex = iSheet.getSheetName().compareTo("отводы") == 0 ||
                iSheet.getSheetName().compareTo("трубы") == 0 ? 9 : 11;
        int lastFilledCell = Integer.parseInt(head[head.length-1]);
        Row fRow = iSheet.createRow(0);

        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        for(int i = 0; i < head.length-1; i++) {
            fRow.createCell(i).setCellValue(head[i]);
            if(i <= lastFilledCell) fRow.getCell(i).setCellStyle(style);
        }


        for (int i = 0; i < resultList.size(); i++) {
            Row row = iSheet.createRow(i+1);
            String outD = null;
            String outD1 = null;
            for(int j = 0; j < resultList.get(i).getSize(); j++) {
                row.createCell(j).setCellValue(resultList.get(i).getValue(j));
            }

            String outDiam = resultList.get(i).getValue(2).replace("х","x");
            if (outDiam.toLowerCase().contains("x")) {
                List<String> splitList = this.GetDiamAndThik(outDiam);

                for (int j = 0; j < splitList.size(); j++) {
                    row.createCell(j+7).setCellValue(splitList.get(j));
                    outD = splitList.get(0);
                    outD1 = splitList.size() > 2 ? splitList.get(2) : null;
                }
                if(outD1 != null && this.checkParse(outD)) {
                    row.createCell(cellIndex).setCellValue(this.getRejThick(outD.replace(",", ".")));
                    row.createCell(12).setCellValue(this.getRejThick(outD1.replace(",", ".")));
                    if (row.getCell(1).toString().toLowerCase().contains("перех") &&
                            !row.getCell(1).toString().toLowerCase().contains("тройн")) {
                        row.createCell(14).setCellValue(this.getReducerType(row.getCell(1).toString()));
                    }
                }
            }


        }
    }

    default  boolean checkParse(String size) {
        try {
            Double.parseDouble(size);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
}