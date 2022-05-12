package Controllers.Client;

import java.io.IOException;
import java.sql.SQLException;

import Controllers.Profile;
import Main.App;
import Main.DataBaseConnection;
import animatefx.animation.FadeIn;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
public class client_home {
    private DataBaseConnection connection;
    @FXML
    private Pane Pane1_button_animation;
    @FXML
    private Pane Pane2_button_animation;
    @FXML
    private Circle Pane1_circle_animation;
    @FXML
    private Circle Pane2_circle_animation;
    
    @FXML
    private void SwitchToAddMailForm() {
        FXMLLoader loder = new FXMLLoader(getClass().getResource("/Resources/VIEW/Client/um.fxml"));
        Pane root;
        try {
            root = loder.load();
            create_mail controller = loder.getController();
            controller.connection=getConnection();
            controller.fillinfo();
            App.changeStage(root);
        } catch (IOException e) {
            e.printStackTrace();
            App.ShowNotificationWindow("error", "Could not load page close app and try again", null);
        }
    }
    public DataBaseConnection getConnection() {
        return connection;
    }
    public void setConnection(DataBaseConnection connection) {
        this.connection = connection;
    }
    @FXML
    private void SwitchMailLog() {
        FXMLLoader loder = new FXMLLoader(getClass().getResource("/Resources/VIEW/Client/MailLog.fxml"));
        Pane root;
        try {
            root = loder.load();
            App.changeStage(root);
            try {
                mail_log controller = loder.getController();
                controller.connection=getConnection();
                controller.SetData();
            } catch (SQLException e) {
                e.printStackTrace();
                App.ShowNotificationWindow("error", "Could not load page close app and try again", null);
                return;
            }
        } 
        catch (IOException e1) {
            e1.printStackTrace();
            App.ShowNotificationWindow("error", "Could not load data close app and try again", null);
        }      
    }
    @FXML
    private void StartAnimation1() {
        Timeline timeline = App.GetButtonAnimtation(Pane1_button_animation, Pane1_circle_animation,100,53);
        timeline.play();
    }
    @FXML
    private void StartAnimation2() {
        Timeline timeline = App.GetButtonAnimtation(Pane2_button_animation, Pane2_circle_animation,100,53);
        timeline.play();
    }
    @FXML
    private void EndAnimation1(){
        Timeline timeline = App.GetButtonAnimtation(Pane1_button_animation, Pane1_circle_animation,0,50);
        timeline.play();
    }
    @FXML
    private void EndAnimation2(){
        Timeline timeline = App.GetButtonAnimtation(Pane2_button_animation, Pane2_circle_animation,0,50);
        timeline.play();

    }
    @FXML
    private void showProfile(){
        try {
            FXMLLoader loder = new FXMLLoader(getClass().getResource("/Resources/VIEW/Profile.fxml"));
            Pane root = loder.load();
            Profile controller = loder.getController();
            controller.setConnection(getConnection());
            Pane parent = (Pane) App.getpStage().getScene().getRoot().getChildrenUnmodifiable().get(0);
            parent.getChildren().add(root);
            FadeIn fadeIn = new FadeIn(root);
            fadeIn.play();
            controller.setParent(parent);
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
    @FXML
    private void Logout(){
        App.Logout();
    }
}
