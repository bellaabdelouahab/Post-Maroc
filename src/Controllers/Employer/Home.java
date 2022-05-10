package Controllers.Employer;

import java.io.IOException;

import Controllers.Profile;
import Main.App;
import Main.DataBaseConnection;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class Home {
    private DataBaseConnection connection;

    @FXML
    private Pane Pane1_button_animation;
    @FXML
    private Circle Pane1_circle_animation;

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
    private void StartAnimation1() {
        Timeline timeline = App.GetButtonAnimtation(Pane1_button_animation, Pane1_circle_animation,100,53);
        timeline.play();
    }
    @FXML
    private void EndAnimation1(){
        Timeline timeline = App.GetButtonAnimtation(Pane1_button_animation, Pane1_circle_animation,0,50);
        timeline.play();
    }
    @FXML
    private void showProfile(){
        try {
            FXMLLoader loder = new FXMLLoader(getClass().getResource("../Resources/VIEW/Profile.fxml"));
            Pane root = loder.load();
            Profile controller = loder.getController();
            controller.setConnection(connection);
            App.changeStage(root);
        } catch (IOException e) {
            e.printStackTrace();
            App.ShowNotificationWindow("error", "Could not load page close app and try again", null);
        }
    }
    @FXML
    private void CloseWindow() {
        App.CloseWindow();
    }
    @FXML   
    private void MinimizeWindow() {
        App.getpStage().setIconified(true);
    }

}
