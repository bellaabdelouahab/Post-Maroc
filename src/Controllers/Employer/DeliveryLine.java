package Controllers.Employer;

import Main.Employer_Connection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class DeliveryLine {
    Employer_Connection connection;
    public Button closebutton;
    @FXML private ComboBox<String> deliverFrom;
    @FXML private ComboBox<String> deliverTo;
    @FXML private TextField Name;
    static String id;

    public void SetData(String Employeeid) {
        id=Employeeid;
        String[] cities = connection.getCities();
        for(int i = 0; i < cities.length; i++) {
            deliverFrom.getItems().add(cities[i]);
            deliverTo.getItems().add(cities[i]);
        }
        String from = connection.getcity(Employeeid,"FROM");
        String to = connection.getcity(Employeeid,"TO");
        deliverFrom.setValue(from);
        deliverTo.setValue(to);
        Name.setText(connection.getName(Employeeid));
    }
    @FXML
    private void updatedeliveryline() {
        String from = deliverFrom.getValue();
        String to = deliverTo.getValue();
        connection.updatedeliveryline(id,from,to);
    }
    public void setConnection(Employer_Connection connection) {
        this.connection = connection;
    }
}
