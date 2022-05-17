package Controllers.Employer;

import java.io.IOException;

import Main.App;
import Main.Employer_Connection;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class Home {
    private Employer_Connection connection;

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
            FXMLLoader loder = new FXMLLoader(getClass().getResource("/Resources/VIEW/Employer/Courier.fxml"));
            Pane root=new Pane();


            try{
                root = loder.load();
            }
            catch (Exception e) {
                System.out.println(System.getProperty("user.dir")+"/../Resources/VIEW/Employer/Courier.fxml");
                System.exit(0);
            }
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
    public void setConnection(Employer_Connection connection){
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
        App.ShowProfile();
    }
    @FXML
    private void CloseWindow() {
        App.CloseWindow();
    }
    @FXML   
    private void MinimizeWindow() {
        App.getpStage().setIconified(true);
    }
    @FXML
    private void Logout(){
        App.Logout();
    }

}
