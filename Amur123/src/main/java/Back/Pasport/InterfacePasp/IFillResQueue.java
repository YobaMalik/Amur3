package Back.Pasport.InterfacePasp;


import Back.Pasport.RowfTable;

import java.util.Queue;

public interface IFillResQueue {
    void fillResultQueue(String aFile, Queue<RowfTable<String>> allTable);
}
