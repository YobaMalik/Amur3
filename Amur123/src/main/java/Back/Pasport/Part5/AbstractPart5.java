package Back.Pasport.Part5;

import Back.Interface.ConvertString;
import Back.Pasport.Part5.Element.Elements;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.ParseException;
import java.util.List;

abstract public class AbstractPart5 implements ConvertString {

   protected Workbook wb;

   public AbstractPart5(Workbook wb) {
      this.wb=wb;
   }
   abstract protected void getInfoPart5(String paspPart);
   abstract public List<Elements> fillTable(String... paspPart) throws ParseException;
}

