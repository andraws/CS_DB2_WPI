import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bucket {
    List column = new ArrayList<String[]>();

    public Bucket(){}

    public void addRecord(String record){
        String[] recordSplit = record.split(", ");
        column.add(recordSplit);
    }

    public List<String> getColumn(int c){
        List<String> columnVal = new ArrayList();
        Iterator<String[]> iter = column.iterator();
        while(iter.hasNext()){
            columnVal.add(iter.next()[c-1]);
        }
        return columnVal;
    }

    public static void main(String[] args) {
         Bucket b= new Bucket();
         b.addRecord("A01-Rec001, Name001, address001, 0424...");
         b.getColumn(2);
    }

    public int size(){
        return column.size();
    }
}
