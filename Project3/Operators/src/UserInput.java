public class UserInput {
    Command cmd;

    private String input;
    private String[] inputList;

    public UserInput(String input) {
        this.input = input;
        this.inputList = input.split(" ");

        if (inputList.length == 12)
            cmd = Command.HASH_JOIN;
        else if (inputList.length == 9)
            cmd = Command.NEST_LOOP_JOIN;
        else if (inputList.length == 8)
            cmd = Command.HASH_AGGREGATION;
        else
            cmd = Command.INVALID;
    }

    public String[] getVariables(){
        if (cmd.equals(Command.HASH_AGGREGATION)){
            String[] cl = new String[2];
            cl[0] = inputList[2];
            cl[1] = inputList[4];
            return cl;
        }
        return null;
    }

    public Command getCmd() {
        return cmd;
    }
}

