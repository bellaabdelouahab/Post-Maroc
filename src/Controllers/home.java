package Controllers;

import java.io.IOException;
import java.sql.SQLException;

import Main.App;
import Main.DataBaseConnection;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
public class home {
    public DataBaseConnection connection;
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
        FXMLLoader loder = new FXMLLoader(getClass().getResource("../Resources/VIEW/um.fxml"));
        Pane root;
        try {
            root = loder.load();
            create_mail controller = loder.getController();
            controller.connection=connection;
            controller.fillinfo();
            App.changeStage(root);
        } catch (IOException e) {
            App.ShowNotificationWindow("error", "Could not load page close app and try again", null);
        }
    }
    @FXML
    private void SwitchMailLog() throws IOException{
        FXMLLoader loder = new FXMLLoader(getClass().getResource("../Resources/VIEW/MailLog.fxml"));
                Pane root = loder.load();
                mail_log controller = loder.getController();
                controller.connection=connection;
                try {
                    controller.SetData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                App.changeStage(root);
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
