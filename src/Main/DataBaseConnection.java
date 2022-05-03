package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Pattern;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.scene.control.Button;
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
    private int mail_id;
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
            Button btn = new Button("OK");
            // close notification window
            btn.setOnAction(e -> App.closeNotification());
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn);
            App.ShowNotificationWindow("Error", "Please enter a valid email",btns);
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
                    Button btn = new Button("OK");
                    // close notification window
                    btn.setOnAction(e -> App.closeNotification());
                    ArrayList<Button> btns = new ArrayList<Button>();
                    App.ShowNotificationWindow("Error",  "Please enter a valid password",btns);
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
            
            result = statement.executeQuery(qry1);
            while (result.next()) {
                mail_id = result.getInt(1);
            }
            System.out.println("RR"+String.format("%09d", mail_id)+"MA"+"<->"+id_client);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
            String Sql = "INSERT INTO POSTMAIL (ID, WEIGHT, ADDRESS, COLLECT_DATE, CLIENT_ID, BACKUPPHONENBR, PRICE)"+
                          "VALUES('"+"RR"+
                          String.format("%09d", mail_id)+
                          "MA"+"','"+Weight+"' , '"+address_+
                          "' ,TO_DATE('"+collect_date_.format(formatter)+"', 'dd-mm-yyyy') , '"+
                          id_client+"' , '"+phonenbr_+"','"+Weight*0.85+"')"; 
            statement.executeUpdate(Sql);
            Button btn = new Button("OK");
            // close notification window
            btn.setOnAction(e -> App.closeNotification());
            Button btn2 = new Button("Save a copy");
            // run pdfgenerator on action
            btn2.setOnAction(e -> {
                    try {
                        pdfGenerator.SavePdfForm(
                            "RR"+String.format("%09d", mail_id)+"MA"
                        );
                    } catch (FileNotFoundException e1) {
                        Button btn1 = new Button("OK");
                        // close notification window
                        btn.setOnAction(E -> App.closeNotification());
                        ArrayList<Button> btns = new ArrayList<Button>();
                        btns.add(btn1);
                        App.ShowNotificationWindow("Error",  "File not Found",btns);
                        return ;
                    } catch (IOException e1) {
                        Button btn1 = new Button("OK");
                        // close notification window
                        btn.setOnAction(E -> App.closeNotification());
                        ArrayList<Button> btns = new ArrayList<Button>();
                        btns.add(btn1);
                        App.ShowNotificationWindow("Error",  "File not Supported",btns);
                        return;
                    } catch (Exception e1) {
                        Button btn1 = new Button("OK");
                        // close notification window
                        btn.setOnAction(E -> App.closeNotification());
                        ArrayList<Button> btns = new ArrayList<Button>();
                        btns.add(btn1);
                        App.ShowNotificationWindow("Error",  "Failed to get sources. Try again on courier log page",btns);
                        return ;
                    }
            });
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn2);
            btns.add(btn);
            App.ShowNotificationWindow("info",  "Courier added successfully \n Your Courier id is : "+"RR"+String.format("%09d", mail_id)+"MA",btns);
        } catch (Exception e) {
            System.out.println("No" + e);
            Button btn = new Button("OK");
            // close notification window
            btn.setOnAction(E -> App.closeNotification());
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn);
            App.ShowNotificationWindow("info", "Mail not added double check your information",btns);
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
