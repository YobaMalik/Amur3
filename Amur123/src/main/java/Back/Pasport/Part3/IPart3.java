package Back.Pasport.Part3;

import Back.Pasport.InterfacePasp.IExtractData;
import Back.Pasport.Part5.Element.Elements;
import Back.Pasport.RowfTable;

import java.util.List;

public interface IPart3 extends IExtractData {
    List<RowfTable<String>> getResLinePart3();
    void setResLinePart3(List<Elements> resLinePart3);

    List<Elements> getElementsPart3();
    void setElementsPart3(List<Elements> elementsPart3);
}
