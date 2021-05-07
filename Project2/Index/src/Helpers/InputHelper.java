package Helpers;

import java.util.ArrayList;
import java.util.List;

public class InputHelper {
    public enum Command {
        BUILD,
        EQUALITY,
        RANGE,
        INEQUALITY
    }

    private List<String> words = new ArrayList<>();
    private Command type;
    private String v;
    private String v2;

    public InputHelper(String input){
        String[] words = input.split(" ");
        for(String word: words){
            this.words.add(word);
        }
        init();
    }

    private void init(){
        int wrdLen = words.size();
        if(wrdLen == 5){
            type = Command.BUILD;
        }else if(wrdLen == 12){
            type = Command.RANGE;
            //TODO: Change these into Integers
            v2 = words.get(wrdLen-1);
            v = words.get(wrdLen-5);
        }else{
            String op = words.get(wrdLen-2);
            v = words.get(wrdLen-1);

            if(op.equals("=")){
                type = Command.EQUALITY;
            }else{
                type = Command.INEQUALITY;
            }
        }
    }

    public Command getType() {
        return type;
    }

    public String[] getVariables(){
        String[] returnVal = null;
        if(this.type==Command.RANGE){
            returnVal = new String[2];
            returnVal[0] = v;
            returnVal[1] = v2;
        }else{
            returnVal = new String[1];
            returnVal[0] = v;
        }
        return returnVal;
    }
}