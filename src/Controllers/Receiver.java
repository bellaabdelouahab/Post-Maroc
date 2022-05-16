package Controllers;

public class Receiver {
    private String FirstName;
    private String LastName;
    private String ReceiverAddress;
    private String ReceiverPhonenbr;
    public Receiver(String firstName, String lastName, String receiverAddress, String receiverPhonenbr) {
        FirstName = firstName;
        LastName = lastName;
        ReceiverAddress = receiverAddress;
        ReceiverPhonenbr = receiverPhonenbr;
    }
    public String getFirstName() {
        return FirstName;
    }
    public String getLastName() {
        return LastName;
    }
    public String getReceiverAddress() {
        return ReceiverAddress;
    }
    public String getReceiverPhonenbr() {
        return ReceiverPhonenbr;
    }
    
    
}
