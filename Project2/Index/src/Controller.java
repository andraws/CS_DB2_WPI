import Helpers.FileHelper;
import Helpers.InputHelper;
import java.util.List;
import java.util.Scanner;

public class Controller {

    InputHelper.Command cmd;
    Boolean built = false;
    HashIndex hashIndex;
    ArrayIndex arrayIndex;
    TableScan tableScan = new TableScan();

    long start_time;

    public static void main(String[] args){
        Controller program = new Controller();
        program.startTerminal();
    }

    public void startTerminal(){
        while(true){
            System.out.println("Program is ready and waiting for user command");
            System.out.print("Input: ");
            Scanner in = new Scanner(System.in);
            String inputString = in.nextLine();

            InputHelper input = new InputHelper(inputString);
            chooseCommand(input);
        }
    }

    private void chooseCommand(InputHelper input){
        InputHelper.Command command = input.getType();

        if(command.equals(cmd.BUILD)){
            hashIndex = new HashIndex();
            arrayIndex = new ArrayIndex();

            for(int file = 1; file < 99; file++){
                FileHelper fH = new FileHelper(file);
                List<String> records = fH.readFile();
                for(int offset = 0; offset< records.size(); offset++){
                    hashIndex.addRecord(records.get(offset),file,offset);
                    arrayIndex.addRecord(records.get(offset),file,offset);
                }
            }
            built = true;
            System.out.println("-- The hash-based and array-based indexes are built successfully.");
        }
        else if (command.equals(cmd.EQUALITY)){
            String v = input.getVariables()[0];
            if(built) {
                startTime();
                RecordLocation rL = hashIndex.printRecord(v);
                System.out.println("-- Query Time: " + endTime() + " milli seconds");
                int filesRead=1;
                if (rL.isEmpty())
                    filesRead = 0;
                System.out.println("-- Used a Hash based Index for query and read "+ filesRead +" file");
            }
            else{
                startTime();
                tableScan.equalitySearch(v);
                System.out.println("-- Query Time: " + endTime() + " milli seconds");
                System.out.println("-- Used Full Table Scan for query and read 99 file");
            }
        }
        else if (command.equals(cmd.RANGE)){
            String v1 = input.getVariables()[0];
            String v2 = input.getVariables()[1];
            if(built){
                startTime();
                Integer blocksRead = arrayIndex.rangeSearch(v1,v2);
                System.out.println("-- Query Time: " + endTime() + " milli seconds");
                System.out.println("-- Used a Hash based Index for query and read " + blocksRead + " file");
            }
            else {
                startTime();
                tableScan.rangeSearch(v1,v2);
                System.out.println("-- Query Time: " + endTime() + " milli seconds");
                System.out.println("-- Used Full Table Scan for query and read 99 file");
            }
        }
        else if (command.equals(cmd.INEQUALITY)){
            String v = input.getVariables()[0];
            startTime();
            tableScan.inequalitySearch(v);
            System.out.println("-- Query Time: " + endTime() + " milli seconds");
            System.out.println("-- Used Full Table Scan for query and read 99 file");
        }

    }

    private void startTime(){
        this.start_time = System.nanoTime();
    }

    private double endTime(){
        long end_time = System.nanoTime();
        long start_time = this.start_time;
        this.start_time = 0;
        return (end_time-start_time)/1e6;
    }
}
