package manager.Helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHelper {
    private String command;
    private Integer blockNum;
    private Integer recordNum;
    private String newContent;

    // Class used to decode messages from terminal.
    // Based on the format showed, I just split the input.
    // First word is the command.
    // Second word is the K
    // If command was set, third is the new content we are setting
    public InputHelper(){
        reset();
    }

    public void reset(){
        command = "";
        recordNum = -1;
        blockNum = -1;
        newContent = "";
    }

    /* Get the user input and turn it into a command.
        Command: GET, SET, PIN, UNPIN
     */
    public void setCommand(String userInput){
        String[] splited = userInput.split("\\s+");

        command = splited[0].toUpperCase();
        setLocation(Integer.parseInt(splited[1]));
        if(command.equals("SET")){
            Pattern p = Pattern.compile("\"([^\"]*)\"");
            Matcher m = p.matcher(userInput);
            while (m.find()) {
                newContent = "\"" + m.group(1) + "\"";
            }
        }

    }

    public void setLocation(int recordNum){
        if(command.equals("PIN") || command.equals("UNPIN")) {
            blockNum = recordNum;
        }else {
            if (recordNum >= 100) {
                blockNum = ((int) Math.floor(recordNum / 100)) + 1;
                this.recordNum = recordNum - ((blockNum-1) * 100);
            } else {
                blockNum = 1;
                this.recordNum = recordNum;
            }
            if(this.recordNum == 0){
                this.recordNum = 100;
            }
        }
    }

    public Integer getRecordNum() {
        return recordNum;
    }

    public String getCommand() {
        return command;
    }

    public Integer getBlockNum() {
        return blockNum;
    }

    public String getNewContent() {
        return newContent;
    }
}
