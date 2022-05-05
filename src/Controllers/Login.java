package Controllers;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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

    public DataBaseConnection connection;
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
            MaskerPane login_animation = new MaskerPane();
            login_animation.setText("Connecting");
            login_animation.setProgress(-1);
            login_animation.setLayoutX(306);
            login_animation.setLayoutY(159);
            subStage.getChildren().add(login_animation);
            Timeline timeline1 = new Timeline();
            KeyValue kvs = new KeyValue(login_animation.progressProperty(),-1, Interpolator.EASE_IN);
            KeyFrame kfs = new KeyFrame(Duration.seconds(1), kvs);
            timeline1.getKeyFrames().add(kfs);
            timeline1.play();
            timeline1.setOnFinished(ep->{
                if (connection.Login_user(email_field,password_text)) {
                    Timeline timeline = new Timeline();
                    KeyValue kv1 = new KeyValue(rightpane.translateXProperty(),-(900), Interpolator.EASE_IN);
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
                        if(connection.getUser_account().getaccounttype().equals("client")){
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Resources/VIEW/Home.fxml"));
                            Pane root;
                            try {
                                root = loader.load();home controller = loader.getController();
                                controller.connection=connection;
                                App.changeStage(root);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else if(connection.getUser_account().getaccounttype().equals("employer")){
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Resources/VIEW/Employer/Home.fxml"));
                            Pane root;
                            try {
                                root = loader.load();
                                Home controller = loader.getController();
                                controller.setConnection(connection);
                                App.changeStage(root);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            System.out.println(connection.getUser_account().getaccounttype().equals("client"));
                            System.exit(0);
                            // TODO: show notification for error
                        }
                        
                    });
                    timeline.play();
                
                }
                else{
                    subStage.getChildren().remove(login_animation);
                    System.out.println("💔 error");
                }
            });
    }

    public void CloseWindow() {
        connection.Disconnect();
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