package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class DataBaseConnection {
    // sql server connection
    private String db = "jdbc:oracle:thin:@localhost:1521:xe";
    private String username = "NFS";
    private String password = "bella462";
    Connection connection;
    Statement statement;
    ResultSet result;

    DataBaseConnection() {
        try {
            connection = DriverManager.getConnection(db, username, password);
            statement = connection.createStatement();
            System.out.println("Connected ✔");
        } catch (Exception e) {
            System.out.println("🔴 Data Base Connection Problem :" + e);
        }
    }

    public ResultSet Login_employ(String x) {
        try {
            String rs = "select * from postaccount where lower(username)='" + x.toLowerCase() + "'";
            result = statement.executeQuery(rs);
        } catch (Exception e) {
            System.out.println("Aha ahmadi");
        }
        return result;
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
    public void AddMail(char Date , Float Weight , int CLASS , int PRIC , String MORE ){
        try {
            String Sql = "INSERT INTO ROOMS VALUES("+Date+" , "+Weight+" , "+1+", "+CLASS+" , "+PRIC+" , '"+MORE+"')"; 
            statement.executeUpdate(Sql);
        } catch (Exception e) {
            System.out.println("No" + e);
        }
    }
}
