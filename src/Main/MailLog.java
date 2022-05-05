package Main;

public class MailLog {
    private String Id;
    private String WEIGHT;
    private String ADDRESS;
    private String COLLECT_DATE;
    private String PRICE;   
    private String STATUS;
    // constructor
    public MailLog(String ID, String WEIGHT, String ADDRESS, String COLLECT_DATE, String PRICE, String STATUS) {
        this.Id = ID;
        this.WEIGHT = WEIGHT;
        this.ADDRESS = ADDRESS;
        this.COLLECT_DATE = COLLECT_DATE;
        this.PRICE = PRICE;
        this.STATUS = STATUS;
    }
    // getters
    public String getId() {
        return Id;
    }
    public String getWEIGHT() {
        return WEIGHT;
    }
    public String getADDRESS() {
        return ADDRESS;
    }
    public String getCOLLECT_DATE() {
        return COLLECT_DATE;
    }
    public String getPRICE() {
        return PRICE;
    }
    public String getSTATUS() {
        return STATUS;
    }
}
