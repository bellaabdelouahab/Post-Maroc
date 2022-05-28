package Controllers.Client;

import org.controlsfx.control.SearchableComboBox;

import Controllers.Courier;
import Controllers.Receiver;
import Main.DataBaseConnection;
import Main.UserAccount;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class form_1 {
    DataBaseConnection connection;
    Courier courier;
    @FXML
    private TextField first_name,last_name,phonenbr,address,
                      receiverFirstname,receiverLastname,
                      receiveraddress,receiverphonenbr,backup_phonenbr;
    @FXML
    private SearchableComboBox<String> combobox_city;
    private Node field;

    // fill info details
    public void fillinfo() {
        UserAccount user_account = connection.getUser_account();
        first_name.setText(user_account.getfirstname());
        last_name.setText(user_account.getlastname());
        address.setText(user_account.getaddress());
        phonenbr.setText(user_account.getphone());
        // read file to fill combobox data
        String[] cities = connection.getCities();
        for(int i = 0; i < cities.length; i++) {
            combobox_city.getItems().add(cities[i]);
        }
    }
    public Courier validateForm1(){
        boolean isvalid = true;
        // get receiver information from text fields
        String receiverFirstname_ = receiverFirstname.getText();
        String receiverLastname_ = receiverLastname.getText();
        String receiveraddress_ = receiveraddress.getText();
        String receiverphonenbr_ = receiverphonenbr.getText();
        String backup_phonenbr_ = backup_phonenbr.getText();
        // validate fields
        // check firstname and lastname
        
        // check if the firstname and lastname are valid
        if(receiverFirstname_.isEmpty()|| !receiverFirstname_.matches("[a-zA-Z]+") ){
            show_error(receiverFirstname);
            isvalid = false;
        }
        if(receiverLastname_.isEmpty() || !receiverLastname_.matches("[a-zA-Z]+")){
            show_error(receiverLastname);
            isvalid = false;
        }
        // check if the address is valid
        if(receiveraddress_.isEmpty()){
            show_error(receiveraddress);
            isvalid = false;
        }
        // check if the phone number is valid   
        if(receiverphonenbr_.isEmpty() || !receiverphonenbr_.matches("[0-9]+")){
            show_error(receiverphonenbr);
            isvalid = false;
        }
        // check if the backup phone number is valid
        if(!backup_phonenbr_.isEmpty()  && !backup_phonenbr_.matches("[0-9]+")){
            show_error(backup_phonenbr);
            isvalid = false;
        }
        // check combobox
        if(combobox_city.getValue() == null){
            show_error(combobox_city);
            isvalid = false;
        }
        if (!isvalid) {
            return null;
        }
        System.out.println("passed validation");
        create_mail.courier.setReceiver(new Receiver(receiverFirstname_,receiverLastname_,combobox_city.getValue()+": "+receiveraddress_,receiverphonenbr_));
        create_mail.courier.setBackupphonenbr(backup_phonenbr_);
        return courier;
    }

    private void show_error(Node textfield) {
        textfield.setStyle(textfield.getStyle()+";-fx-border-color: red");
    }
    // mouse click event for reset style of text fields
    @FXML
    private void hide_error(MouseEvent event) {
        field = (Node)event.getSource();
        field.setStyle(field.getStyle()+";-fx-border-color: #00000000");
    }
    public void setConnection(DataBaseConnection connection) {
        this.connection = connection;
    }
}
