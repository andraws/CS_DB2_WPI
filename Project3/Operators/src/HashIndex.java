import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static sun.tools.jstat.Alignment.keySet;

public class HashIndex {
    public HashMap<Integer, Bucket> hashMap = new HashMap();
    public HashMap<String, Float> aggMap = new HashMap();
    private String map;

    public HashIndex(String letterName){
        this.map = letterName;
        init();
    }

    private void init(){
        for(int i=1; i<=500; i++){
            Bucket bk = new Bucket();
            hashMap.put(i,bk);
        }
    }

    private Integer getRecords(String fileName, int fileNum, int offset){
        FileReader fileReader = new FileReader(fileName,fileNum);
        return fileReader.getRecordV(offset);
    }

    private void buildRecord(int fileNum){
        init();
        FileReader fileReader = new FileReader(this.map,fileNum);
        List<String> records = fileReader.readFile();
        for(int offset = 0; offset < records.size(); offset++){
            int l = records.get(offset).length();
            Integer key = Integer.parseInt(records.get(offset).substring(l-7,l-3));
            hashMap.get(key).addRecord(records.get(offset));
        }
    }

    private void buildRecord(){
        for(int fileNum = 1; fileNum<99; fileNum++){
          FileReader fileReader = new FileReader(this.map,fileNum);
          List<String> records = fileReader.readFile();
          for(int offset = 0; offset < records.size(); offset++){
              int l = records.get(offset).length();
              Integer key = Integer.parseInt(records.get(offset).substring(l-7,l-3));
              hashMap.get(key).addRecord(records.get(offset));

          }
        }
    }

    public void printHashJoin(){
        buildRecord();
        System.out.println("A.Col1,      A.Col2,   B.Col1,      B.Col2\n");
        HashIndex bMap = new HashIndex("B");
        bMap.buildRecord();
        for(int v=1;v<=500; v++) {
            System.out.println("RandomV: " + v);
            List aM1 = hashMap.get(v).getColumn(1);
            List aM2 = hashMap.get(v).getColumn(2);
            List bM1 = bMap.hashMap.get(v).getColumn(1);
            List bM2 = bMap.hashMap.get(v).getColumn(2);

            int b = bM1.size();
            int a = aM1.size();
            for (int i = 0; Math.max(a, b) > i; i++) {
                if (a > i && b > i)
                    System.out.println(aM1.get(i) + "   " + aM2.get(i) + "   " + bM1.get(i) + "   " + bM2.get(i));
                else if (a <= i)
                    System.out.println("                       " + bM1.get(i) + "   " + bM2.get(i));
                else
                    System.out.println(aM1.get(i) + "   " + aM2.get(i));
            }
        }
    }

    public int printNestedLoopJoin(){
        // Going through all A files
        Integer counter = 0;
        for(int fileNum=1; fileNum<=99; fileNum++){
            // Building our HashMap with all records in that file
            buildRecord(fileNum);
//            System.out.println("FileNum: " + fileNum + " Counter: " + counter);
            // Going through each file
            for (int bFileNum = 1; bFileNum<=99; bFileNum++){
                // Going through each record in B(bFileNum).txt
                for(int offset = 0; offset<99;offset++) {
                    int k = getRecords("B", bFileNum, offset);
                    counter += getLarger(k);
                }
            }
        }
        System.out.println(counter);
        return counter;
    }

    private Integer getLarger(int V){
        Integer counter = 0;
        for(int i = 500; i>=V; i--){
            counter += hashMap.get(i).size();
        }
        return counter;
    }

    public void sumAggregate(){
        for(int fileNum=1;fileNum<=99;fileNum++) {
            FileReader fileReader = new FileReader(this.map, fileNum);
            List<String> records = fileReader.readFile();

            for (String record : records) {
                String col2Key = record.substring(12, 18);
                Integer V = Integer.parseInt(record.substring((40 - 7), (40 - 3)));
                try {
                    Float finalV = V +  aggMap.get(col2Key);
                    aggMap.put(col2Key, finalV);
                } catch (Exception e) {
                    Float finalV = Float.valueOf(V);
                    aggMap.put(col2Key, finalV);
                }
            }
        }
        printAgg();
    }

    public void avgAggregate(){
        for(int fileNum=1;fileNum<=99;fileNum++) {
            FileReader fileReader = new FileReader(this.map, fileNum);
            List<String> records = fileReader.readFile();

            for (String record : records) {
                // Get Col2
                String col2Key = record.substring(12, 18);
                // get RandomV
                Integer V = Integer.parseInt(record.substring((40 - 7), (40 - 3)));
                try {
                    Float finalV = ((V +  aggMap.get(col2Key))/2);
                    aggMap.put(col2Key, finalV);
                } catch (Exception e) {
                    Float finalV = Float.valueOf(V);
                    aggMap.put(col2Key, finalV);
                }
            }
        }
        printAgg();
    }

    private void printAgg(){
        Iterator<String> iter = aggMap.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            Float v = aggMap.get(key);
            System.out.print(key + ": ");
            System.out.printf("%.2f",v);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        HashIndex aMap = new HashIndex("B");
        aMap.avgAggregate();
    }
}
