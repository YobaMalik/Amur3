import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        TaskList task = new TaskList();
        while (task.value != 123){
            task.execute();
        }
    }
}



