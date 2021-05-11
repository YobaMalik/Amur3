package Back.Interface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public interface IResultDocs {
        void createDocs(Map<String, ByteArrayOutputStream> fileInput,
                        Map<String, ByteArrayOutputStream> resultList,
                        ExecutorService newTask ) throws IOException;
}
