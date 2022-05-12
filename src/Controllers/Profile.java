package Controllers;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

import Main.DataBaseConnection;
import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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
    private DataBaseConnection connection;

    public String compte;
    BufferedImage ImagebBufferedImage;
    public GNAvatarView HomeProfilePicture;
    private Pane parent;

    public void Goback(ActionEvent e)  {
            parent.getChildren().remove(ChildPane1);
    }

    public Pane getParent() {
        return parent;
    }

    public void setParent(Pane parent) {
        this.parent = parent;
    }

    public void setConnection(DataBaseConnection connection) {
        this.connection = connection;
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