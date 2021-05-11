package NDT.NDTSpecWelders;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@EqualsAndHashCode
public class LineWelders implements Cloneable{
    @Getter @Setter
    private String line;
    @Getter @Setter
    private String zone;
    @Getter @Setter
    private Map<String,String> idCode = new TreeMap<>();


    protected Object clone() throws CloneNotSupportedException {
        LineWelders welders = new LineWelders();
        welders.setZone(zone);
        welders.setLine(line);
        welders.setIdCode(Map.copyOf(idCode));
        return welders;
    }
}

