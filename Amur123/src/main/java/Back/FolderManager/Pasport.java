package Back.FolderManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Pasport {
    private String number;

   // Map<String, String> lines = new HashMap<>();
    public Map<String, Lines> lines = new HashMap<>();

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }

    @Override
    public boolean equals(Object obj) {
        Pasport pasport = (Pasport) obj;
        return (pasport.getNumber().equals(this.number) && this != pasport);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
