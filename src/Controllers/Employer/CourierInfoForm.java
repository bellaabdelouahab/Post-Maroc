package Controllers.Employer;

import Controllers.Courier;
import Main.Employer_Connection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CourierInfoForm {
    Employer_Connection connection;
    @FXML Label Courier_id;
    @FXML Label Courier_Address;
    @FXML Label Courier_Price;
    @FXML Label Courier_Collect_Date;
    @FXML Label clientfullname;
    @FXML Label receiverid;
    @FXML Label clientid;
    @FXML Label courierweight;
    @FXML Label courierstatus;
    @FXML Label creationdate;
    @FXML Label courierdiscription;
    @FXML Button closebutton;
    public CourierTable ParentController;
    
    public void Fillinfo(String CourierId,Boolean DeliveryLine){
        Courier courier = connection.getCourierbyid(CourierId,DeliveryLine);
        if(courier==null) {
            closebutton.fire();
            return;
        }
        Courier_id.setText(CourierId);
        Courier_Address.setText(courier.getAddress());
        Courier_Price.setText(courier.getPrice()+" DH");
        Courier_Collect_Date.setText(courier.getCollectDate());
        clientfullname.setText(connection.getclientFullname(courier.getClientId()));
        receiverid.setText(courier.getReceiver_id());
        clientid.setText(courier.getClientId());
        courierweight.setText(courier.getWeight()+" Kg");
        courierstatus.setText(courier.getStatus());
        creationdate.setText(courier.getCreationdate());
        courierdiscription.setText(courier.getDiscription());

    }
    public void setConnection(Employer_Connection connection) {
        this.connection = connection;
    }
}
