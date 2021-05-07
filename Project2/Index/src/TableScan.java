import Helpers.FileHelper;

import java.util.ArrayList;
import java.util.List;

public class TableScan {

    public TableScan(){}

    public void equalitySearch(String key){
        int keyI = Integer.parseInt(key);
        List<String> equalRecords = new ArrayList<>();
        for(int file = 1; file < 99; file++){
            FileHelper fH = new FileHelper(file);
            List<String> records = fH.readFile();
            for(int offset = 0; offset< records.size(); offset++){
                int l = records.get(offset).length();
                int k = Integer.parseInt(records.get(offset).substring(l-7,l-3));
                if(keyI == k){
                    equalRecords.add(records.get(offset));
                }
            }
        }
        printRecords(equalRecords);
    }

    public void inequalitySearch(String key){
        List<String> equalRecords = new ArrayList<>();
        int keyI = Integer.parseInt(key);
        for(int file = 1; file < 99; file++){
            FileHelper fH = new FileHelper(file);
            List<String> records = fH.readFile();
            for(int offset = 0; offset< records.size(); offset++){
                int l = records.get(offset).length();
                int k = Integer.parseInt(records.get(offset).substring(l-7,l-3));
                if(k != keyI){
                    equalRecords.add(records.get(offset));
                }
            }
        }
        printRecords(equalRecords);
    }

    public void rangeSearch(String key1, String key2){
        List<String> equalRecords = new ArrayList<>();
        for(int file = 1; file < 99; file++){
            FileHelper fH = new FileHelper(file);
            List<String> records = fH.readFile();
            for(int offset = 0; offset< records.size(); offset++){
                int l = records.get(offset).length();
                int k = Integer.parseInt(records.get(offset).substring(l-7,l-3));
                int givenKey1 = Integer.parseInt(key1);
                int givenKey2 = Integer.parseInt(key2);
                if(k > givenKey1 && k < givenKey2){
                    equalRecords.add(records.get(offset));
                }
            }
        }
        printRecords(equalRecords);
    }

    private void printRecords(List<String> records){
        for(String r: records){
            System.out.println(r);
        }
    }
}
