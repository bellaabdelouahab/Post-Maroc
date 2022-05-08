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
    private void SwitchToWaitingCourier() {
        SwitchTo(true,false,false);
    }
    
    //SwitchToAllCourier
    @FXML
    private void SwitchToAllCourier(){
        SwitchTo(false,false,true);
    }
    //SwitchToConfirmedCourier
    @FXML
    private void SwitchToConfirmedCourier(){
        SwitchTo(false,true,false);
    }
    // Switcher
    private void SwitchTo(Boolean Waiting, Boolean Confirmed,Boolean All)  {
        try {
            FXMLLoader loder = new FXMLLoader(getClass().getResource("../../Resources/VIEW/Employer/Courier.fxml"));
            Pane root;
            root = loder.load();
            CourierTable controller = loder.getController();
            controller.setConnection(connection);
            controller.SetData(Waiting, Confirmed,All);
            App.changeStage(root);
        } catch (IOException e) {
            // notifiy that it couldn't switch
            e.printStackTrace();
        }
        
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
