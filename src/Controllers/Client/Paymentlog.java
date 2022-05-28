package Controllers.Client;

import Controllers.Payment;
import Main.App;
import Main.Client_Connection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class Paymentlog {
    

    Client_Connection connection;
    
    @FXML
    private TableView<Payment> PAYMENTTABLE;

    ObservableList<Payment> List = FXCollections.observableArrayList();

    @FXML
    private FontAwesomeIconView Home;

    @FXML
    private TableColumn<Payment, String> ID;
    // @FXML
    // private TableColumn<Payment, String> WEIGHT;
    @FXML
    private TableColumn<Payment, String> AMOUNT;
    @FXML
    private TableColumn<Payment, String> DATE;
    @FXML  private Label Paid;
    @FXML  private Label Unpaid;
    @FXML  private Label PaymentAmount;



    @FXML
    public void filldata(){
        Paid.setText(connection.CountCourierPayment(1));
        Unpaid.setText(connection.CountCourierPayment(0));
        PaymentAmount.setText(connection.CountCourierPaymentAmount()+" DH");
        List.addAll(connection.GetPayment());
        PAYMENTTABLE.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ID.setCellValueFactory(new PropertyValueFactory<Payment, String>("Id"));
        AMOUNT.setCellValueFactory(new PropertyValueFactory<Payment, String>("AMOUNT"));
        DATE.setCellValueFactory(new PropertyValueFactory<Payment, String>("DATE"));
        PAYMENTTABLE.setItems(List);
    }
    @FXML
    private void switchToHomePage(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/VIEW/Client/Home.fxml"));
            Pane root = loader.load();
            client_home controller = loader.getController();
            controller.setConnection(connection);
            // get window parentstage
            App.changeStage(root);
        }
        catch (Exception e) {
            System.out.println("ERREUR :( \n" + e);
        }
    }
    @FXML
    private void showProfile(){
        App.ShowProfile();
    }
    @FXML
    private void CloseWindow() {
        App.CloseWindow();
    }
    @FXML   
    private void MinimizeWindow() {
        App.getprimaryStage().setIconified(true);
    }
    @FXML
    private void Logout(){
        App.Logout();
    }
}
