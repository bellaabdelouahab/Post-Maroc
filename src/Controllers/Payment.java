package Controllers;

public class Payment {
    private int Id;
    private String Client_id;
    private String AMOUNT;
    private String DATE;
    // constructor
    public Payment(int ID, String CLIENTID, String AMOUNT, String DATE) {
        this.Id = ID;
        this.Client_id = CLIENTID;
        this.AMOUNT = AMOUNT;
        this.DATE = DATE;
    }
    //  getters
    public int getId() {
        return Id;
    }
    public String getClient_id() {
        return Client_id;
    }
    public String getAMOUNT() {
        return AMOUNT;
    }
    public String getDATE() {
        return DATE;
    }
    
}
