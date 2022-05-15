package Controllers;

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
    private Receiver receiver;
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
    public Courier(){
        this.CourierId = "";
        this.Weight = "";
        this.Address = "";
        this.CollectDate = "";
        this.ClientId = "";
        this.Backupphonenbr = "";
        this.Price = "";
        this.Status = "";
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
    public Receiver getReceiver() {
        return receiver;
    }
    public String getReceiver_id() {
        return receiver_id;
    }
    // setters
    public void setCourierId(String CourierId) {
        this.CourierId = CourierId;
    }
    public void setWeight(String Weight) {
        this.Weight = Weight;
    }
    public void setAddress(String Address) {
        this.Address = Address;
    }
    public void setCollectDate(String CollectDate) {
        this.CollectDate = CollectDate;
    }
    public void setClientId(String ClientId) {
        this.ClientId = ClientId;
    }
    public void setBackupphonenbr(String Backupphonenbr) {
        this.Backupphonenbr = Backupphonenbr;
    }
    public void setPrice(String Price) {
        this.Price = Price;
    }
    public void setStatus(String Status) {
        this.Status = Status;
    }
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    
}
