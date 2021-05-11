package Back.Pasport.Part4;

import Back.Pasport.InterfacePasp.IExtractData;
import java.util.Calendar;
import java.util.List;

public interface IPart4 extends IExtractData {
    List<String> getDWGsList();
    void setDWGsList();

    Calendar getDate();
    void setDate(Calendar date);
}
