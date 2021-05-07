package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContentMap {
    HashMap<Integer,String> content = new HashMap<>();


    // Class to help me with storing the content of a file in a HashMap
    public ContentMap(){
    }

    // Setting the Hash Map, index i corresponds to a single record 1-100
    public void setMap(String fContent){
        List<String> parts = new ArrayList<String>();
        int len = fContent.length();
        for(int i = 0; i < len; i += 40){
            parts.add(fContent.substring(i, Math.min(len,i+40)));
        }
        int key = 1;
        for(String part : parts){
            content.put(key,part);
            key += 1;
        }
    }

    // Set new content to the right position
    public void setNewContent(int record, String newContent){
        content.put(record, newContent.substring(1,newContent.length()-1));
    }

    // get all the content as a String, we can write this to a file.
    public String getContent(){
        String rString = "";
        for(Integer key : content.keySet()){
            rString += content.get(key);
        }
        return rString;
    }

    // Get record j, 0-100
    public String getRecord(int j){
        String record = this.content.get(j);
        return record;
    }

    // Function to print hashmap
    public void printSelf(){
        for(Integer key : content.keySet()){
            System.out.println(key.toString() + ": " + content.get(key).toString());
        }
    }

}
