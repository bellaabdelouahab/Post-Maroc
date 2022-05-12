package Controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import Main.DataBaseConnection;
import Main.UserAccount;
import io.github.gleidson28.GNAvatarView;
import javafx.embed.swing.SwingFXUtils;
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
    private TextField FullName, Sex, email, Adress, jobtitle, Cin, Nationnality, Phonenumber;
    @FXML
    private GridPane PasswordForm;
    private DataBaseConnection connection;

    public String compte;
    BufferedImage ImagebBufferedImage;
    public GNAvatarView HomeProfilePicture;
    private Pane parent;
    private String user_id;

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
        FillProfileData();
    }

    public void FileChooser() {
        try{
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "PNG","PNG");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
            BufferedImage imagebBufferedImage = ImageIO.read(chooser.getSelectedFile());
            ImagebBufferedImage = imagebBufferedImage;
            Image Image1 = SwingFXUtils.toFXImage(ImagebBufferedImage, null);
            ProfilePicture.setImage(Image1);
            }
        }
        catch(Exception e){
            System.out.println("No image selected");
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    public void FillProfileData() {
        UserAccount useraccount = connection.getUser_account();
        FullName.setText(useraccount.getfirstname()+" "+useraccount.getlastname());
        email.setText(useraccount.getemail());
        Adress.setText(useraccount.getaddress());
        Sex.setText(useraccount.getgender());
        jobtitle.setText(useraccount.jobtitle());
        Cin.setText(useraccount.getid());
        Nationnality.setText(useraccount.getnationnality());
        Phonenumber.setText(useraccount.getphone());
        ProfilePicture.setImage(useraccount.getImage());
    }

    @FXML
    public void update_info(ActionEvent event) throws Exception {
        try{
        user_id = connection.getUser_account().getid();
        File file = new File(System.getProperty("user.dir")+ "/src/Resources/IMAGES/ProfilePictures/"+user_id+".png");
        ImageIO.write(ImagebBufferedImage, "png", file);
        }
        catch (Exception e) {
            System.out.println("No image selected");
        }
    }

}