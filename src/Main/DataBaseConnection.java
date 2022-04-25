package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.TextField;
public class DataBaseConnection {
    // sql server connection
    private String db = "jdbc:oracle:thin:@localhost:1521:xe";
    private String username = "NFS";
    private String password = "bella462";
    Connection connection;
    Statement statement;
    ResultSet result;
    UserAccount user_account;

    DataBaseConnection() {
        try {
            connection = DriverManager.getConnection(db, username, password);
            statement = connection.createStatement();
            System.out.println("Connected ✔");
        } catch (Exception e) {
            System.out.println("🔴 Data Base Connection Problem :" + e);
        }
    }

    public boolean Login_user(TextField username_text, TextField password_text) {
        try {
            String rs = "select * from postaccount where lower(username)='" + username_text.getText().toLowerCase() + "'";
            result = statement.executeQuery(rs);
            while (result.next()) {
                if(result.getString(2).toLowerCase().equals(password_text.getText())){
                user_account = new UserAccount(username_text.getText().toLowerCase(), password_text.getText().toLowerCase(),
                                               result.getString(3));
                user_account.setacountdetails(statement);
                System.out.println("Login Successful");
                return true;
                }
                else{
                return false;
                }
            }
        }
        catch (Exception e) {
            System.out.println("Aha ahmadi \n"+e);
        }
        return false;
    }
    // Disconnect from the Data Base
    public void Disconnect() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Problem");
        }
    }
    public void AddMail(Float Weight , String id_client, String phonenbr_,String address_, LocalDate collect_date_){
        try {
            String qry1 = "SELECT COUNT(*) FROM POSTMAIL";
            System.out.println("sddsds");
            result = statement.executeQuery(qry1);
            int mail_id = 0;
            while (result.next()) {
                mail_id = result.getInt(1);
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
            String Sql = "INSERT INTO POSTMAIL VALUES('"+"RR"+String.format("%09d", mail_id)+"MA"+"','"+Weight+"' , '"+address_+"' ,TO_DATE('"+collect_date_.format(formatter)+"', 'dd-mm-yyyy') , '"+id_client+"' , '"+phonenbr_+"')"; 
            statement.executeUpdate(Sql);
        } catch (Exception e) {
            System.out.println("No" + e);
        }
    }
    // get user classe
    public UserAccount getuserclass() {
        return user_account;
    }
}
