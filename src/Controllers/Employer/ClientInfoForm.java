package Controllers.Employer;

import Main.ClientAccount;
import Main.Employer_Connection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ClientInfoForm {
    Employer_Connection connection;
    @FXML 
    private TextField email,firstname,lastname,nationnality,gender,address,jobtitle,phonenbr;
    private String clientid;
    @FXML Button closebutton;
    
    public void fillinfo(String Clientid){
        ClientAccount client = connection.getClient(Clientid);
        // set data
        if(client==null) {
            closebutton.fire();
            return;
        }
        clientid = client.getId();
        email.setText(client.getemail());
        firstname.setText(client.getfirstname());
        lastname.setText(client.getlastname());
        nationnality.setText(client.getnationnality());
        gender.setText(client.getgender());
        address.setText(client.getaddress());
        jobtitle.setText(client.getjobtitle());
        phonenbr.setText(client.getphone());

    }
    @FXML
    private void DeleteClient(){
        connection.deleteClient(clientid);
        closebutton.fire();
    }
    @FXML
    private void UpdateClientinfo(){
        connection.updateClientinfo(clientid,email.getText(),firstname.getText(),lastname.getText(),
                                nationnality.getText(),gender.getText(),address.getText(),
                                jobtitle.getText(),phonenbr.getText());
        closebutton.fire();
    }
    public void setConnection(Employer_Connection connection) {
        this.connection = connection;
    }
}
