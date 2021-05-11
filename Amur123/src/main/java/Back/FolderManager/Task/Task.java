package Back.FolderManager.Task;

import Back.FolderManager.CompareIdentTOExcelList;
import Back.FolderManager.FilesCopyTOFolders;
import Back.FolderManager.Pasport;

import java.util.concurrent.Callable;

public class Task implements Callable<Void> {
    private final CompareIdentTOExcelList comList;
    private final String number;
    private final Pasport pasport;

    public Task( CompareIdentTOExcelList comList,String number, Pasport pasport) {
        this.comList = comList;
        this.number = number;
        this.pasport = pasport;
    }
    @Override
    public Void call() {
        System.out.println(number + " Call");
        FilesCopyTOFolders filesCopyTOFolders = new FilesCopyTOFolders(comList,number,pasport);
        filesCopyTOFolders.copy();
        System.out.println(number + " Done");
        return null;
    }
}
