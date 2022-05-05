package Controllers.Employer;

public class Courier {
    // This class will be used to control the courier page
    private String CourierId;
    private String Weight;
    private String Address;
    private String CollectDate;
    private String ClientId;
    private String Backupphonenbr;
    private String Price;
    private String Status;
    private String receiver_id;
    // constructor
    public Courier(String CourierId, String Weight, String Address, String CollectDate, String ClientId, String Backupphonenbr, String Price, String Status, String Receiver_id) {
        this.CourierId = CourierId;
        this.Weight = Weight;
        this.Address = Address;
        this.CollectDate = CollectDate;
        this.ClientId = ClientId;
        this.Backupphonenbr = Backupphonenbr;
        this.Price = Price;
        this.Status = Status;
        this.receiver_id = Receiver_id;
    }
    // getters
    public String getCourierId() {
        return CourierId;
    }
    public String getWeight() {
        return Weight;
    }
    public String getAddress() {
        return Address;
    }
    public String getCollectDate() {
        return CollectDate;
    }
    public String getClientId() {
        return ClientId;
    }
    public String getBackupphonenbr() {
        return Backupphonenbr;
    }
    public String getPrice() {
        return Price;
    }
    public String getStatus() {
        return Status;
    }
    public String getReceiver() {
        return receiver_id;
    }

    
}
