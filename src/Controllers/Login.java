package Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import org.controlsfx.control.ToggleSwitch;

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
    private Button close_btn;
    @FXML
    private ToggleSwitch toggleSwitch;
    @FXML
    private Pane subStage;
    public Stage presentStage;
    @FXML
    private void StartConnection() {
        try {
            if (connection.Login_user(email_field,password_text)) {
                FXMLLoader loder = new FXMLLoader(getClass().getResource("../Resources/VIEW/Home.fxml"));
                Pane root = loder.load();
                home controller = loder.getController();
                // controller.ParentPane=ParentPane;
                controller.connection=connection;
                // FadeOutLeft FideOut =new FadeOutLeft(ChiledStage);
                // FideOut.play();
                App.changeStage(root);
                Stage stage = App.getpStage();
                stage.setWidth(800);
                stage.setHeight(500);
                stage.setX(stage.getX()+49);
                stage.setY(stage.getY()+49);
                // WindowRoot.getChildren().clear();
                // WindowRoot.getChildren().add(root);
                // FideOut.setOnFinished(e->{
                    
                //     ParentPane.getChildren().remove(ChiledStage);
                // });
                // ParentPane.getChildren().add(root);
                // new FadeInRightBig(root).play();
            }
            else{
                System.out.println("💔 error");
            }
        }
        catch (Exception e) {
        System.out.println("ERREUR :( \n" + e);
        }
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