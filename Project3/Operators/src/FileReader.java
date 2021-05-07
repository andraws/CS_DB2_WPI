import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    File myFile;
    String folderName;

    public FileReader(String folderLetter, int fileNum){
        if (folderLetter.equals("A")){
            this.folderName = "Project3Dataset-A";
        }else{
            this.folderName = "Project3Dataset-B";
        }

        String fileName = folderLetter + String.valueOf(fileNum) + ".txt";
//        this.myFile = new File("Project3Dataset/" + this.folderName+"/"+fileName); // Uncomment this line if you are running on IDE
        this.myFile = new File(this.folderName+"/"+fileName);

        try {
            Scanner myReader = new Scanner(this.myFile);
        }catch (Exception e){
            e.printStackTrace();
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

    public Integer getRecordV(int offset){
        String record = "-1";
        try {
            Scanner myReader = new Scanner(this.myFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                record = data.substring((((offset+1)*40)-7),(((offset+1)*40)-3));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(record);
    }

    public static void main(String[] args) {
        FileReader fR = new FileReader("B",1);
        System.out.println(fR.getRecordV(0));
    }
}
