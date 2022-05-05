package Controllers.Employer;

public class Receiver {
    private String FullName;
    private String ReceiverAddress;
    private String ReceiverPhonenbr;
    public Receiver(String fullName2, String receiverAddress, String receiverPhonenbr) {
        FullName = fullName2;
        ReceiverAddress = receiverAddress;
        ReceiverPhonenbr = receiverPhonenbr;
    }
    public String getFullName() {
        return FullName;
    }
    public String getReceiverAddress() {
        return ReceiverAddress;
    }
    public String getReceiverPhonenbr() {
        return ReceiverPhonenbr;
    }
    
    
}
