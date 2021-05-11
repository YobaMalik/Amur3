package Back.Pasport;

import Back.Interface.IRows;
import java.util.ArrayList;
import java.util.List;

public class RowfTable<T> implements IRows<T> {
    private List<T> RowList=new ArrayList<>();

    public void addValue(int index, T iValue){
        if(iValue!=null) {
            RowList.add(index, iValue);
        } else {
            RowList.add(index, (T) "");
        }
    }

    public void addValue(T iValue) {
        RowList.add(iValue);
    }
    public int getSize (){
        return RowList.size();
    }
    public T getValue(int index){
        return RowList.get(index);
    }
    public T getLast(){
        return RowList.get(0);
    }
    public List<T> getRowList(){
        return this.RowList;
    }
}
