package manager;

import manager.Helper.FileHelper;

public class Frame {
    private String content;
    private Boolean dirty;
    private Boolean pinned;
    private Integer blockID;
    private Integer frameNum;
    private ContentMap contentMap = new ContentMap();

    public Frame(int i){
        this.frameNum = i;
    }

    // Initialize Frame
    public void initialize(){
        content = new String();
        dirty = false;
        pinned = false;
        blockID = -1;
    }

    // Creates Hashmap with records from blockNum. blockNum is 1-7 for the txt files.
    public void getBlockNum(int blockNum){
        initialize();
        blockID = blockNum;
        String fileName = new String("F"+blockNum+".txt");
        FileHelper fp = new FileHelper("resources/Dataset/", fileName);
        this.content = fp.readFile();
        this.contentMap.setMap(this.content);
    }

    // Set the new content to our hashmap and then update the content String.
    public void setContent(int record, String newContent){
        setDirty(true);
        contentMap.setNewContent(record,newContent);
        content = contentMap.getContent();
    }

    // Save block on the txt file
    public void saveBlock(){
        String fileName = new String("F"+blockID+".txt");
        FileHelper fp = new FileHelper("resources/Dataset/", fileName);
        fp.saveFile(content);
    }


    // Gets record from contentMap, contentMap prints out the record as well
    public String getRecord(int record){
        return contentMap.getRecord(record);
    }

    // All my setters and getters

    public ContentMap getContentMap() {
        return contentMap;
    }

    public void setBlockID(Integer blockID) {
        this.blockID = blockID;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setDirty(Boolean dirty) {
        this.dirty = dirty;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    public Boolean getDirty() {
        return dirty;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public String getContent() {
        return content;
    }

    public Integer getBlockID() {
        return blockID;
    }

    public Integer getFrameNum() {
        return frameNum;
    }

    public void printHeader(){
        System.out.println(blockID + ", " + frameNum + ", " + dirty + "," + pinned);
    }
}
