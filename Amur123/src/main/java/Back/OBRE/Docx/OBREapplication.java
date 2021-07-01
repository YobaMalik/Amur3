package Back.OBRE.Docx;


import Back.Interface.ConvertString;

import Back.OBRE.TableInterfaces.GetTableNumber;
import Back.Pasport.InterfacePasp.IPasportData;

import Back.Pasport.Part5.Element.Elements;

import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OBREapplication implements GetTableNumber, ConvertString {
    private CTP paragraph;

    private void mergerow(XWPFTableRow emptyR) {
        CTRow testR = emptyR.getCtRow();
        List<CTTc> rList = testR.getTcList();
        for (int i = 0; i < rList.size(); i++) {
            rList.get(i).addNewTcPr();
            CTTcPr TcPr = rList.get(i).getTcPr();
            CTHMerge merg = TcPr.addNewHMerge();
            if (i == 0) {
                merg.setVal(STMerge.RESTART);
            } else {
                merg.setVal(STMerge.CONTINUE);
            }
        }
    }
    public void repalceTextInParagrahps(XWPFDocument wDoc, String token, String value){

        for (XWPFParagraph p : wDoc.getParagraphs()) {
            List<XWPFRun> r = p.getRuns();
            for (XWPFRun e : r) {
                String text = e.getText(0);
                if (text != null && text.contains(token)) {
                    text = text.replaceAll(token, value);
                    e.setText(text, 0);
                }
            }
        }
    }
    public void replaceFooterText(XWPFDocument document, String footerText, IPasportData pasport) throws XmlException {

        for (XWPFHeader f : document.getHeaderList()) {

            for (XWPFParagraph paragraph : f.getParagraphs()) {
                XmlCursor cursor = paragraph.getCTP().newCursor();
                cursor.selectPath("declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//*/w:txbxContent/w:p/w:r");

                List<XmlObject> ctrsintxtbx = new ArrayList<>();

                while (cursor.hasNextSelection()) {
                    cursor.toNextSelection();
                    XmlObject obj = cursor.getObject();
                    ctrsintxtbx.add(obj);
                }

                for (XmlObject obj : ctrsintxtbx) {
                    CTR ctr = CTR.Factory.parse(obj.xmlText());
                    XWPFRun bufferrun = new XWPFRun(ctr, (IRunBody) paragraph);
                    String text = bufferrun.getText(0);

                    if(text!=null&&text.contains("000")) {
                          bufferrun.setText(footerText, 0);
                          obj.set(bufferrun.getCTR());
                    }

                    if(text != null && text.contains("Трубопровод")) {
                        String res = "Трубопровод "+pasport.getPipename();
                        bufferrun.setText( res, 0);
                        bufferrun.addBreak();
                        bufferrun.setText( "ОБОСНОВАНИЕ", 1);
                        bufferrun.addBreak();
                        bufferrun.setText( "БЕЗОПАСНОСТИ", 2);
                        obj.set(bufferrun.getCTR());
                    }

                }
            }

        }



    }

    public void replaceFooterFactory(XWPFDocument document, String footerText) throws XmlException {
        for (XWPFFooter f : document.getFooterList()){
            f.getBodyElements().forEach(e->{
                if (e instanceof XWPFSDT) {
                    XWPFSDT coverpageObj = (XWPFSDT) e;
                    System.out.println(coverpageObj.getContent().getText());
                }
            });
        }

        for (XWPFHeader f : document.getHeaderList()) {

            for (XWPFParagraph paragraph : f.getParagraphs()) {
                XmlCursor cursor = paragraph.getCTP().newCursor();
                cursor.selectPath("declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//*/w:txbxContent/w:p/w:r");

                List<XmlObject> ctrsintxtbx = new ArrayList<>();

                while (cursor.hasNextSelection()) {
                    cursor.toNextSelection();
                    XmlObject obj = cursor.getObject();
                    ctrsintxtbx.add(obj);
                }

                for (XmlObject obj : ctrsintxtbx) {
                    CTR ctr = CTR.Factory.parse(obj.xmlText());
                    //CTR ctr = CTR.Factory.parse(obj.newInputStream());
                    XWPFRun bufferrun = new XWPFRun(ctr, (IRunBody) paragraph);
                    String text = bufferrun.getText(0);
                    if (text != null && text.contains("Газпром переработка Благовещенск")) {
                        bufferrun.getCTR().addNewBr();
                        bufferrun.setText("", 0);
                        bufferrun.setText(footerText, 1);
                        obj.set(bufferrun.getCTR());
                    }
                }
            }

        }
    }
    private void mergecol(XWPFTable table, int firstrow, int lastrow, int col) {

        String val = this.GetParagraphText(table.getRow(firstrow).getCell(col));
        for (int i = firstrow; i < lastrow; i++) {
            CTTc mCell = table.getRow(i).getCell(col).getCTTc();
            if (i == firstrow) {
                this.removeObj(mCell);
                mCell.addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                this.removeObj(mCell);
                mCell.addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }

        }
        CTP Paragraph = table.getRow(firstrow).getCell(col).getCTTc().getPArray(0);
        CTR run = Paragraph.addNewR();
        CTRPr prp = paragraph.getRList().get(0).getRPr();
        run.setRPr(prp);
        CTText text = run.addNewT();
        text.setStringValue(val);
        table.getRow(firstrow).getCell(col).getCTTc().getPArray(0).setPPr(paragraph.getPPr());
    }

    public String GetParagraphText(XWPFTableCell cell) {
        String sValue = null;
        CTP Paragraph = cell.getCTTc().getPArray(0);
        if (Paragraph.getRList().size() > 0 && Paragraph.getRArray(0).getTList().size() > 0) {
            sValue = Paragraph.getRArray(0).getTArray(0).getStringValue();
        }
        return sValue;
    }

    public void MrgClTbl(XWPFTable iTable, int fRow, int... args) {
        int rowsize = iTable.getRows().size();
        String cellV = this.GetParagraphText(iTable.getRow(fRow).getCell(args[0]));
        int z = fRow;
        for (int i = fRow; i < rowsize; i++) {
            String aStr = this.GetParagraphText(iTable.getRow(i).getCell(args[0]));
            if (cellV != null && aStr != null && cellV.compareTo(aStr) != 0 &&
                    iTable.getRow(i).getTableCells().size() > 1) {
                cellV = aStr;
                if ((i - z > 1)) {
                    for (int arg : args) {
                        this.mergecol(iTable, z, i, arg);
                    }
                }
                z = i;
            }
            if (cellV == null) {
                cellV = aStr;
            }

        }
    }

    private void removeObj(XWPFTable iTable1, int rowC, int cellC) {
        XWPFTableCell tCell = iTable1.getRow(rowC).getCell(cellC);
        CTTc rCell = tCell.getCTTc();
        rCell.setPArray(new CTP[]{CTP.Factory.newInstance()});

    }

    private void removeObj(XWPFTableRow row, int cellC) {
        XWPFTableCell tCell = row.getCell(cellC);
        CTTc rCell = tCell.getCTTc();
        rCell.setPArray(new CTP[]{CTP.Factory.newInstance()});
    }

    private void removeObj(CTTc rCell) {
        rCell.setPArray(new CTP[]{CTP.Factory.newInstance()});
    }

    public void fTablle2(XWPFTable iTable1, IPasportData z) {
        this.setProperty(iTable1);
        this.setValue(iTable1,0, 1, z.getFactoryName());
        this.setValue(iTable1,1, 1, z.getPipename());
        this.setValue(iTable1,2, 1, z.getPipePurpose());
        this.setValue(iTable1,3, 1, z.getFluidCode());
        this.setValue(iTable1,4, 2, z.getHazardcode().replace(".0",""));
        this.setValue(iTable1,5, 2, z.getExpHazard());
        this.setValue(iTable1,6, 1, z.getOperationPressure());
        this.setValue(iTable1,7, 1, z.getDesingPressure());
        this.setValue(iTable1,8, 1,  z.getDesignTemp().replace(".0",""));
        this.setValue(iTable1,9, 1, z.getKatGOST());
        this.setValue(iTable1,10, 2,  z.getTestPressHydro());
        this.setValue(iTable1,11, 2,  z.getTestPressPnevmo());
        this.setValue(iTable1,12, 1, z.getMinTemp());
        this.setValue(iTable1,13, 1, z.getBillP().replace(".0",""));
    }

    protected void setProperty(XWPFTable iTable1){
        paragraph = (CTP) iTable1.getRow(0).getCell(1).getCTTc().getPArray(0).copy();
    }

    private void setValue(XWPFTable iTable1,int row, int col, String val) {
        CTRPr prp = paragraph.getRList().get(0).getRPr();
        this.removeObj(iTable1,row,col);
        CTR run = iTable1.getRow(row).getCell(col).getCTTc().getPArray(0).addNewR();
        run.addNewRPr();
        run.setRPr(prp);
        iTable1.getRow(row).getCell(col).getCTTc().getPArray(0).setPPr(paragraph.getPPr());
        CTText text = run.addNewT();
        text.setStringValue(val);
     }

    private void setValue( XWPFTableRow row, int col, String val) {
        CTRPr prp = paragraph.getRList().get(0).getRPr();
        this.removeObj(row,col);
        CTR run = row.getCell(col).getCTTc().getPArray(0).addNewR();
        run.addNewRPr();
        run.setRPr(prp);
        row.getCell(col).getCTTc().getPArray(0).setPPr(paragraph.getPPr());
        CTText text = run.addNewT();
        text.setStringValue(val);
        row.getCell(col).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
    }

    private void setValue5OB( XWPFTableRow row, int col, String val) {
        CTRPr prp = row.getCell(0).getCTTc().getPArray(0).getRList().get(0).getRPr();
        this.removeObj(row,col);
        CTR run = row.getCell(col).getCTTc().getPArray(0).addNewR();
        run.addNewRPr();
        run.setRPr(prp);
        row.getCell(col).getCTTc().getPArray(0).setPPr(row.getCell(0).getCTTc().getPArray(0).getPPr());
        row.getCell(col).getCTTc().getPArray(0).getRList().get(0).
                addNewRPr().addNewHighlight().setVal(STHighlightColor.YELLOW);
        CTText text = run.addNewT();
        text.setStringValue(val);
    }

    private void setValue5OB( XWPFTableRow row, int col, String delimeter, String ... val) {
        CTRPr prp = row.getCell(0).getCTTc().getPArray(0).getRList().get(0).getRPr();
        this.removeObj(row,col);
        row.getCell(col).getCTTc().getPArray(0).setPPr(row.getCell(0).getCTTc().getPArray(0).getPPr());

        for(int i=0 ; i < val.length ; i++){
            CTR run = row.getCell(col).getCTTc().getPArray(0).addNewR();
            if(i!=0) run.addNewBr();
            run.addNewRPr();
            run.setRPr(prp);
            row.getCell(col).getCTTc().getPArray(0).getRList().get(i).
                    addNewRPr().addNewHighlight().setVal(STHighlightColor.YELLOW);
            CTText text = run.addNewT();
            if(i!=val.length-1){
                text.setStringValue((val[i]+delimeter).replace(":,",""));
            } else{
                text.setStringValue(val[i]);
            }
        }
    }

    public void createBorder(XWPFTable iTable) {
        iTable.setBottomBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
        iTable.setTopBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
        iTable.setLeftBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
        iTable.setRightBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
        iTable.setInsideHBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
        iTable.setInsideVBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
    }

    public void fillTable51(XWPFTable iTable1, IPasportData pasportData) {
        if (iTable1 != null) {
            String line = null;
            for(Elements e:pasportData.getIRowsList()){

                if(!e.getLineNumber().equals(line)){
                    XWPFTableRow row1 = iTable1.createRow();
                    this.mergerow(row1);
                    this.setValue(row1,0,e.getLineNumber());
                    line = e.getLineNumber();
                }

                XWPFTableRow row = iTable1.createRow();
                this.setValue(row,0,e.getNn().toString());
                this.setValue(row,1,e.getElementName());
                this.setValue(row,2,e.getDimension());
                this.setValue(row,3,e.getGrade());
                this.setValue(row,4,e.getGost());

            }

             }
    }

    public void fillTable53(XWPFTable iTable1, IPasportData pasportData) {

        if (iTable1!=null) {

            //    this.mergerow(row);
            String line = null;

            for(Elements e:pasportData.getPart53List()){

                if(!e.getLineNumber().equals(line)){
                    XWPFTableRow row1 = iTable1.createRow();
                    this.mergerow(row1);
                    this.setValue(row1,0,e.getLineNumber());
                    line = e.getLineNumber();
                }
                XWPFTableRow row = iTable1.createRow();
                try {
                    this.setValue(row, 0, e.getNn().toString());
                    this.setValue(row, 1, e.getElementName());
                    this.setValue(row, 2, e.getIdCode());
                    this.setValue(row, 3, e.getDimension().replace(".0",""));
                    this.setValue(row, 4, e.getDesPress());
                    this.setValue(row, 5, e.getGrade());
                    this.setValue(row, 6, e.getGost());
                }catch (Exception ze){
                    ze.printStackTrace();
                }

            }

        }
    }

    public void fillTable5OB(XWPFTable iTable1, IPasportData pasportData) {

        if (iTable1!=null) {
            String test;

            if(pasportData.getFileName().toLowerCase().contains("рев")){

                test = pasportData.getFileName().toLowerCase().split("рев")[0].
                        replaceAll("паспорт","").replaceAll(" ","")
                        .replaceAll("№","").replaceAll(".xlsx","");
            } else {
                test = pasportData.getFileName().toLowerCase()
                        .replaceAll("паспорт", "").replaceAll(" ", "")
                        .replaceAll("№","").replaceAll(".xlsx","");
            }

                DataBaseObject datas = pasportData.getResultMapOBRE().get(test);
            this.setValue5OB(iTable1.getRow(2), 3, "Паспорт "+pasportData.getPipename());
         /*   this.setValue5OB(iTable1.getRow(3), 3, "Паспорт "+"\n"+pasportData.getpipename()+
                    ", Акты испытаний "+"\n"+datas.getAip());
          */

            this.setValue5OB(iTable1.getRow(3), 3,
                    "", "Паспорт "+pasportData.getPipename()+", ",
                    "Акты испытаний ",datas.getAip());
            this.setValue5OB(iTable1.getRow(4), 3, "Паспорт "+pasportData.getPipename());
            this.setValue5OB(iTable1.getRow(5), 3,
                    "","Руководство по эксплуатации ",datas.getReNumber());

            this.setValue5OB(iTable1.getRow(6), 3,",", pasportData.getDWGs().toArray(String[]::new));
        }
    }

    public void fillTitulTable(XWPFTable iTable1, IPasportData pasportData) {
        if (iTable1 != null) {
            Calendar calendar = pasportData.getDate();
            Date prodDate = calendar.getTime();
            String tempData = new SimpleDateFormat("dd-MMMM-yyyy").format(prodDate);
            String[] arr = tempData.split("-");
            String date = "« " + arr[0]
                    + " » " + arr[1] +
                    " " + arr[2] +" г.";
            iTable1.getRow(1).getCell(1).setText(date);
            iTable1.getRow(1).getCell(1)
                    .getCTTc().getPArray(0)
                    .getRList().get(0).addNewRPr().addNewHighlight().setVal(STHighlightColor.YELLOW);
        }
    }


    public void fillTable52(XWPFTable iTable1, IPasportData pasportData) {
        if (iTable1!=null) {
            String line = null;

            for(Elements e:pasportData.getPart52List()){

                if(!e.getLineNumber().equals(line)){
                    XWPFTableRow row1 = iTable1.createRow();
                    row1.createCell();
                    row1.createCell();
                    this.mergerow(row1);
                    this.setValue(row1,0,e.getLineNumber());
                    line = e.getLineNumber();
                }

                XWPFTableRow row = iTable1.createRow();
                row.createCell();
                row.createCell();

                try {
                    this.setValue(row, 0, e.getNn().toString());
                    this.setValue(row, 1, e.getElementName());
                    this.setValue(row, 2, e.getGost());
                    this.setValue(row, 3, e.getDimension().replace(".0",""));
                    this.setValue(row, 4, e.getDesPress());
                    this.setValue(row,6,e.getFGost());
                    this.setValue(row,5,e.getGrade());
                    this.setValue(row,8,e.getAElementGost());
                    this.setValue(row,7,e.getAElementGrade());
                }catch (Exception ze){
                    ze.printStackTrace();
                }

            }

        }
    }
    public void fillTable3(XWPFTable iTable1, IPasportData pasportData) throws ParseException {
        int temp = 3;
        int count = 3;

        if (iTable1!=null) {

            String tempLine = pasportData.getPart3List().get(0).getLineNumber();
            String line;
            for(Elements e:pasportData.getPart3List()){
                XWPFTableRow row = iTable1.createRow();
                line = e.getLineNumber();
                this.setValue(row, 0, e.getNn().toString());
                this.setValue(row, 1, e.getLineNumber());
                this.setValue(row, 2, e.getElementName());
                String format = this.stringToDouble(e.getDimension())?
                        String.format("%.2f", this.conv(e.getDimension().replace(".",","))):
                        e.getDimension();
                this.setValue(row, 3, format);

                if(!tempLine.equals(e.getLineNumber())){
                    this.mergecol(iTable1,temp,count,1);
                    this.mergecol(iTable1,temp,count,0);
                    tempLine=line;
                temp = count;
                }

                if(count-2==pasportData.getPart3List().size()){
                    this.mergecol(iTable1,temp,count+1,1);
                    this.mergecol(iTable1,temp,count+1,0);
                }
                count++;
            }
        }
    }


    private void copyTable(XWPFTable source, XWPFTable target) {

        if (source.getCTTbl() != null && source.getCTTbl().getTblPr() != null && source.getCTTbl().getTblGrid() != null) {
            target.getCTTbl().setTblPr(source.getCTTbl().getTblPr());
            target.getCTTbl().setTblGrid(source.getCTTbl().getTblGrid());
        }
        for (int r = 0; r < source.getRows().size(); r++) {
            XWPFTableRow targetRow = target.createRow();
            XWPFTableRow row = source.getRows().get(r);
            targetRow.getCtRow().setTrPr(row.getCtRow().getTrPr());
            for (int c = 0; c < row.getTableCells().size(); c++) {
                //newly created row has 1 cell
                if (targetRow.getTableCells().size() > 0) {
                    XWPFTableCell targetCell = c == 0 ? targetRow.getTableCells().get(0) : targetRow.createCell();
                    XWPFTableCell cell = row.getTableCells().get(c);
                    targetCell.getCTTc().setTcPr(cell.getCTTc().getTcPr());
                    XmlCursor cursor = targetCell.getParagraphArray(0).getCTP().newCursor();
                    for (int p = 0; p < cell.getBodyElements().size(); p++) {
                        IBodyElement elem = cell.getBodyElements().get(p);
                        if (elem instanceof XWPFParagraph) {
                            XWPFParagraph targetPar = targetCell.insertNewParagraph(cursor);
                            cursor.toNextToken();
                            XWPFParagraph par = (XWPFParagraph) elem;
                            copyParagraph(par, targetPar);
                        } else if (elem instanceof XWPFTable) {
                            XWPFTable targetTable = targetCell.insertNewTbl(cursor);
                            XWPFTable table = (XWPFTable) elem;
                            copyTable(table, targetTable);
                            cursor.toNextToken();
                        }
                    }
                    targetCell.removeParagraph(targetCell.getParagraphs().size() - 1);
                }
            }
        }

        target.removeRow(0);
    }

    private void copyParagraph(XWPFParagraph source, XWPFParagraph target) {
        if (source.getCTP() == null && source.getCTP().getPPr() != null) {
            target.getCTP().setPPr(source.getCTP().getPPr());
        }
        for (int i = 0; i < source.getRuns().size(); i++) {
            XWPFRun run = source.getRuns().get(i);
            XWPFRun targetRun = target.createRun();
            targetRun.getCTR().setRPr(run.getCTR().getRPr());
            targetRun.setText(run.getText(0));
        }
    }
}