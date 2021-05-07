package manager.Helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileHelper {
    File myFile;

    // Class used to offload all I/O Commands
    public FileHelper(String dir, String fileName){
        this.myFile = new File(dir+fileName);
    }


    // Reads file
    public String readFile(){
        try{
            Scanner myReader = new Scanner(this.myFile);
            while (myReader.hasNextLine()){
                String data = myReader.nextLine();
                return data;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    // Writes to file the new content
    public void saveFile(String content){
        try {
            PrintWriter pW = new PrintWriter(this.myFile);
            pW.print(content);
            pW.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
