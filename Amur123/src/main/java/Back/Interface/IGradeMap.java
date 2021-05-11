package Back.Interface;

import java.sql.SQLException;
import java.util.Set;

public interface IGradeMap {
    void getSpecMap() throws SQLException;
    Set<String> getGradeMap(String spec) throws SQLException;
    double getTensionValue(String spec, String grade, String table, double desTemp, double[] array) throws SQLException;
}
