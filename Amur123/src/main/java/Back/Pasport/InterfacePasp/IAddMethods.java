package Back.Pasport.InterfacePasp;

import org.apache.poi.ss.usermodel.Workbook;

public interface IAddMethods {
    default boolean checkFNIP(Workbook excPasp) {
        return excPasp.getNumberOfSheets() < 10;
    }

    default boolean checkNum(String str){
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
