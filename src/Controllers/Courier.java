package Controllers;

public class Courier {
    // This class will be used to control the courier page
    private String CourierId;
    private Float Weight;
    private String Address;
    private String CollectDate;
    private String ClientId;
    private String Backupphonenbr;
    private Float Price;
    private String Status;
    private String receiver_id;
    private Receiver receiver;
    private String discription;
    private String creationdate;
    // constructor
    public Courier(String CourierId, Float Weight, String Address, String CollectDate, String ClientId, String Backupphonenbr, Float Price, String Status, String Receiver_id, String creationdate, String discription) {
        this.CourierId = CourierId;
        this.Weight = Weight;
        this.Address = Address;
        this.CollectDate = CollectDate;
        this.ClientId = ClientId;
        this.Backupphonenbr = Backupphonenbr;
        this.Price = Price;
        this.Status = Status;
        this.receiver_id = Receiver_id;
        this.creationdate = creationdate;
        this.discription = discription;
    }
    public Courier(){
        this.CourierId = "";
        this.Weight = 0f;
        this.Address = "";
        this.CollectDate = "";
        this.ClientId = "";
        this.Backupphonenbr = "";
        this.Price = 0.1f;
        this.Status = "";
    }
    // getters
    public String getCourierId() {
        return CourierId;
    }
    public Float getWeight() {
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
    public Float getPrice() {
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
    public String getDiscription() {
        return discription;
    }
    public String getCreationdate() {
        return creationdate;
    }
    // setters
    public void setCourierId(String CourierId) {
        this.CourierId = CourierId;
    }
    public void setWeight(Float weight_) {
        this.Weight = weight_;
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
    public void setPrice(Float price_) {
        this.Price = price_;
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
    public void setDiscription(String text) {
        this.discription = text;
    }
    
}
