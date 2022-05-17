package Controllers;

import java.io.IOException;
import java.util.prefs.Preferences;

import com.jfoenix.controls.JFXCheckBox;

import Controllers.Client.client_home;
import Controllers.Employer.Home;
import Main.App;
import Main.Client_Connection;
import Main.DataBaseConnection;
import Main.Employer_Connection;
import animatefx.animation.FadeIn;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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
    private Button Loging_btn;
    @FXML
    private JFXCheckBox remember_me;
    @FXML
    private Pane subStage;
    @FXML
    private AnchorPane rightpane;

    public Stage presentStage;
    
    Preferences preferences;

    ProgressIndicator login_animation;
    
    Task<Void> task;

    @FXML
    private void StartConnection() {
        login_animation = new ProgressIndicator();
        login_animation.setLayoutX(550);
        login_animation.setLayoutY(523);
        login_animation.setProgress(-1);
        rightpane.getChildren().add(login_animation);
        Loging_btn.setDisable(true);
        Timeline tl = new Timeline();
        KeyFrame kf = new KeyFrame(Duration.seconds(1), new KeyValue(login_animation.progressProperty(),-(1), Interpolator.EASE_IN));
        tl.getKeyFrames().add(kf);
        tl.setOnFinished(t -> {
            if (connection == null)   tl.play();
            else{
                // Start Data base Connection using javafx Task
                Boolean isConnected = connection.Login_user(email_field,password_field,
                                                            email_error_line,email_error_circle,
                                                            password_error_line,password_error_circle);
                if (!isConnected) {
                    rightpane.getChildren().remove(login_animation);
                    Loging_btn.setDisable(false);
                    System.out.println("Not connected");
                    return;
                }
                Timeline timeline = new Timeline();
                KeyFrame kf1 = new KeyFrame(Duration.seconds(1), new KeyValue(rightpane.translateXProperty(),-(440), Interpolator.EASE_IN));
                timeline.getKeyFrames().add(kf1);
                timeline.setOnFinished(e -> {
                    LoadNextPage();
                });
                timeline.play();      
            }
        });
        tl.play();
        return;
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
        // check for data in seassion
        preferences = Preferences.userRoot().node("Login");
        if(preferences.get("email",null)==null){
            remember_me.setSelected(false);
            return;
        }
        email_field.setText(preferences.get("email",null));
        password_field.setText(preferences.get("password",null));
        remember_me.setSelected(true);
    }
    private void LoadNextPage() {
        subStage.getChildren().remove(rightpane);
        Stage stage = App.getpStage();
        stage.setX(stage.getX()+49);
        stage.setY(stage.getY()+49);
        stage.setWidth(800);
        stage.setHeight(500);
        subStage.setLayoutX(0);
        subStage.setLayoutY(0);
        Pane root= new Pane();
        if(this.connection.getUser_account().getaccounttype().equals("client")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/VIEW/Client/Home.fxml"));
            try {
                root = loader.load();
                client_home controller = loader.getController();
                controller.setConnection(new Client_Connection(false));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        else if(this.connection.getUser_account().getaccounttype().equals("employer")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/VIEW/Employer/Home.fxml"));
            try {
                root = loader.load();
                Home controller = loader.getController();
                controller.setConnection(new Employer_Connection(false));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        App.changeStage(root);
        new FadeIn(root).play();
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