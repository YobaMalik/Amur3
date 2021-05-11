package Back.Pasport.InterfacePasp;

import org.apache.poi.ss.usermodel.Workbook;

@FunctionalInterface
public interface IExtractData {
     void exctractData(Workbook excPasp);
}
