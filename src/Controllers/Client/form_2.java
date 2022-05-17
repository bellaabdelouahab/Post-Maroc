package Controllers.Client;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import Controllers.Courier;
import Main.App;
import Main.Client_Connection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class form_2 implements Initializable{

    private Client_Connection connection;
    @FXML
    private TextField weight,Pricefield;
    @FXML
    private TextArea discription;
    @FXML
    private DatePicker collect_date;
    @FXML
    private ComboBox<String> combohour,combominutes;
    Courier courier;





    private void CheckPriceField() {
        try{
            Float weight_ = weight.getText().isEmpty() ? 0.1f : Float.parseFloat(weight.getText());
            Float price = connection.CalculatePrice(weight_);
            if(price == null){
                Pricefield.setText("");
                throw new Exception();
            }
            // price to String
            String price_ = String.valueOf(price);
            Pricefield.setText(price_);
        }catch(Exception E){
            App.ShowNotificationWindow("Error",  "Please enter a valid weight, It should be a numirecal value between 0 and 10",null);
            // TODO : add error message
            return;
        }
    }
    public Courier validateForm2(){
        Float weight_ = 0f;
        Float price_ = 0f;
        try{
            weight_ = weight.getText().isEmpty() ? 0.1f : Float.parseFloat(weight.getText());
            price_ = Float.parseFloat(Pricefield.getText());
            CheckPriceField();
        }
        catch(Exception E){
            show_error(weight);
            App.ShowNotificationWindow("Error",  "Please enter a valid weight",null);
            return null;
        }
        // get date from text fields
        LocalDate collect_date_ = collect_date.getValue();
        String collectHour = combohour.getValue();
        String collectMinutes = combominutes.getValue();
        // check if the entered date bigger  current date
        if(!collect_date_.isAfter(LocalDate.now()) ){
            show_error(collect_date);
            App.ShowNotificationWindow("Error",  "Please enter a valid date",null);
            System.out.println(collect_date_+"<==>"+LocalDate.now());
            return null;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
        String collect_date_str = collect_date_.format(formatter)+" "+collectHour+":"+collectMinutes;
        courier.setCollectDate(collect_date_str);
        courier.setPrice(price_);
        courier.setWeight(weight_);
        courier.setDiscription(discription.getText());
        return courier;
    }

    private void show_error(Node textfield) {
        textfield.setStyle(textfield.getStyle()+";-fx-border-color: red");
    }
    // mouse click event for reset style of text fields
    @FXML
    private void hide_error(MouseEvent event) {
        Node field = (Node)event.getSource();
        field.setStyle(field.getStyle()+";-fx-border-color: #00000000");
    }
    
    @FXML
    private void CalculatePrice() {
        Pricefield.setText(connection.CalculatePrice(weight.getText().isEmpty() ? 0.2f : Float.parseFloat(weight.getText()))+"");
    }
    public void setConnection(Client_Connection connection) {
        this.connection = connection;
    }
    public void setCourier(Courier courier) {
        this.courier = courier;
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        LocalDate DateIn = LocalDate.now();
        collect_date.setValue(DateIn.plusDays(1));
        System.out.println("action added");
        weight.setOnKeyReleased((e) -> {
            // check if enter was clicked 
            if(e.getCode().equals(javafx.scene.input.KeyCode.ENTER)){
                CheckPriceField();
            }
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
