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

import Controllers.Employer.Courier;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;

// import BCrypt spring library

public class DataBaseConnection {
    // sql server connection
    private String db = "jdbc:oracle:thin:@localhost:1521:xe";
    private String username = "NFS";
    private String password = "bella462";
    Connection connection;
    Statement statement;
    ResultSet result;
    private UserAccount user_account;
    private int mail_id;
    DataBaseConnection() {
        try {
            connection = DriverManager.getConnection(db, username, password);
            statement = connection.createStatement();
        } catch (Exception e) {
            // connection Failed notification
            Button btn = new Button("OK");
            // close notification window
            btn.setOnAction(E -> System.exit(0));
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn);
            App.ShowNotificationWindow("Error",  "Failed to get sources. Try again on courier log page",btns);

        }
    }

    public UserAccount getUser_account() {
        return user_account;
    }

    public void setUser_account(UserAccount user_account) {
        this.user_account = user_account;
    }

    public boolean Login_user(TextField email_Field, TextField password_text, Line email_error_line, ImageView email_error_circle, Line password_error_line, ImageView password_error_circle) {
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
                    setUser_account(new UserAccount(
                        result.getString("EMAIL"),
                        result.getString("password"),
                        result.getString("id_user"),
                        result.getString("accounttype")
                    ));
                    getUser_account().setacountdetails(statement);
                    return true;
                } else {
                    // password incorrect
                    password_error_line.setVisible(true);
                    password_error_circle.setVisible(true);
                    return false;
                }
            }
            // email not found
            email_error_line.setVisible(true);
            email_error_circle.setVisible(true);
            return false;
          
            
        }
        catch (Exception e) {
            // notify about connection error
            Button btn = new Button("OK");
            // close notification window
            btn.setOnAction(E -> App.closeNotification());
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn);
            App.ShowNotificationWindow("Error", "Failed to get sources. check you internet connection",btns);
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
    public boolean Disconnect() {
        try {
            connection.close();
            statement.close();
            return true;
        } catch (SQLException e) {
            // notify that connection is not closed
            Button btn = new Button("Close Anyway");
            // close notification window
            btn.setOnAction(E -> System.exit(0));
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn);
            App.ShowNotificationWindow("Error", "internet error : Failed to close connection",btns);
        }
        return false;
    }
    public void AddMail(Float Weight , String id_client, String phonenbr_,Float price,String address_, LocalDate collect_date_, String collectHour, String collectMinutes,String FirstName,String LastName, String Address, String phonenbr){
        try {
            String qry1 = "SELECT COUNT(*) FROM POSTCOURIER";
            
            result = statement.executeQuery(qry1);
            while (result.next()) {
                mail_id = result.getInt(1);
            }
            //"RR"+String.format("%09d", mail_id)+"MA"+"<->"+id_client);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
            // generate a 16bite random password
            String receiver_id = BcryptTool.generateRandomId();
            String qry2 = "INSERT INTO POSTCOURIER (ID, WEIGHT, ADDRESS, COLLECT_DATE, CLIENT_ID, BACKUPPHONENBR, PRICE,RECEIVER_ID)"+
                          "VALUES('"+"RR"+
                          String.format("%09d", mail_id)+
                          "MA"+"','"+Weight+"' , '"+address_+
                          "' ,TO_DATE('"+collect_date_.format(formatter)+" "+collectHour+":"+collectMinutes+"', 'DD-MM-YYYY HH24:MI') , '"+
                          id_client+"' , '"+phonenbr_+"','"+price+"','"+receiver_id+"')"; 
            String qry3 = "INSERT INTO POSTCOURIER_RECEIVER (RECEIVER_ID,FIRSTNAME,LASTNAME, ADDRESS,PHONENBR)"+
                            "VALUES('"+receiver_id+"','"+FirstName+"','"+LastName+"' , '"+Address+"' ,'"+phonenbr+"')";
            statement.executeUpdate(qry2);
            statement.executeUpdate(qry3);
            Button btn = new Button("OK");
            // close notification window
            btn.setOnAction(e -> App.closeNotification());
            Button btn2 = new Button("Save a copy");
            // run pdfgenerator on action
            btn2.setOnAction(e -> {
                        WriteToFile(
                            "RR"+String.format("%09d", mail_id)+"MA",
                            user_account.getfirstname(),
                            user_account.getlastname(),
                            user_account.getaddress(),
                            user_account.getphone(),
                            FirstName,
                            LastName,
                            Address,
                            phonenbr
                        );
                        //  open an exe 
                        try {
                            Runtime.getRuntime().exec("./CourierFormCreator.py");
                        } catch (IOException e1) {
                            // TODO Fix errors that the python exe may genrate
                            e1.printStackTrace();
                            Button btn1 = new Button("OK");
                            // close notification window
                            btn1.setOnAction(E -> System.exit(0));
                            ArrayList<Button> btns = new ArrayList<Button>();
                            btns.add(btn1);
                            App.ShowNotificationWindow("Error",  "Failed to get sources. Try again on courier log page",btns);
                            // System.exit(0);
                        }
            });
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn2);
            btns.add(btn);
            App.ShowNotificationWindow("info",  "Courier added successfully \n Your Courier id is : "+"RR"+String.format("%09d", mail_id)+"MA",btns);
        } catch (Exception e) {
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
        return getUser_account();
    }

    public ResultSet GetEmails() throws SQLException {
        
        String rs = "select * from POSTCOURIER where lower(client_id)='" + getUser_account().getid() + "'";
        result = statement.executeQuery(rs);
        return result;
    }
    public Float CalculatePrice(Float weight) {
        String qry1 = "select price from postcourierprices where "+weight+"<=weight_end_point and "+weight+">=weight_start_point";
        Float mail_id_=0.0f;
            try {
                result = statement.executeQuery(qry1);
                while (result.next()) {
                    mail_id_ = result.getFloat(1);
                    return mail_id_;
                }
            } catch (SQLException e) {
                Button btn = new Button("OK");
                // close notification window
                btn.setOnAction(e_ -> App.closeNotification());
                ArrayList<Button> btns = new ArrayList<Button>();
                btns.add(btn);
                App.ShowNotificationWindow("Error",  "Failed to get sources.",btns);
            }
            return mail_id_;
    }

    public ArrayList<Courier> getCourier(String status) {
        String qry1 = "select * from POSTCOURIER where status in("+status+")  ORDER BY id ";
        try {
            result = statement.executeQuery(qry1);
            ArrayList<Courier> couriers = new ArrayList<Courier>();
            while (result.next()) {
                String id = result.getString(1);
                String weight = result.getString(2);
                String address = result.getString(3);
                String collect_date = result.getString(4);
                String client_id = result.getString(5);
                String backup_phonenbr = result.getString(6);
                String price = result.getString(7);
                String status_ = result.getString(8);
                String receiver_id = result.getString(9);
                // create a courier class with previous information
                couriers.add(new Courier(id, weight, address, collect_date, client_id, backup_phonenbr, price,status_, receiver_id));   
            }
            return couriers;
        } catch (SQLException e) {
            Button btn = new Button("OK");
            e.printStackTrace();
            System.exit(0);
            // close notification window
            btn.setOnAction(e_ -> App.closeNotification());
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn);
            App.ShowNotificationWindow("Error",  "Failed to get sources.",btns);
            
        }
        return null;
    }

    public void SupportCourier(String CourierId) {
        String qry1 = "update POSTCOURIER set status='Supported' where id='" + CourierId + "'";
        try {
            statement.executeUpdate(qry1);
            Button btn = new Button("OK");
            // close notification window
            btn.setOnAction(e -> App.closeNotification());
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn);
            App.ShowNotificationWindow("info",  "Courier successfully Supported ",btns);
        } catch (SQLException e) {
            Button btn = new Button("OK");
            e.printStackTrace();
            // close notification window
            btn.setOnAction(E -> App.closeNotification());
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn);
            App.ShowNotificationWindow("info",  "Unable to Supprted try later or contact your administrator",btns);
        }
    }

    public void CancelCourier(String CourierId) {
        String qry1 = "update POSTCOURIER set status='Cancelled' where id='" + CourierId + "'";
        try {
            statement.executeUpdate(qry1);
            Button btn = new Button("OK");
            // close notification window
            btn.setOnAction(e -> App.closeNotification());
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn);
            App.ShowNotificationWindow("info",  "Courier successfully Cancelled ",btns);
        } catch (SQLException e) {
            Button btn = new Button("OK");
            e.printStackTrace();
            // close notification window
            btn.setOnAction(E -> App.closeNotification());
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn);
            App.ShowNotificationWindow("info",  "Unable to Cancelled try later or contact your administrator",btns);
        }

    }
    private boolean WriteToFile(String CourierId, String user_FN, String user_LN, String user_address, String user_Phone, String FirstName, String LastName, String address, String phonenbr) {
        try {
            FileWriter myWriter = new FileWriter("../Resources/OutputCourierForm/prototype/CurrentCourierInfo.txt");
            // whrite to file
            myWriter.write(CourierId+"\n");
            myWriter.write(user_LN + "\n");
            myWriter.write(user_FN + "\n");
            myWriter.write(FirstName+"\n");
            myWriter.write(LastName+"\n");
            myWriter.write(user_address + "\n");
            myWriter.write(address + "\n");
            myWriter.write(user_Phone + "\n");
            myWriter.write(phonenbr + "\n");
            myWriter.close();
            return true;
        } catch (IOException e) {
            Button btn1 = new Button("OK");
            // close notification window
            btn1.setOnAction(E -> App.closeNotification());
            ArrayList<Button> btns = new ArrayList<Button>();
            btns.add(btn1);
            App.ShowNotificationWindow("Error",  "File not Created",btns);
            return false;
        }
      }
}
