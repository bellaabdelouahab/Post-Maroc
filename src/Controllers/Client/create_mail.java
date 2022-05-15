package Controllers.Client;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Controllers.Courier;
import Controllers.Profile;
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
    Courier courier;
    @FXML Pane forms;
    @FXML
    private TextField first_name, last_name,phonenbr,address,weight,Pricefield,receiverFirstname,receiverLastname,receiveraddress,receiverphonenbr;
    @FXML
    private DatePicker collect_date;
    @FXML
    private ComboBox<String> combohour,combominutes;
    public int currentform=1;
    private form_1 Form1controller ;
    @FXML
    private void ValidateMail(){
        if (currentform==1){
            Courier Courier_cach = Form1controller.validateForm1();
            if(Courier_cach==null)return;
            courier=Courier_cach;
            SwitchToForm2();
        }
        // get details from text fields
        String cin_ = connection.getuserclass().getid();
        
        // add details 
        // connection.AddMail( weight_,cin_,price_ ,collect_date_,collectHour,collectMinutes,receiverFirstname_,receiverLastname_,receiveraddress_,receiverphonenbr_);
        
    }
    @FXML
    private void SwitchToForm2(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/VIEW/Client/CourierFillForm_2.fxml"));
            Pane root = loader.load();
            form_2 controller = loader.getController();
            controller.setConnection(connection);
            forms.getChildren().clear();
            forms.getChildren().add(root);
        }
        catch(Exception e){

        }
    }
    @FXML 
    public void SwitchToForm1(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/VIEW/Client/CourierFillForm_1.fxml"));
            Pane root = loader.load();
           Form1controller = loader.getController();
           Form1controller.setConnection(connection);
           Form1controller.fillinfo();
           Form1controller.setCourier(courier);
            forms.getChildren().clear();
            forms.getChildren().add(root);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void switchToHomePage(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/VIEW/Client/Home.fxml"));
            Pane root = loader.load();
            client_home controller = loader.getController();
            controller.setConnection(connection);
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
        // LocalDate DateIn = LocalDate.now();
        // collect_date.setValue(DateIn.plusDays(1));
        // System.out.println("action added");
        // weight.setOnKeyReleased((e) -> {
        //     CheckPriceField();
        // });
        // // add hours to the combohour
        // for(int i = 8; i < 18; i++){
        //     combohour.getItems().add(String.format("%02d", i));
        // }
        // // add minutes to the combominutes
        // for(int i = 0; i < 60; i++){
        //     combominutes.getItems().add(String.format("%02d", i));
        // }
        
    }
    @FXML
    private void showProfile(){
        try {
            FXMLLoader loder = new FXMLLoader(getClass().getResource("/Resources/VIEW/Profile.fxml"));
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
    @FXML
    private void Logout(){
        App.Logout();
    }
}
