package Controllers;

import java.io.IOException;
import java.util.prefs.Preferences;

import com.jfoenix.controls.JFXCheckBox;

import Controllers.Employer.Home;
import Main.App;
import Main.DataBaseConnection;
import animatefx.animation.FadeIn;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Login{

    private DataBaseConnection connection;
    @FXML
    private AnchorPane WindowRoot ;
    @FXML
    private TextField email_field;
    @FXML
    private TextField password_field;
    @FXML
    private Line email_error_line,password_error_line;
    @FXML
    private ImageView email_error_circle,password_error_circle;
    @FXML
    private JFXCheckBox remember_me;
    @FXML
    private Pane subStage;
    @FXML
    private AnchorPane rightpane;
    public Stage presentStage;
    Preferences preferences;
    @FXML
    private void StartConnection() {
            connection = new DataBaseConnection();
            // TODO make sure this function is called only once
            ProgressIndicator login_animation = new ProgressIndicator();
            login_animation.setLayoutX(550);
            login_animation.setLayoutY(523);
            login_animation.setProgress(-1);
            // wait freez for 2 se
            rightpane.getChildren().add(login_animation);
            Boolean isConnected = this.connection.Login_user(email_field,password_field,email_error_line,email_error_circle,password_error_line,password_error_circle);
            if (!isConnected) {
                rightpane.getChildren().remove(login_animation);
                System.out.println("💔 error");
                return;
            }
            Timeline timeline = new Timeline();
            KeyValue kv1 = new KeyValue(rightpane.translateXProperty(),-(440), Interpolator.EASE_IN);
            KeyFrame kf1 = new KeyFrame(Duration.seconds(1), kv1);
            timeline.getKeyFrames().add(kf1);
            timeline.setOnFinished(t -> {
                subStage.getChildren().remove(rightpane);
                subStage.getChildren().remove(login_animation);
                Stage stage = App.getpStage();
                stage.setX(stage.getX()+49);
                stage.setY(stage.getY()+49);
                stage.setWidth(800);
                stage.setHeight(500);
                subStage.setLayoutX(0);
                subStage.setLayoutY(0);
                
                if(this.connection.getUser_account().getaccounttype().equals("client")){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/VIEW/Home.fxml"));
                    Pane root;
                    try {
                        root = loader.load();home controller = loader.getController();
                        controller.connection=this.connection;
                        App.changeStage(root);
                        new FadeIn(root).play();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(this.connection.getUser_account().getaccounttype().equals("employer")){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/VIEW/Employer/Home.fxml"));
                    Pane root;
                    try {
                        root = loader.load();
                        Home controller = loader.getController();
                        controller.setConnection(this.connection);
                        App.changeStage(root);
                        new FadeIn(root).play();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // check if remeber me is checked
                if(!remember_me.isSelected()){
                    preferences = Preferences.userRoot().node("Login"); 
                    preferences.remove("email");
                    preferences.remove("password");
                    return;
                }   
                preferences = Preferences.userRoot().node("Login");
                preferences.put("email", email_field.getText());
                preferences.put("password", password_field.getText());
            });
            timeline.play();
    }
    @FXML
    private void clear_email() {
        email_field.setText("");
        email_error_circle.setVisible(false);
        email_error_line.setVisible(false);
    }
    @FXML
    private void clear_password() {
        password_field.setText("");
        password_error_circle.setVisible(false);
        password_error_line.setVisible(false);
    }
    public void setConnection(DataBaseConnection connection) {
        this.connection = connection;
    }

    public void CloseWindow() {
        System.exit(0);
    }
    public void MinimizeWindow() {
        App.getpStage().setIconified(true);
    }
    public void initializ() {
        // check if there is data in seassion
        preferences = Preferences.userRoot().node("Login");
        if(preferences.get("email",null)==null){
            remember_me.setSelected(false);
            return;
        }
        email_field.setText(preferences.get("email",null));
        password_field.setText(preferences.get("password",null));
        remember_me.setSelected(true);
    }
    public AnchorPane getWindowRoot() {
        return WindowRoot;
    }
    public void setWindowRoot(AnchorPane windowRoot) {
        this.WindowRoot = windowRoot;
    }
    public DataBaseConnection getConnection() {
        return connection;
    }
}