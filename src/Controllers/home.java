package Controllers;

import java.io.IOException;
import java.sql.SQLException;

import Main.App;
import Main.DataBaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
public class home {
    public DataBaseConnection connection;
    
    @FXML
    private void SwitchToAddMailForm() throws IOException{
        FXMLLoader loder = new FXMLLoader(getClass().getResource("../Resources/VIEW/um.fxml"));
                Pane root = loder.load();
                create_mail controller = loder.getController();
                controller.connection=connection;
                controller.fillinfo();
                App.changeStage(root);
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
