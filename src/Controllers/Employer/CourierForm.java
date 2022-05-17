package Controllers.Employer;

import Controllers.Courier;
import Main.Employer_Connection;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class CourierForm {
    private Employer_Connection connection;
    @FXML Label Courier_id;
    @FXML Label Courier_Address;
    @FXML Label Courier_Price;
    @FXML Label Courier_Collect_Date;
    Courier courier;
    @FXML
    Pane PlaceToShowStatus;

    @FXML 
    private void SupportCourier(){
        connection.SupportCourier(Courier_id.getText());
    }
    @FXML
    private void CancelCourier(){
        connection.CancelCourier(Courier_id.getText());
    }

    public void setConnection(Employer_Connection connection) {
        this.connection = connection;
    }
}
