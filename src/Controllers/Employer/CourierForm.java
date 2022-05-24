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
        new Thread(new Runnable() {
            @Override
            public void run() {
                connection.UpdateCourierStatus(Courier_id.getText(),"Supported");
            }
        }).start();
    }
    @FXML
    private void CancelCourier(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                connection.UpdateCourierStatus(Courier_id.getText(),"Canceled");
            }
        }).start();
    }

    public void setConnection(Employer_Connection connection) {
        this.connection = connection;
    }
}
