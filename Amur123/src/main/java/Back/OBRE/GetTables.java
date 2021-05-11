package Back.OBRE;

import org.apache.poi.xwpf.usermodel.*;

import java.util.Iterator;
import java.util.List;

public class GetTables {
    private XWPFTable tablePart2;
    private XWPFTable tablePart51;
    private XWPFTable tablepart53;
    private XWPFTable tablepart52;
    private XWPFTable tablepart3;
    private XWPFTable table5OB;
    private XWPFTable titul;

    public GetTables(XWPFDocument testdoc){
        this.getTableIter(testdoc);
    }

    public void getTableIter(XWPFDocument testdoc) {
        Iterator<XWPFTable> iter = testdoc.getTablesIterator();

        while (iter.hasNext()) {

            XWPFTable newTable = iter.next();
            List<XWPFTableRow> row = newTable.getRows();
            for (int i = 0; i < row.size(); i++) {
                List<XWPFTableCell> cellIter = newTable.getRow(i).getTableCells();
                for (int j = 0; j < cellIter.size(); j++) {
                    try {
                        String value = this.getText(newTable.getRow(i).getCell(j));

                        if (value.toLowerCase().contains("наимен") &&
                                value.toLowerCase().contains("элемент") ) {
                          this.tablePart51 = newTable;
                        }

                        if (value.toLowerCase().contains("цех") &&
                                value.toLowerCase().contains("установка") ) {
                            this.tablePart2 = newTable;
                        }

                        if (value.toLowerCase().contains("обозначение") &&
                                value.toLowerCase().contains("каталог") ) {
                            this.tablepart53 = newTable;
                        }

                        if (value.toLowerCase().contains("фланца") &&
                                value.toLowerCase().contains("детали") ) {
                            this.tablepart52 = newTable;
                        }

                        if (value.toLowerCase().contains("наименов") &&
                                value.toLowerCase().contains("участк")) {
                            this.tablepart3 = newTable;
                        }
                        if (value.toLowerCase().contains("сведения") &&
                                value.toLowerCase().contains("2013") ) {
                            this.table5OB = newTable;
                        }

                        if (value.toLowerCase().contains("утверждаю") ) {
                            this.titul = newTable;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(newTable.getRow(i) == null);
                    }
                }
            }

        }

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

    public XWPFTable getTablePart51() {
        return tablePart51;
    }

    public XWPFTable getTablePart2() {
        return tablePart2;
    }

    public XWPFTable getTablepart53() {
        return tablepart53;
    }

    public XWPFTable getTablepart52() {
        return tablepart52;
    }

    public XWPFTable getTablepart3() {
        return this.tablepart3;
    }

    public XWPFTable getTable5OB() {
        return table5OB;
    }

    public XWPFTable getTitul() {
        return titul;
    }
}
