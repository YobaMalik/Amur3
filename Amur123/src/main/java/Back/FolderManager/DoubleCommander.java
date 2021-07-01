package Back.FolderManager;

import Back.Pasport.TPTC032;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DoubleCommander implements TPTC032 {
    public void getFileList(String path){
        List<String> files = new ArrayList<>();
        this.getFileList(path, files);
        try(Writer writer = new FileWriter(System.getProperty("user.home") +
                File.separator + "Desktop" + File.separator+"Result.txt")){
            files.forEach(e -> {
                try {
                    File file = new File(e);
                    Date date = new Date(file.lastModified());
                    String newstring = new SimpleDateFormat("dd-MM-yyyy").format(date);
                    writer.write(e);
                    writer.write("\n");
                    writer.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
