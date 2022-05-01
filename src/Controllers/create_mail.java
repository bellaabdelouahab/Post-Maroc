package Controllers;

import java.time.LocalDate;

import Main.App;
import Main.DataBaseConnection;
import Main.UserAccount;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class create_mail {
    public DataBaseConnection connection;
    @FXML
    private TextField cin, first_name, last_name, natio, phonenbr,address,weight;
    @FXML
    private DatePicker collect_date;
    @FXML
    private void ValidateMail(){
        // get details from text fields
        String cin_ = cin.getText();
        String phonenbr_ = phonenbr.getText();
        String address_ = address.getText();
        Float weight_ = 0f;
        try{
        weight_ = weight.getText().isEmpty() ? 0 : Float.parseFloat(weight.getText());
        }catch(Exception e){
            App.showAlert("Error",  "Please enter a valid weight");
            return;
        }
        LocalDate collect_date_ = collect_date.getValue();
        
        if(!(cin_.length() == 8 || cin_.length() == 7)){
            App.showAlert("Error",  "Please enter a valid CIN - 7 or 8 digits got :"+cin_.length());
            return;
        }
        // check cin with regex /^[A-Za-z]{2}/
        if(!cin_.matches("[A-Za-z]{2}[0-9]{6}") && !cin_.matches("[A-Za-z]{1}[0-9]{7}")){
            App.showAlert("Error",  "Please enter a valid CIN");
            return;
        }

        
        // add details 
        connection.AddMail( weight_,cin_,phonenbr_, address_,collect_date_ );
        
    }
    // fill info details
    public void fillinfo() {
        UserAccount user_account = connection.getuserclass();
        first_name.setText(user_account.getfirstname());
        last_name.setText(user_account.getlastname());
        natio.setText(user_account.getnationnality());
        cin.setText(user_account.getid());
        address.setText(user_account.getaddress());
        phonenbr.setText(user_account.getphone());
    }
    @FXML
    private void switchToHomePage(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Resources/VIEW/Home.fxml"));
            Pane root = loader.load();
            home controller = loader.getController();
            controller.connection=connection;
            // get window parentstage
            App.changeStage(root);
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
}
