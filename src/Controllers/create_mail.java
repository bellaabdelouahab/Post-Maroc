package Controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import Main.App;
import Main.DataBaseConnection;
import Main.UserAccount;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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
        }catch(Exception E){
            Button btn = new Button("OK");
            // close notification window
            btn.setOnAction(e -> App.closeNotification());
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn);
            btns.add(btn);
            App.ShowNotificationWindow("Error",  "Please enter a valid weight",btns);
            return;
        }
        LocalDate collect_date_ = collect_date.getValue();
        
        if(!(cin_.length() == 8 || cin_.length() == 7)){
            Button btn = new Button("OK");
            // close notification window
            btn.setOnAction(e -> App.closeNotification());
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn);
            App.ShowNotificationWindow("Error",  "Please enter a valid CIN - 7 or 8 digits got :"+cin_.length(),btns);
            return;
        }
        // check cin with regex /^[A-Za-z]{2}/
        if(!cin_.matches("[A-Za-z]{2}[0-9]{6}") && !cin_.matches("[A-Za-z]{1}[0-9]{7}")){
            Button btn = new Button("OK");
            // close notification window
            btn.setOnAction(e -> App.closeNotification());
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn);
            App.ShowNotificationWindow("Error",  "Please enter a valid CIN",btns);
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
