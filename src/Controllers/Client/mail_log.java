package Controllers.Client;

import java.sql.ResultSet;
import java.sql.SQLException;

import Main.App;
import Main.Client_Connection;
import Main.MailLog;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class Mail_log {

    Client_Connection connection;
    
    @FXML
    private TableView<MailLog> USERSTABLE;

    ObservableList<MailLog> List = FXCollections.observableArrayList();

    @FXML
    private FontAwesomeIconView Home;

    @FXML
    private TableColumn<MailLog, String> ID;
    // @FXML
    // private TableColumn<MailLog, String> WEIGHT;
    @FXML
    private TableColumn<MailLog, String> ADDRESS;
    @FXML
    private TableColumn<MailLog, String> COLLECT_DATE;
    @FXML
    private TableColumn<MailLog, Float> PRICE;
    @FXML
    private TableColumn<MailLog, String> STATUS;

    public void SetData() throws SQLException{
        ResultSet Lest = connection.GetEmails();    
        try {
            
            while (Lest.next()) {
                System.out.println(Lest.getString("STATUS"));
                List.add(new MailLog(Lest.getString("ID"),
                Lest.getString("ADDRESS"), Lest.getString("COLLECT_DATE"),
                Lest.getFloat("PRICE"), Lest.getString("STATUS")));
            }
            ;

        } catch (SQLException e) {
            System.out.println("No Data Found" + e);
        }
        USERSTABLE.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ID.setCellValueFactory(new PropertyValueFactory<MailLog, String>("Id"));
        ADDRESS.setCellValueFactory(new PropertyValueFactory<MailLog, String>("ADDRESS"));
        COLLECT_DATE.setCellValueFactory(new PropertyValueFactory<MailLog, String>("COLLECT_DATE"));
        PRICE.setCellValueFactory(new PropertyValueFactory<MailLog, Float>("PRICE"));
        STATUS.setCellValueFactory(new PropertyValueFactory<MailLog, String>("STATUS"));
        USERSTABLE.setItems(List);

    }
    // switch to home page
    @FXML
    private void switchToHomePage(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/VIEW/Client/Home.fxml"));
            Pane root = loader.load();
            Client_home controller = loader.getController();
            controller.setConnection(connection);
            controller.init();
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
