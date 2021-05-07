import java.util.LinkedList;
import java.util.List;

public class ArrayIndex implements Index{

    List<RecordLocation> index = new LinkedList<>();

    public ArrayIndex(){
        init();
    }

    private void init(){
        for(int i=0; i<5000;i++){
            RecordLocation rL = new RecordLocation();
            index.add(rL);
        }
    }

    public Integer rangeSearch(String v1, String v2){
        Integer oldFile = -1;
        Integer fileCnt = 0;
        Integer v1i = Integer.parseInt(v1);
        Integer v2i = Integer.parseInt(v2);
        for(int i = v1i; i < v2i-1; i++){
            RecordLocation rL = printRecord(i);
            fileCnt += rL.getBlockChange();
        }
        return fileCnt;
    }

    public void addRecord(String record, int fileNum, int offSet){
        int l = record.length();
        Integer key = Integer.parseInt(record.substring(l-7,l-3));
        index.get(key-1).addRecord(fileNum,offSet);
    }

    public RecordLocation printRecord(String key){
        Integer k = Integer.parseInt(key);
        index.get(k).printRecords();
        return index.get(k);
    }

    public RecordLocation printRecord(int key){
        index.get(key).printRecords();
        return index.get(key);
    }
}
