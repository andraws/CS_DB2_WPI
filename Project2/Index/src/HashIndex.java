import Helpers.FileHelper;

import java.util.HashMap;
import java.util.List;

public class HashIndex implements Index {
    private HashMap<Integer, RecordLocation> hashMap = new HashMap<>();

    public HashIndex(){
        init();
    }

    private void init(){
        for(int i=1; i<=5000;i++){
            RecordLocation rL = new RecordLocation();
            hashMap.put(i,rL);
        }
    }

    private void buildRecord(){
        for(int file = 1; file < 99; file++){
            FileHelper fH = new FileHelper(file);
            List<String> records = fH.readFile();
            for(int offset = 0; offset<records.size();offset++){
                int l = records.get(offset).length();
                Integer key = Integer.parseInt(records.get(offset).substring(l-7,l-3));
                hashMap.get(key).addRecord(file,offset);
            }
        }
    }

    public void addRecord(String record, int fileNum, int offSet){
        int l = record.length();
        Integer key = Integer.parseInt(record.substring(l-7,l-3));
        hashMap.get(key).addRecord(fileNum,offSet);
    }

    public RecordLocation printRecord(String key){
        Integer k = Integer.parseInt(key);
        hashMap.get(k).printRecords();
        return hashMap.get(k);
    }
}
