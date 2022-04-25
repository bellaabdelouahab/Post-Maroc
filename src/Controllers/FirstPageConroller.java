package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import animatefx.animation.Jello;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
public class FirstPageConroller implements Initializable{
    public void SwitchToAdminPage(ActionEvent event) throws IOException {
        
    }

    // Switch To Employer Page
    public void SwitchToEmployerPage(ActionEvent event) throws IOException, SQLException {
        
    }
    public void CloseWindow() {
    }
    public void ExitHover(MouseEvent event){
        new Jello((Node) event.getSource()).play();
        ((Button) event.getSource()).setStyle("-fx-background-color:#f70a0a");
    }
    public void ExitHoverOut(MouseEvent event){
        ((Button) event.getSource()).setStyle("-fx-background-color: #00000000");
    }
    public void MinimizeWindow(){
        // Stage stage = (Stage)ParentPane.getScene().getWindow();
        // stage.setIconified(true);
    }
    public void returnHeaderBlack(){
        // new FadeOutLeft(Rectan).play();
        // new FadeOutLeft(Polygona).play();
        // header.getChildren().remove(Rectan);
        // header.getChildren().remove(Polygona);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    /////The only Place To initialize The DataBase connection
    // Connection.ConnectToDataBase();
    }
}
