package Controllers.Employer;

import java.io.IOException;

import Controllers.Profile;
import Main.App;
import Main.DataBaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class Home {
    private DataBaseConnection connection;
    // Switch To Waiting Courier
    @FXML
    private void SwitchToWaitingCourier() throws IOException{
        FXMLLoader loder = new FXMLLoader(getClass().getResource("../../Resources/VIEW/Employer/WaitingCourier.fxml"));
        Pane root = loder.load();
        WaitingCourier controller = loder.getController();
        controller.setConnection(connection);
        controller.SetData();
        App.changeStage(root);
    }
    //SwitchToAllCourier
    @FXML
    private void SwitchToAllCourier() throws IOException{
        FXMLLoader loder = new FXMLLoader(getClass().getResource("../../Resources/VIEW/Employer/AllCourier.fxml"));
        Pane root = loder.load();
        AllCourier controller = loder.getController();
        controller.setConnection(connection);
        controller.SetData();
        App.changeStage(root);
    }
    //SwitchToConfirmedCourier
    @FXML
    private void SwitchToConfirmedCourier() throws IOException{
        FXMLLoader loder = new FXMLLoader(getClass().getResource("../../Resources/VIEW/Employer/ConfirmedCourier.fxml"));
        Pane root = loder.load();
        ConfirmedCourier controller = loder.getController();
        controller.setConnection(connection);
        controller.SetData();
        App.changeStage(root);
    }
    // create a setter for Data Base Connection
    public void setConnection(DataBaseConnection connection){
        this.connection = connection;
    }
    @FXML
    public void showProfile() throws IOException{
        try {
            FXMLLoader loder = new FXMLLoader(getClass().getResource("../Resources/VIEW/Profile.fxml"));
            Pane root = loder.load();
            Profile controller = loder.getController();
            controller.connection=connection;
            App.changeStage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void CloseWindow() {
        connection.Disconnect();
        App.getpStage().close();
    }
    public void MinimizeWindow() {
        App.getpStage().setIconified(true);
    }

}
