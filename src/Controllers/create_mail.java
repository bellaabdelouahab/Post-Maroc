package Controllers;

import java.time.LocalDate;

import Main.DataBaseConnection;
import Main.UserAccount;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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
        Float weight_ = Float.parseFloat(weight.getText());
        LocalDate collect_date_ = collect_date.getValue();
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
}
