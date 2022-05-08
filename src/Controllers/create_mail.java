package Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Main.App;
import Main.DataBaseConnection;
import Main.UserAccount;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class create_mail implements Initializable{
    public DataBaseConnection connection;
    @FXML
    private TextField cin, first_name, last_name, natio, phonenbr,address,weight,Pricefield,receiverFullname,receiveraddress,receiverphonenbr;
    @FXML
    private DatePicker collect_date;
    @FXML
    private ComboBox<String> combohour,combominutes;
    @FXML
    private void ValidateMail(){
        // get details from text fields
        String cin_ = cin.getText();
        String phonenbr_ = phonenbr.getText();
        String address_ = address.getText();
        Float weight_ = 0f;
        Float price_ = 0f;
        try{
        weight_ = weight.getText().isEmpty() ? 0 : Float.parseFloat(weight.getText());
        price_ = Pricefield.getText().isEmpty() ? 0 : Float.parseFloat(Pricefield.getText());
        CheckPriceField();
        }catch(Exception E){
            Button btn = new Button("OK");
            // close notification window
            btn.setOnAction(e -> App.closeNotification());
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn);
            App.ShowNotificationWindow("Error",  "Please enter a valid weight",btns);
            return;
        }
        // get date from text fields
        LocalDate collect_date_ = collect_date.getValue();
        String collectHour = combohour.getValue();
        String collectMinutes = combominutes.getValue();
        String time_ = collectHour + ":" + collectMinutes;
        // check if the entered date bigger  current date
        if(!collect_date_.isAfter(LocalDate.now()) && !collect_date_.isEqual(LocalDate.now())){
            // notify
            Button btn = new Button("OK");
            // close notification window
            btn.setOnAction(e -> App.closeNotification());
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn);
            App.ShowNotificationWindow("Error",  "Please enter a valid date",btns);
            System.out.println(collect_date_+"<==>"+LocalDate.now());
            return;
        }
        // if the entered date is equal to current date check time
        if(collect_date_.isEqual(LocalDate.now())){
            // check time
            // get current hour and minutes
            String[] currentTime = java.time.LocalDateTime.now().toString().split(":");
            System.out.println(currentTime[0].substring(11, 13)+"<==>"+time_);
            if(time_.compareTo(LocalDate.now().toString()) > 0){
                // notify
                Button btn = new Button("OK");
                // close notification window
                btn.setOnAction(e -> App.closeNotification());
                ArrayList<Button> btns = new ArrayList<Button>();
                btns.add(btn);
                App.ShowNotificationWindow("Error",  "Please enter a valid time",btns);
                System.out.println(time_+"<==>"+LocalDate.now().toString()+"\n "+time_.compareTo(LocalDate.now().toString()));
                // System.out.println(collect_date_+"<==>"+LocalDate.now().);
                return;
            }
            
            System.out.println(time_+"<==>"+LocalDate.now().toString()+"\n "+time_.compareTo(LocalDate.now().toString()));
        }
        
        // get receiver information from text fields
        String receiverFullname_ = receiverFullname.getText();
        String receiveraddress_ = receiveraddress.getText();
        String receiverphonenbr_ = receiverphonenbr.getText();
        // check if all fields are filled
        if(cin_==null || phonenbr_==null || address_==null || collectHour==null || collectMinutes==null || receiverFullname_==null || receiveraddress_==null || receiverphonenbr_==null){
            App.ShowNotificationWindow("Error", "Please fill all fields",null);
        }  
        // check cin
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
        connection.AddMail( weight_,cin_,phonenbr_,price_ ,address_,collect_date_,collectHour,collectMinutes,receiverFullname_,receiveraddress_,receiverphonenbr_);
        
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
    
    private void CheckPriceField() {
        try{
            Float weight_ = weight.getText().isEmpty() ? 0 : Float.parseFloat(weight.getText());
            Float price = connection.CalculatePrice(weight_);
            // price to String
            String price_ = String.valueOf(price);
            Pricefield.setText(price_);
        }catch(Exception E){
            System.out.println(E);
            Button btn = new Button("OK");
            // close notification window
            btn.setOnAction(e1 -> App.closeNotification());
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn);
            App.ShowNotificationWindow("Error",  "Please enter a valid weight",btns);
            // TODO : add error message
            return;
        }
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        LocalDate DateIn = LocalDate.now();
        collect_date.setValue(DateIn);
        System.out.println("action added");
        weight.setOnKeyReleased((e) -> {
            CheckPriceField();
        });
        // add hours to the combohour
        for(int i = 8; i < 18; i++){
            combohour.getItems().add(String.format("%02d", i));
        }
        // add minutes to the combominutes
        for(int i = 0; i < 60; i++){
            combominutes.getItems().add(String.format("%02d", i));
        }
        
    }
}
