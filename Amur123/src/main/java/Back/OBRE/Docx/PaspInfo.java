package Back.OBRE.Docx;


import Back.Pasport.PasportPipeGost;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

public class PaspInfo extends PasportPipeGost {
    public PaspInfo(InputStream excPasp,String filename) throws IOException, ParseException {
        super(excPasp, filename);
    }
}
