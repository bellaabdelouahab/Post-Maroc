package Controllers;

import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import Main.App;
import Main.DataBaseConnection;

public class Profile implements Initializable {
    @FXML
    private Pane ChildPane1;
    @FXML
    private GNAvatarView ProfilePicture;
    @FXML
    private TextField FullName, Sex, email, Adress, Age, Cin, Nationnality, Phonenumber;
    @FXML
    private GridPane PasswordForm;
    Image Image1;
    public DataBaseConnection connection;
    // private String adr = "", natio = "", phone = "", cin = "", age = "", password = "";
    // private int result = 0;

    public String compte;
    BufferedImage ImagebBufferedImage;
    public GNAvatarView HomeProfilePicture;

    public void Goback(ActionEvent e) throws IOException {
        try {
            FXMLLoader loder = new FXMLLoader(getClass().getResource("../Resources/VIEW/Home.fxml"));
            Pane root = loder.load();
            home controller = loder.getController();
            controller.connection=connection;
            App.changeStage(root);
        } catch (IOException E) {
            // TODO Auto-generated catch block
            E.printStackTrace();
        }
    }

    public void FileChooser() {
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    public void FillProfileData() {
    }

    @FXML
    public void update_info(ActionEvent event) throws Exception {
    }

}