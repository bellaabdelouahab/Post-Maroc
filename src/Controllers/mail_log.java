package Controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import Main.App;
import Main.DataBaseConnection;
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

public class mail_log {

    public DataBaseConnection connection;
    
    @FXML
    private TableView<MailLog> USERSTABLE;

    ObservableList<MailLog> List = FXCollections.observableArrayList();

    @FXML
    private FontAwesomeIconView Home;

    @FXML
    private TableColumn<MailLog, String> ID;
    @FXML
    private TableColumn<MailLog, String> WEIGHT;
    @FXML
    private TableColumn<MailLog, String> ADDRESS;
    @FXML
    private TableColumn<MailLog, String> COLLECT_DATE;
    @FXML
    private TableColumn<MailLog, String> PRICE;
    @FXML
    private TableColumn<MailLog, String> STATE;

    public void SetData() throws SQLException{
        ResultSet Lest = connection.GetEmails();    
        try {

            while (Lest.next()) {
                // System.out.println(Lest.getString("ID"));
                // System.exit(0);
                List.add(new MailLog(Lest.getString("ID"), Lest.getString("WEIGHT"),
                Lest.getString("ADDRESS"), Lest.getString("COLLECT_DATE"),
                 Lest.getString("PRICE"), Lest.getString("STATE")));
            }
            ;

        } catch (SQLException e) {
            System.out.println("No Data Found" + e);
        }
        ID.setCellValueFactory(new PropertyValueFactory<MailLog, String>("Id"));
        WEIGHT.setCellValueFactory(new PropertyValueFactory<MailLog, String>("WEIGHT"));
        ADDRESS.setCellValueFactory(new PropertyValueFactory<MailLog, String>("ADDRESS"));
        COLLECT_DATE.setCellValueFactory(new PropertyValueFactory<MailLog, String>("COLLECT_DATE"));
        PRICE.setCellValueFactory(new PropertyValueFactory<MailLog, String>("PRICE"));
        STATE.setCellValueFactory(new PropertyValueFactory<MailLog, String>("STATE"));
        USERSTABLE.setItems(List);

    }
    // switch to home page
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
}
