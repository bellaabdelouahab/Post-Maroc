package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Main.DataBaseConnection;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Login{
    public DataBaseConnection connection;
    @FXML
    private AnchorPane WindowRoot ;
    @FXML
    private TextField username_text;
    @FXML
    private TextField password_text;
    @FXML
    private TextField emai_label;
    @FXML
    private Button close_btn;
    @FXML
    private void StartConnection() {
        String x="";
        try {
            ResultSet rs = connection.Login_employ(username_text.getText().toLowerCase());
            
            while (rs.next()) {
                x = rs.getString(2).toLowerCase();
            }
            if (x.equals(password_text.getText())) {
                System.out.println("voila");
                FXMLLoader loder = new FXMLLoader(getClass().getResource("../Resources/VIEW/um.fxml"));
                Pane root = loder.load();
                create_mail controller = loder.getController();
                // controller.ParentPane=ParentPane;
                controller.connection=connection;
                // FadeOutLeft FideOut =new FadeOutLeft(ChiledStage);
                // FideOut.play();
                WindowRoot.getChildren().clear();
                WindowRoot.getChildren().add(root);
                root.setLayoutX(49);
                root.setLayoutY(49);
                // FideOut.setOnFinished(e->{
                    
                //     ParentPane.getChildren().remove(ChiledStage);
                // });
                // ParentPane.getChildren().add(root);
                // new FadeInRightBig(root).play();
            }
        }
        catch (Exception e) {
        System.out.println("ERREUR :( \n" + e);
        }
    }
    public void CloseWindow() {
        connection.Disconnect();
        Stage stage = (Stage)close_btn.getScene().getWindow();
        stage.close();
    }

    public void initializ() {
        StartConnection();
    }
}