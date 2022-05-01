package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import javafx.scene.control.TextField;

// import BCrypt spring library

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

    public boolean Login_user(TextField email_Field, TextField password_text) {
        String email = "";
        // email validation
        if (validateEmail(email = email_Field.getText())) {
            email = email_Field.getText();
        } else {
            App.showAlert("Error", "Please enter a valid email");
            return false;
        }
        try {
            String rs = "select * from postaccount where lower(EMAIL)='" + email.toLowerCase() + "'";
            result = statement.executeQuery(rs);
            while (result.next()) {
                // check password using bcrypt
                if (BcryptTool.checkPassword(password_text.getText(), result.getString("password"))) {
                    user_account = new UserAccount(
                        result.getString("EMAIL"),
                        result.getString("password"),
                        result.getString("id_user")
                    );
                    user_account.setacountdetails(statement);
                    return true;
                } else {
                    App.showAlert("Error",  "Please enter a valid password");
                    return false;
                }
            }
        }
        catch (Exception e) {
            System.out.println("Aha ahmadi \n"+e);
        }
        return false;
    }

    public boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +"[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (pattern.matcher(email).matches()) {
            return true;
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
            String Sql = "INSERT INTO POSTMAIL (ID, WEIGHT, ADDRESS, COLLECT_DATE, CLIENT_ID, BACKUPPHONENBR, PRICE)"+
                          "VALUES('"+"RR"+
                          String.format("%09d", mail_id)+
                          "MA"+"','"+Weight+"' , '"+address_+
                          "' ,TO_DATE('"+collect_date_.format(formatter)+"', 'dd-mm-yyyy') , '"+
                          id_client+"' , '"+phonenbr_+"','"+Weight*0.85+"')"; 
            statement.executeUpdate(Sql);
            App.showAlert("info",  "Mail added successfully \n Your Courier id is : "+"RR"+String.format("%09d", mail_id)+"MA");
            pdfGenerator.SavePdfForm("RR"+String.format("%09d", mail_id)+"MA");
        } catch (Exception e) {
            System.out.println("No" + e);
            App.showAlert("info", "Mail not added");
        }
    }
    // get user classe
    public UserAccount getuserclass() {
        return user_account;
    }

    public ResultSet GetEmails() throws SQLException {
        
        String rs = "select * from postmail where lower(client_id)='" + user_account.getid() + "'";
        result = statement.executeQuery(rs);
        return result;
    }
}
