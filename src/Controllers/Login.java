package Controllers;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.ToggleSwitch;

import Controllers.Employer.Home;
import Main.App;
import Main.DataBaseConnection;

public class Login{

    private DataBaseConnection connection;
    @FXML
    private AnchorPane WindowRoot ;
    @FXML
    private TextField email_field;
    @FXML
    private TextField password_text;
    @FXML
    private Button Loging_btn;
    @FXML
    private ToggleSwitch toggleSwitch;
    @FXML
    private Pane subStage;
    @FXML
    private AnchorPane rightpane;
    public Stage presentStage;
    @FXML
    private void StartConnection() {
        // TODO make sure this function is called only once
            ProgressIndicator login_animation = new ProgressIndicator();
            login_animation.setLayoutX(550);
            login_animation.setLayoutY(523);
            login_animation.setProgress(-1);
            // wait freez for 2 se
            rightpane.getChildren().add(login_animation);
            Boolean isConnected = this.connection.Login_user(email_field,password_text);
            if (isConnected) {
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
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Resources/VIEW/Home.fxml"));
                        Pane root;
                        try {
                            root = loader.load();home controller = loader.getController();
                            controller.connection=this.connection;
                            App.changeStage(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(this.connection.getUser_account().getaccounttype().equals("employer")){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Resources/VIEW/Employer/Home.fxml"));
                        Pane root;
                        try {
                            root = loader.load();
                            Home controller = loader.getController();
                            controller.setConnection(this.connection);
                            App.changeStage(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                timeline.play();
            }
            else{
                subStage.getChildren().remove(login_animation);
                System.out.println("💔 error");
            }
    }

    public void setConnection(DataBaseConnection connection) {
        this.connection = connection;
    }

    public void CloseWindow() {
        this.connection.Disconnect();
        App.getpStage().close();
    }
    public void MinimizeWindow() {
        App.getpStage().setIconified(true);
    }
    public void initializ() {
        // StartConnection();
        // System.out.println("ERREUR :( \n" + "initializ");
        // toggleSwitch.setOnMouseEntered(arg0 -> {
        //     toggleSwitch.setText("helo");
        // });
    }
    public void ChangeTheme() {
        if (toggleSwitch.isSelected()) {
            subStage.setStyle(subStage.getStyle()+"-fx-background-color:  #444444;");
        } else {
            subStage.setStyle(subStage.getStyle()+"-fx-background-color: #f2f2f2;");
        }
    }

    public AnchorPane getWindowRoot() {
        return WindowRoot;
    }

    public void setWindowRoot(AnchorPane windowRoot) {
        this.WindowRoot = windowRoot;
    }
}