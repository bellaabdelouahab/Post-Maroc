package Main;




import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Pattern;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;


public class DataBaseConnection {
    final static String DB_URL="jdbc:oracle:thin:@nfs315_high";
    final static String DB_USER = "admin";
    final static String DB_PASSWORD = "Abdobella4624";
    final static String CONN_FACTORY_CLASS_NAME="oracle.jdbc.pool.OracleDataSource";
    static Connection connection;
    static Statement statement;
    ResultSet result;
    static UserAccount user_account;
     int mail_id;
    static PoolDataSource pds;
    public DataBaseConnection(Boolean parent) {
        if(!parent) return;
        try{
            // System.out.println(DataBaseConnection.class("\\src\\Resources\\Wallet_NFS315"));
        System.setProperty("oracle.net.tns_admin",App.path_to_wallet);
        // System.exit(0);
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
            e.printStackTrace();
            System.out.println("notification added");
            App.CurrentNotification="Connection Failed";
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
            App.CurrentNotification = "Please enter a valid email" ;
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
            App.CurrentNotification = "Failed to get sources. check you internet connection";

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
            App.CurrentNotification = "Failed to get sources.";
        }
        return null;
    }
    public void updateUser_account(UserAccount useraccount) {
        String qry1 = "update POSTUSER set first_name='"+useraccount.getfirstname()+
                      "',last_name='"+useraccount.getlastname()+"',address='"+useraccount.getaddress()+
                      "',phonenbr='"+useraccount.getphone()+"',JOB_TITLE='"+useraccount.getjobtitle()+
                      "' where id='"+useraccount.getid()+"'";
        try {
            statement.executeUpdate(qry1);
            App.CurrentNotification =  "User account successfully updated ";
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("failed to update user account postuser");
            App.CurrentNotification = "Unable to update try later or contact your administrator";
        }
    }
}
