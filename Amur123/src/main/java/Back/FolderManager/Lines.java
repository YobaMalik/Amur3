package Back.FolderManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Lines {
    private String line;

    Map<String, String> ident = new HashMap<>();

    @Override
    public int hashCode() {
        return Objects.hashCode(line);
    }

    @Override
    public boolean equals(Object obj) {
        Lines lines = (Lines) obj;
        return lines.getLine().equals(this.line) && this != lines;
    }


    public void setLine(String line) {
        this.line = line;
    }
    public String getLine(){
        return this.line;
    }
}
