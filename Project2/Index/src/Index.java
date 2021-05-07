public interface Index {

    public void addRecord(String record, int fileNum, int offSet);
    public RecordLocation printRecord(String key);
}
