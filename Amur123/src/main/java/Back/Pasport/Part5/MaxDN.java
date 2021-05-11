package Back.Pasport.Part5;


import Back.Pasport.InterfacePasp.IAddMethods;
import Back.Pasport.InterfacePasp.IExtractData;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.HashMap;
import java.util.Map;

public class MaxDN implements IAddMethods, IExtractData {

    private double MaXDN;
    private HashMap<String, String> pipeMaterial=new HashMap<>();
    public HashMap<String, String> getPipeMaterial(){
        return this.pipeMaterial;
    }
    public double getMaXDN() {
        return MaXDN;
    }

    private void getPipeInfo(Workbook excPasp)  {

        Sheet iSheet = null;


        for(int diam = 0; diam < excPasp.getNumberOfSheets(); ++diam) {
            if (excPasp.getSheetName(diam).equals("5.1")) {
                iSheet = excPasp.getSheetAt(diam);
                break;
            }
        }

        if (iSheet != null) {
            int diam = 0;
            int material = 0;
            int firstRow = 0;

            for(int i = 0; i < iSheet.getNumMergedRegions(); ++i) {
                CellRangeAddress mergeadres = iSheet.getMergedRegion(i);
                int mergRow = mergeadres.getFirstRow();
                int mergCol = mergeadres.getFirstColumn();
                String adString = iSheet.getRow(mergRow).getCell(mergCol).toString().toLowerCase();
                if (adString.contains("диаметр")  && adString.contains("толщина") ) {
                    diam = mergeadres.getFirstColumn();
                    firstRow = mergeadres.getLastRow();
                }

                if (adString.contains("марка")  && adString.contains("стали") ) {
                    material = mergeadres.getFirstColumn();
                }
            }

            for(int i = firstRow + 1; i < iSheet.getLastRowNum(); ++i) {
                if (iSheet.getRow(i) != null && iSheet.getRow(i).getCell(diam) != null) {
                    CellType checkType = iSheet.getRow(i).getCell(diam).getCellType();
                    if (checkType != CellType.BLANK) {
                        CellType checkTyp = iSheet.getRow(i).getCell(diam).getCellType();
                        if (checkTyp != CellType.BLANK) {
                            this.pipeMaterial.put(iSheet.getRow(i).getCell(diam).toString(), iSheet.getRow(i).getCell(material).toString());
                        }
                    }
                }
            }
        }
    }

    public void getMAXdn( Workbook wbk) {
               double maxDN = 0;


        for(Map.Entry<String, String> entry:this.pipeMaterial.entrySet()) {
            String aStr =  (entry.getKey()).trim().toLowerCase().replaceAll("х", "x");
            aStr = aStr.replaceAll(",", ".");
            String[] diams = aStr.split("x");

            if (this.checkNum(diams[0]) && Double.parseDouble(diams[0]) > maxDN) {
                maxDN = Double.parseDouble(diams[0]);
            }
        }

        Sheet iSheet = null;
        int iDN = -1;
        int firstcell = -1;


        for(int i = 0; i < wbk.getNumberOfSheets(); ++i) {
            if (wbk.getSheetName(i).contains("5.4")) {
                iSheet = wbk.getSheetAt(i);
                break;
            }
        }

        for(int i = 0; i < iSheet.getNumMergedRegions(); ++i) {
            CellRangeAddress mergeadres = iSheet.getMergedRegion(i);
            int mergRow = mergeadres.getFirstRow();
            int mergCol = mergeadres.getFirstColumn();
            String adString = iSheet.getRow(mergRow).getCell(mergCol).toString().toLowerCase();
            if (adString.contains("наружный") && adString.contains("диаметр") ) {
                iDN = mergeadres.getFirstColumn();
                firstcell = mergeadres.getLastRow();
            }
        }

        for(int i = firstcell; i < iSheet.getLastRowNum(); ++i) {
            if (iSheet.getRow(i) != null&& iSheet.getRow(i).getCell(iDN)!=null) {
                CellType checkType = iSheet.getRow(i).getCell(iDN).getCellType();
                if (checkType != CellType.BLANK) {
                    iSheet.getRow(i).getCell(iDN).getCellType();
                    String str = iSheet.getRow(i).getCell(iDN).toString();
                    if (str.length() > 2) {
                        str = str.replaceAll(",", ".");
                        str = str.replaceAll("х", "x");
                        str = str.replaceAll("/", "x");
                        str = str.replaceAll("-", "x");
                        String[] diams = str.split("x");
                        for (String diam : diams) {
                            if (checkNum(diam) && Double.parseDouble(diam) > maxDN) {
                                maxDN = Double.parseDouble(diam);
                            }
                        }

                    }
                }
            }
        }
        this.MaXDN=maxDN;
    }

    @Override
    public void exctractData(Workbook excPasp) {
        this.getPipeInfo(excPasp);
        this.getMAXdn(excPasp);
    }
}
