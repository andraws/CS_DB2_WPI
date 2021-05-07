import java.util.Scanner;

public class Controller {
    private long start_time;

    public static void main(String[] args) {
        Controller program = new Controller();
        program.startTerminal();
    }

    private void startTime(){
        this.start_time = System.nanoTime();
    }

    private void endTime(){
        long end_time = System.nanoTime();
        long start_time = this.start_time;
        this.start_time = 0;
        double t = ((end_time-start_time)/1e6);
        System.out.println("-- Query Time: " + t + " milli seconds");

    }

    public void startTerminal(){
        while(true){
            System.out.println("Program is ready and waiting for user command");
            System.out.print("Input: ");
            Scanner sc = new Scanner(System.in);
            UserInput input = new UserInput(sc.nextLine());
            execute(input);
        }
    }

    private void execute(UserInput input){
        if(input.getCmd().equals(Command.HASH_JOIN)){
            HashIndex hI = new HashIndex("A");
            startTime();
            hI.printHashJoin();
            endTime();
        }else if(input.getCmd().equals(Command.NEST_LOOP_JOIN)){
            HashIndex hI = new HashIndex("A");
            System.out.println("Running Query...");
            startTime();
            hI.printNestedLoopJoin();
            endTime();
        }else if(input.getCmd().equals(Command.HASH_AGGREGATION)){
            String[] vars = input.getVariables();
            String dataset = vars[1];
            HashIndex hI = new HashIndex(dataset);

            startTime();
            if(vars[0].equals("SUM(RandomV)")){
                hI.sumAggregate();
            }else if(vars[0].equals("AVG(RandomV)")){
                hI.avgAggregate();
            }
            endTime();

        }
    }
}
