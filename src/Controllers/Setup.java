package Controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import Main.App;
import Main.Tools_;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class Setup implements Initializable{
    private String Chosen_path;
    @FXML private TextField Path;
    private Stage primaryStage;
    @FXML
    private void SelectFolder(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory == null) {
            return;
        }
        Chosen_path = selectedDirectory.getPath();
        Path.setText(Chosen_path);
    }
    @FXML
    private void Finish(){
        if(Chosen_path.equals("")){
            App.CurrentNotification = "Please select a folder";
            return;
        }
        File pathToUploads;
        try{
            pathToUploads = new File(Chosen_path);
        }catch(Exception e){
            App.CurrentNotification = "Unauthorized folder, try running the program as administrator";
            return;
        }
        if (!pathToUploads.exists() || !pathToUploads.isDirectory()) {
            App.CurrentNotification = "Please select a valid folder";
            return; 
        }
        try{
            new File(Chosen_path +"\\Post-Maroc\\wallet").mkdirs();
            new File(Chosen_path +"\\Post-Maroc\\assets").mkdirs();
            new File(Chosen_path +"\\Post-Maroc\\assets\\ProfilePictures").mkdirs();
            ArrayList<String> files = new ArrayList<String>();
            files.add("cwallet.sso");
            files.add("ewallet.p12");
            files.add("ewallet.pem");
            files.add("keystore.jks");
            files.add("ojdbc.properties");
            files.add("sqlnet.ora");
            files.add("tnsnames.ora");
            files.add("truststore.jks");
            for (String file : files) {
                Tools_.copyInputStreamToFile(App.class.getResourceAsStream("/Resources/Wallet_NFS315/"+file),
                                             new File(Chosen_path +"\\Post-Maroc\\wallet\\"+file));
            }
            files.clear();
            files.add("CourierForm.docx");
            files.add("CourierFormCreator.exe");
            files.add("CurrentCourierInfo.txt");
            files.add("Barcode.png");
            for (String file : files) {
                Tools_.copyInputStreamToFile(App.class.getResourceAsStream("/Resources/OutputCourierForm/"+file),
                                             new File(Chosen_path +"\\Post-Maroc\\assets\\"+file));
            }
            Preferences preferences = Preferences.userRoot().node("Init");
            preferences.put("Path_To_Resources", Chosen_path +"\\Post-Maroc");
            App.path_to_dependencies= Chosen_path +"\\Post-Maroc";
            System.out.println("good now for login");
            App.ShowLogin(primaryStage);
        }
        catch (Exception e){
            e.printStackTrace();
            App.CurrentNotification="Error in Setup, check your path";
        }
    }
    @FXML
    private void Cancel(){
        System.exit(0);
    }
    @FXML 
    private void CloseWindow(){
        System.exit(0);
    }
    public void setprimaryStage(Stage primaryStage) {
        this.primaryStage=primaryStage;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(System.getProperty("os.name").startsWith("Windows"))
        Path.setText(System.getenv("ProgramFiles"));

    }
}
