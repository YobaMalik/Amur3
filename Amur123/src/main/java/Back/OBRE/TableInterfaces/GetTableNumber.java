package Back.OBRE.TableInterfaces;

import org.apache.poi.xwpf.usermodel.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public interface GetTableNumber {
    default XWPFTable getTableIter(XWPFDocument testdoc, int part) {
        XWPFTable sTable = null;
        Iterator<XWPFTable> iter = testdoc.getTablesIterator();

        Set<XWPFTableCell> set = new TreeSet<>();
        while (iter.hasNext()) {

                XWPFTable newTable = iter.next();
                List<XWPFTableRow> row = newTable.getRows();
                for (int i = 0; i < row.size(); i++) {
                    List<XWPFTableCell> cellIter = newTable.getRow(i).getTableCells();
                    for (int j = 0; j < cellIter.size(); j++) {
                        try {
                            String value = this.getText(newTable.getRow(i).getCell(j));
                            if(sTable==null && this.chechValue(value,part)) sTable = newTable;


                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(newTable.getRow(i)==null);
                        }
                    }
                }

        }

        return sTable;
    }
    private String getText(XWPFTableCell cell){
        StringBuilder out = new StringBuilder();
        for(XWPFParagraph xwpfParagraph:cell.getParagraphs()){
            for (IRunElement run : xwpfParagraph.getRuns()) {
                if (run instanceof XWPFRun) {
                    XWPFRun xRun = (XWPFRun) run;
                    // don't include the text if reviewing is enabled and this is a deleted run
                    if (xRun.getCTR().getDelTextArray().length == 0) {
                        out.append(xRun);
                    }
                } else if (run instanceof XWPFSDT) {
                    out.append(((XWPFSDT) run).getContent().getText());
                } else {
                    out.append(run);
                }
            }
        }

        return out.toString();
    }

    private boolean chechValue(String value,int part){
        boolean b = false;
        if (value.toLowerCase().contains("наимен") &&
                value.toLowerCase().contains("элемент") && part == 51) {
            b = true;
        }
    /*    if (value.toLowerCase().contains("цех") &&
                value.toLowerCase().contains("установка") && part == 2) {
            b = true;
        }*/
        return b;
    }
 }



/*
  if (newTable.getRow(i).getCell(j).getText().toLowerCase().contains("цех") &&
                                    newTable.getRow(i).getCell(j).getText().toLowerCase().contains("установка") && part == 2) {
                                sTable = newTable;
                            }

                            if (newTable.getRow(i).getCell(j).getText().toLowerCase().contains("наимен") &&
                                    newTable.getRow(i).getCell(j).getText().toLowerCase().contains("элемент") && part == 51) {
                                sTable = newTable;
                            }
 */

