package Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Main.App;
import Main.DataBaseConnection;
import Main.UserAccount;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class create_mail implements Initializable{
    DataBaseConnection connection;
    @FXML
    private TextField first_name, last_name,phonenbr,address,weight,Pricefield,receiverFirstname,receiverLastname,receiveraddress,receiverphonenbr;
    @FXML
    private DatePicker collect_date;
    @FXML
    private ComboBox<String> combohour,combominutes;
    @FXML
    private void ValidateMail(){
        // get details from text fields
        String cin_ = connection.getuserclass().getid();
        Float weight_ = 0f;
        Float price_ = 0f;
        try{
        weight_ = weight.getText().isEmpty() ? 0 : Float.parseFloat(weight.getText());
        price_ = Pricefield.getText().isEmpty() ? 0 : Float.parseFloat(Pricefield.getText());
        CheckPriceField();
        }catch(Exception E){
            App.ShowNotificationWindow("Error",  "Please enter a valid weight",null);
            return;
        }
        // get date from text fields
        LocalDate collect_date_ = collect_date.getValue();
        String collectHour = combohour.getValue();
        String collectMinutes = combominutes.getValue();
        // check if the entered date bigger  current date
        if(!collect_date_.isAfter(LocalDate.now()) ){
            App.ShowNotificationWindow("Error",  "Please enter a valid date",null);
            System.out.println(collect_date_+"<==>"+LocalDate.now());
            return;
        }
        
        // get receiver information from text fields
        String receiverFirstname_ = receiverFirstname.getText();
        String receiverLastname_ = receiverLastname.getText();
        String receiveraddress_ = receiveraddress.getText();
        String receiverphonenbr_ = receiverphonenbr.getText();
        // check if all fields are filled
        if(cin_==null || collectHour==null || collectMinutes==null || receiverFirstname_==null || receiverLastname_==null || receiveraddress_==null || receiverphonenbr_==null){
            App.ShowNotificationWindow("Error", "Please fill all fields",null);
            return;
        }  
        
        // check firstname and lastname
        if(receiverFirstname_.isEmpty() || receiverLastname_.isEmpty()){
            return;
        }
        // check if the firstname and lastname are valid
        if(!receiverFirstname_.matches("[a-zA-Z]+") || !receiverLastname_.matches("[a-zA-Z]+")){
            App.ShowNotificationWindow("Error", "Please enter a valid firstname and lastname",null);
            return;
        }
        // check if the phone number is valid   
        if(receiverphonenbr_.isEmpty() || receiverphonenbr_.matches("[0-9]+")){
            App.ShowNotificationWindow("Error", "Please enter a valid phone number",null);
            return;
        }
        // check if the address is valid
        if(receiveraddress_.isEmpty()){
            return;
        }
        if(!receiveraddress_.matches("[a-zA-Z0-9]+")){
            App.ShowNotificationWindow("Error", "Please enter a valid address",null);
            return;
        }
        // add details 
        connection.AddMail( weight_,cin_,price_ ,collect_date_,collectHour,collectMinutes,receiverFirstname_,receiverLastname_,receiveraddress_,receiverphonenbr_);
        
    }
    // fill info details
    public void fillinfo() {
        UserAccount user_account = connection.getuserclass();
        first_name.setText(user_account.getfirstname());
        last_name.setText(user_account.getlastname());
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
            App.changeStage(root);
        }
        catch (Exception e) {
            System.out.println("ERREUR :( \n" + e);
        }
    }
    private void CheckPriceField() {
        try{
            Float weight_ = weight.getText().isEmpty() ? 0 : Float.parseFloat(weight.getText());
            Float price = connection.CalculatePrice(weight_);
            // price to String
            String price_ = String.valueOf(price);
            Pricefield.setText(price_);
        }catch(Exception E){
            App.ShowNotificationWindow("Error",  "Please enter a valid weight",null);
            // TODO : add error message
            return;
        }
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        LocalDate DateIn = LocalDate.now();
        collect_date.setValue(DateIn.plusDays(1));
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
    @FXML
    private void showProfile(){
        try {
            FXMLLoader loder = new FXMLLoader(getClass().getResource("../Resources/VIEW/Profile.fxml"));
            Pane root = loder.load();
            Profile controller = loder.getController();
            controller.setConnection(connection);
            App.changeStage(root);
        } catch (IOException e) {
            e.printStackTrace();
            App.ShowNotificationWindow("error", "Could not load page close app and try again", null);
        }
    }
    @FXML
    private void CloseWindow() {
        App.CloseWindow();
    }
    @FXML   
    private void MinimizeWindow() {
        App.getpStage().setIconified(true);
    }
}
