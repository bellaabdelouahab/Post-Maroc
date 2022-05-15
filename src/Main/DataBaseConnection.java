package Main;




import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.ucp.jdbc.PoolDataSourceFactory;
import oracle.ucp.jdbc.PoolDataSource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Pattern;

import Controllers.Courier;

import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;

// import BCrypt spring library

public class DataBaseConnection {
    // sql server connection
    final static String DB_URL="jdbc:oracle:thin:@nfs315_high?TNS_ADMIN=Wallet_NFS315";
    final static String DB_USER = "admin";
    final static String DB_PASSWORD = "Abdobella4624";
    final static String CONN_FACTORY_CLASS_NAME="oracle.jdbc.pool.OracleDataSource";
    static Connection connection;
    static Statement statement;
    ResultSet result;
    private static UserAccount user_account;
    private int mail_id;
    static PoolDataSource pds;
    public DataBaseConnection() {

        try{
        pds = PoolDataSourceFactory.getPoolDataSource();
        pds.setConnectionFactoryClassName(CONN_FACTORY_CLASS_NAME);
        pds.setURL(DB_URL);
        pds.setUser(DB_USER);
        pds.setPassword(DB_PASSWORD);
        pds.setConnectionPoolName("JDBC_UCP_POOL");
        pds.setInitialPoolSize(5);
        pds.setMinPoolSize(5);
        pds.setMaxPoolSize(20);
        pds.setTimeoutCheckInterval(5);
        pds.setInactiveConnectionTimeout(10);
        Properties connProps = new Properties();
        connProps.setProperty("fixedString", "false");
        connProps.setProperty("remarksReporting", "false");
        connProps.setProperty("restrictGetTables", "false");
        connProps.setProperty("includeSynonyms", "false");
        connProps.setProperty("defaultNChar", "false");
        connProps.setProperty("AccumulateBatchResult", "false");
        connection = pds.getConnection();
        System.out.println(DB_URL + "    is connected");
        System.out.println("Available connections after checkout: "+ pds.getAvailableConnectionsCount());
        System.out.println("Borrowed  connections after checkout: "+ pds.getBorrowedConnectionsCount());
        statement = connection.createStatement();
        System.out.println("connected successfully");
        } catch (Exception e) {
            App.ShowNotificationWindow("Error",  "No internet connection please restart the app and try again.",null);
        }
    }
    public UserAccount getUser_account() {
        return user_account;
    }

    public void setUser_account(UserAccount user_account) {
        DataBaseConnection.user_account = user_account;
    }

    public boolean Login_user(TextField email_Field, TextField password_text, Line email_error_line, ImageView email_error_circle, Line password_error_line, ImageView password_error_circle) {
        String email = "";
        // email validation
        if (!validateEmail(email = email_Field.getText())) {
            App.ShowNotificationWindow("error","Please enter a valid email",null); ;
            return false;
            
        }
        email = email_Field.getText();
        try {
            String rs = "select * from postaccount where lower(EMAIL)='" + email.toLowerCase() + "'";
            result = statement.executeQuery(rs);
            while (result.next()) {
                // check password using bcrypt
                if (!BcryptTool.checkPassword(password_text.getText(), result.getString("password"))) {
                    // password incorrect
                    password_error_line.setVisible(true);
                    password_error_circle.setVisible(true);
                    return false;
                }
                setUser_account(new UserAccount(
                    result.getString("EMAIL"),
                    result.getString("password"),
                    result.getString("id_user"),
                    result.getString("accounttype")
                ));
                getUser_account().setacountdetails(statement);
                return true;
            }
            // email not found || resualt empty
            email_error_line.setVisible(true);
            email_error_circle.setVisible(true);
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            App.ShowNotificationWindow("error",  "Failed to get sources. check you internet connection",null);

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
    public static boolean Disconnect() {
        user_account = null; 
        return true;
    }
    public void AddMail(Float Weight , String id_client,Float price, LocalDate collect_date_, String collectHour, String collectMinutes,String FirstName,String LastName, String Address, String phonenbr){
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
                          "MA"+"','"+Weight+"' , '"+Address+
                          "' ,TO_DATE('"+collect_date_.format(formatter)+" "+collectHour+":"+collectMinutes+"', 'DD-MM-YYYY HH24:MI') , '"+
                          id_client+"' , '"+user_account.getphone()+"','"+price+"','"+receiver_id+"')"; 
            String qry3 = "INSERT INTO POSTCOURIER_RECEIVER (RECEIVER_ID,FIRSTNAME,LASTNAME, ADDRESS,PHONENBR)"+
                            "VALUES('"+receiver_id+"','"+FirstName+"','"+LastName+"' , '"+Address+"' ,'"+phonenbr+"')";
            statement.executeUpdate(qry2);
            statement.executeUpdate(qry3);
            Button save_copy = new Button("Save A Copy");
            // run pdfgenerator on action
            save_copy.setOnAction(e -> {
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
                    App.ShowNotificationWindow("Error",  "Failed to get sources. Try again on courier log page",null);
                }
            });
            App.ShowNotificationWindow("info",  "Courier added successfully \n Your Courier id is : "+
                                       "RR"+String.format("%09d", mail_id)+"MA",save_copy);
        } catch (Exception e) {
            App.ShowNotificationWindow("info", "Mail not added double check your information",null);
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
                App.ShowNotificationWindow("Error",  "Failed to get sources.",null);
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
            App.ShowNotificationWindow("Error",  "Failed to get sources.",null);
            
        }
        return null;
    }

    public void SupportCourier(String CourierId) {
        String qry1 = "update POSTCOURIER set status='Supported' where id='" + CourierId + "'";
        try {
            statement.executeUpdate(qry1);
            App.ShowNotificationWindow("info",  "Courier successfully Supported ",null);
        } catch (SQLException e) {
            App.ShowNotificationWindow("info",  "Unable to Supprted try later or contact your administrator",null);
        }
    }

    public void CancelCourier(String CourierId) {
        String qry1 = "update POSTCOURIER set status='Cancelled' where id='" + CourierId + "'";
        try {
            statement.executeUpdate(qry1);
            App.ShowNotificationWindow("info",  "Courier successfully Cancelled ",null);
        } catch (SQLException e) {
            App.ShowNotificationWindow("info",  "Unable to Cancelled try later or contact your administrator",null);
        }

    }
    private boolean WriteToFile(String CourierId, String user_FN, String user_LN, String user_address, String user_Phone, String FirstName, String LastName, String address, String phonenbr) {
        try {
            FileWriter myWriter = new FileWriter("/Resources/OutputCourierForm/prototype/CurrentCourierInfo.txt");
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
            App.ShowNotificationWindow("Error",  "File not Created",null);
            return false;
        }
      }
    public String[] getCities() {
        String qry1 = "select * from postavaiablecities";
        try {
            result = statement.executeQuery(qry1);
            ArrayList<String> cities = new ArrayList<String>();
            while (result.next()) {
                String city = result.getString(1);
                cities.add(city);
            }
            String[] cities_ = new String[cities.size()];
            for (int i = 0; i < cities.size(); i++) {
                cities_[i] = cities.get(i);
            }
            return cities_;
        } catch (SQLException e) {
            App.ShowNotificationWindow("Error",  "Failed to get sources.",null);
        }
        return null;
    }
}
