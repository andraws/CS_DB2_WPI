import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecordLocation {
    List<String> fileLoc = new ArrayList<>();
    List<Integer>  offSet = new ArrayList<>();
    String directory = "Project2Dataset/";

    public RecordLocation(){}

    public void addRecord(int fileNum, int offset){
        String file = "F"+fileNum+".txt";
        fileLoc.add(file);
        offSet.add(offset);
    }

    public boolean isEmpty(){
        if(fileLoc.size() == 0)
            return true;
        else
            return false;
    }

    public void printRecords(){
        Scanner myScanner = null;
        for(int i = 0; i < fileLoc.size(); i++) {

            try {
                myScanner = new Scanner(new File(directory+fileLoc.get(i)));
            } catch (FileNotFoundException e) {
                directory = "resources/Project2Dataset/";
                try {
                    myScanner = new Scanner(new File(directory+fileLoc.get(i)));
                } catch (FileNotFoundException e2) {
                    e2.printStackTrace();
                }
            }

            while (myScanner.hasNextLine()) {
                String data = myScanner.nextLine();
                recordOffset(data, offSet.get(i));
            }
        }
    }

    public Integer getBlockChange(){
        String lastFile = "";
        Integer cnt = 0;
        for(String file: fileLoc){
            if(!lastFile.equals(file)){
                cnt++;
                lastFile = file;
            }
        }
        return cnt;
    }

    private void recordOffset(String data, int offset){
        System.out.println(data.substring((offset*40),((offset*40)+40)));
    }
}
