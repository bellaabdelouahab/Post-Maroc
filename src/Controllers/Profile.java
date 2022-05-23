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
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

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
    private String[] namearray;

    public void Goback(ActionEvent e)  {
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(ChildPane1.translateXProperty(), -400, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.7), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        timeline.setOnFinished(event -> {
            parent.getChildren().remove(ChildPane1);
        });
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
        jobtitle.setText(useraccount.getjobtitle());
        Cin.setText(useraccount.getid());
        Nationnality.setText(useraccount.getnationnality());
        Phonenumber.setText(useraccount.getphone());
        ProfilePicture.setImage(useraccount.getImage());
    }

    @FXML
    public void update_info(ActionEvent event) throws Exception {
        namearray = FullName.getText().split(" ");
        UserAccount useraccount = connection.getUser_account();
        String FirstName = namearray[0].toString();
        // the rest is last name
        String LastName = namearray[1].toString();
        int len = namearray.length;
        for (int i = 2; i <len; i++) {
            LastName = LastName + " " + namearray[i].toString();
        }
        useraccount.setfirstname(FirstName);
        useraccount.setlastname(LastName);
        useraccount.setaddress(Adress.getText());
        useraccount.setphone(Phonenumber.getText());
        useraccount.setjobtitle(jobtitle.getText());
        new Thread(new Runnable() {
            @Override
            public void run() {
                connection.updateUser_account(useraccount);
            }
        }).start();
        // connection.updateUser_account(useraccount);
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