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
    CourierTable PrentController;
    @FXML
    private void Show_more(){
        PrentController.ShowAllinfo(Courier_id.getText());
    }
    @FXML 
    private void SupportCourier(){
        connection.UpdateCourierStatus(Courier_id.getText(),"Supported");
    }
    @FXML
    private void CancelCourier(){
        connection.UpdateCourierStatus(Courier_id.getText(),"Canceled");
    }

    public void setConnection(Employer_Connection connection) {
        this.connection = connection;
    }
}
