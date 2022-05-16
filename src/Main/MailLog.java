package Main;

public class MailLog {
    private String Id;
    private String ADDRESS;
    private String COLLECT_DATE;
    private Float PRICE;   
    private String STATUS;
    // constructor
    public MailLog(String ID,String ADDRESS, String COLLECT_DATE, float PRICE, String STATUS) {
        this.Id = ID;
        this.ADDRESS = ADDRESS;
        this.COLLECT_DATE = COLLECT_DATE;
        this.PRICE = PRICE;
        this.STATUS = STATUS;
    }
    // getters
    public String getId() {
        return Id;
    }
    public String getADDRESS() {
        return ADDRESS;
    }
    public String getCOLLECT_DATE() {
        return COLLECT_DATE;
    }
    public Float getPRICE() {
        return PRICE;
    }
    public String getSTATUS() {
        return STATUS;
    }
}
