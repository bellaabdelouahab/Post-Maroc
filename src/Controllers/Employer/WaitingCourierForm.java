package Controllers.Employer;

import Main.DataBaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class WaitingCourierForm {
    private DataBaseConnection connection;
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

    public void setConnection(DataBaseConnection connection) {
        this.connection = connection;
    }
}
