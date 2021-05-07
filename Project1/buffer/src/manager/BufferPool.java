package manager;

import manager.Helper.InputHelper;

import java.util.*;

public class BufferPool {

    private List<Frame> bufferArray;
    private int lastBlockID = 0;

    public void initialize(int frameSize){
        bufferArray = new ArrayList<>();
        for(int i=0; i<frameSize;i++){
            Frame frame = new Frame(i+1);
            frame.initialize();
            bufferArray.add(frame);
        }
    }

    // The way we take the users inputs
    public void startTerminal(){
        Boolean goFlag = true;
        InputHelper inputHelper = new InputHelper();
        while(goFlag){
            try {
                inputHelper.reset();
                Scanner input = new Scanner(System.in);
                String inputString = input.nextLine();
                inputHelper.setCommand(inputString);
                System.out.print("Output: ");
                if (inputHelper.getCommand().equals("SET")) {
                    setBlock(inputHelper.getBlockNum(), inputHelper.getRecordNum(), inputHelper.getNewContent());
                } else if (inputHelper.getCommand().equals("GET")) {
                    getBlock(inputHelper.getBlockNum(), inputHelper.getRecordNum());
                } else if (inputHelper.getCommand().equals("PIN")) {
                    pinBlock(inputHelper.getBlockNum());
                } else if (inputHelper.getCommand().equals("UNPIN")) {
                    unPinBlock(inputHelper.getBlockNum());
                } else {
                    System.out.println("Sorry, That is not a correct Command");
                }
                System.out.println();
            } catch (Exception e) {
                System.out.println("Sorry, I don't understand the command");
                continue;
            }
        }
    }


    // Function the checkers block # requested is in function
    private boolean isBlockInBuffer(int blockNum){
        for(Frame frame : bufferArray){
            if(frame.getBlockID() == blockNum) {
                return true;
            }
        }
        return false;
    }

    // Function that checks if there is any empty space in our Buffer
    private boolean isEmptyInBuffer(){
        for(Frame frame: bufferArray){
            if(frame.getBlockID() == -1){
                return true;
            }
        }
        return false;
    }

    // Function that gives us the frame that is currently empty
    private Frame getEmptyFrame(){
        for(Frame frame:bufferArray){
            if(frame.getBlockID()==-1){
                return frame;
            }
        }
        return null;
    }

    // Function that returns us the frame in the buffer with the corresponding block #
    private Frame getBlockInFrame(int blockNum){
        for(Frame frame : bufferArray){
            if(frame.getBlockID() == blockNum) {
                return frame;
            }
        }
        return null;
    }

    // Function that checks if at least 1 frame is unpinned
    private boolean canReplace(){
        for(Frame frame : bufferArray){
            if(!frame.getPinned()) {
                return true;
            }
        }
        return false;
    }

    // Function that returns the frame with regards of the replacement policy noted in the Project 1 Guide lines
    private Frame replacePolicy(){
        int i = 0;
        while(i < bufferArray.size()){
            if(!bufferArray.get(lastBlockID).getPinned()){
                Frame f =  bufferArray.get(lastBlockID);
                lastBlockID += 1;
                if(lastBlockID >= bufferArray.size()){
                    lastBlockID = 0;
                }
                return f;
            }else{
                i += 1;
                lastBlockID += 1;
                if(lastBlockID >= bufferArray.size()){
                    lastBlockID = 0;
                }
            }
        }
        return null;
    }

    // The 4 Scenarios for the GET method as identified in the Project 1 Guide lines
    private void getBlock(int blockNum,int recordNum){
        if(isBlockInBuffer(blockNum)){ // The block is in the buffer
            Frame frame = getBlockInFrame(blockNum);
            System.out.print(frame.getRecord(recordNum));
            System.out.println("; File " + blockNum + " already in memory; Located in Frame " + frame.getFrameNum());

        }
        else if(isEmptyInBuffer()){ // We have at least 1 empty spot in buffer
            Frame tmp = getEmptyFrame();
            tmp.getBlockNum(blockNum);
            System.out.print(tmp.getRecord(recordNum));
            System.out.println("; Brought File "+ blockNum +" from disk; Placed in Frame "+ tmp.getFrameNum());
        }else if(canReplace()){
            Frame rFrame = replacePolicy();
            int oldBlockNum = rFrame.getBlockID();
            if(rFrame.getDirty()){
                rFrame.saveBlock();
            }
            rFrame.getBlockNum(blockNum);
            System.out.print(rFrame.getRecord(recordNum));
            System.out.println("; Brought File "+ blockNum +" from disk; Placed in Frame "+ rFrame.getFrameNum() + "; Evicted file " + oldBlockNum + " from Frame " + rFrame.getFrameNum());
        }else{
            System.out.println("The corresponding block #" + blockNum + " cannot be accessed from disk because the memory buffers are full");
        }
    }

