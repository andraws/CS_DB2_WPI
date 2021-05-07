package Helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FileHelper {
    File myFile;
    String directory = "Project2Dataset/";

    // Class used to offload all I/O Commands
    public FileHelper(int fileNum){
        String fileName = "F"+fileNum+".txt";
        try {
            this.myFile = new File(directory + fileName);
            Scanner myReader = new Scanner(this.myFile);
        }catch (Exception e){
            directory = "resources/Project2Dataset/";
            this.myFile = new File(directory + fileName);
            try {
                Scanner myReader = new Scanner(this.myFile);
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
        }
    }


    // Reads file
    public List<String> readFile() {
        try {
            Scanner myReader = new Scanner(this.myFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                return seperateRecords(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> seperateRecords(String data){
        List<String> records = new LinkedList<>();
        for(int i =0; i < 99; i++){
            records.add(data.substring((i*40),(i*40 + 40)));
        }
        return records;
    }
}