    // The 4 Scenarios for the SET method as identified in the Project 1 Guide lines
    private void setBlock(int blockID, int recordNum, String nContent){
        Frame frame;
        if(isBlockInBuffer(blockID)){
            frame = getBlockInFrame(blockID);
            frame.setContent(recordNum,nContent);
            System.out.println("Write was successful; File " + blockID + " already in in memory; Located in Frame " + frame.getFrameNum());
        }else if (isEmptyInBuffer()){
            frame = getEmptyFrame();
            frame.getBlockNum(blockID);
            frame.setContent(recordNum,nContent);
            System.out.println("Write was successful; Brought File "+ blockID +" from disk; Placed in Frame "+ frame.getFrameNum());
        }else if (canReplace()){
            frame = replacePolicy();
            int oldBlockNum = frame.getBlockID();
            if(frame.getDirty()){
                frame.saveBlock();
            }
            frame.getBlockNum(blockID);
            frame.setContent(recordNum,nContent);
            System.out.println("Write was successful; Brought File "+ blockID +" from disk; Placed in Frame "+ frame.getFrameNum() + "; Evicted file " + oldBlockNum + " from Frame " + frame.getFrameNum());
        }else{
            System.out.println("The corresponding block #" + blockID + " cannot be accessed from disk because the memory buffers are full; Write was unsuccessful");
        }
    }

    // The 4 Scenarios for the PIN method as identified in the Project 1 Guide lines
    private void pinBlock(int blockID){
        if(isBlockInBuffer(blockID)){
            Frame frame = getBlockInFrame(blockID);
            boolean wasPinned = frame.getPinned();
            frame.setPinned(true);
            if(wasPinned){
                System.out.println("File " + blockID + " pinned in Frame " + frame.getFrameNum() + "; Already pinned");
            }else {
                System.out.println("File " + blockID + " pinned in Frame " + frame.getFrameNum() + "; Not already pinned");
            }
        }else if(isEmptyInBuffer()){
            Frame tmp = getEmptyFrame();
            tmp.getBlockNum(blockID);
            tmp.setPinned(true);
            System.out.println("File "+ blockID +" pinned in Frame " + tmp.getFrameNum() + "; Not already pinned;");
        }else if(canReplace()){
            Frame rFrame = replacePolicy();
            int oldBlockNum = rFrame.getBlockID();
            if(rFrame.getDirty()){
                rFrame.saveBlock();
            }
            rFrame.getBlockNum(blockID);
            rFrame.setPinned(true);
            System.out.println("File " + blockID + " is pinned in Frame " + rFrame.getFrameNum() + "; Frame " + rFrame.getFrameNum() + " was not already pinned; Evicted file " + oldBlockNum + " from Frame " + rFrame.getFrameNum());
        }else{
            System.out.println("The corresponding block " + blockID + " cannot be pinned because the memory buffers are full");
        }
    }

    // The 2 Scenarios for the UNPIN method as identified in the Project 1 Guide lines
    private void unPinBlock(int blockID){
        if(isBlockInBuffer(blockID)){
            Frame frame = getBlockInFrame(blockID);
            boolean wUnpinned = frame.getPinned();
            frame.setPinned(false);
            if(wUnpinned){
                System.out.println("File " + blockID + " is unpinned in frame " + frame.getFrameNum() + "; Frame " + frame.getFrameNum() + " was not already unpinned");
            }else{
                System.out.println("File " + blockID + " in frame " + frame.getFrameNum() + " is unpinned; Frame was already unpinned");
            }
        }else{
            System.out.println("The corresponding block " + blockID + " cannot be unpinned because it is not in memory");
        }
    }

    // Debug function to print Buffer
    public void printBuffer(){
        for(Frame f : this.bufferArray){
            f.printHeader();
        }
    }


    public static void main(String[] args) {
        int buffSize = 0;
        if(args.length == 0) {
            try {
                System.out.println("Enter Buffer Size: ");
                Scanner input = new Scanner(System.in);
                String inputString = input.nextLine();
                buffSize = Integer.parseInt(inputString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            buffSize = Integer.parseInt(args[0]);
        }
        BufferPool buffer = new BufferPool();
        buffer.initialize(buffSize);
        System.out.println("Enter Commands");
        buffer.startTerminal();
    }
}
