package Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserAccount {
    // account constructor
    public String username;
    private String email;
    private String phone;
    private String address;
    private String password;
    private String id;
    private String first_name;
    private String last_name;
    private String nationnality;
    private String jobtitle;
    private String gender;
    private String accounttype;

    public UserAccount(String username, String password,String id,String accounttype) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.accounttype=accounttype;
    }
    // set acount details 
    public void setacountdetails(Statement statement) {
        try {
            String Qry = "select * from postuser where lower(id_client)='" + this.id + "'";
            ResultSet result = statement.executeQuery(Qry);
            while (result.next()) {
                this.email = result.getString(2).toLowerCase();
                this.first_name = result.getString(3).toLowerCase();
                this.last_name = result.getString(4).toLowerCase();
                this.nationnality = result.getString(5).toLowerCase();
                this.gender = result.getString(6).toLowerCase();
                this.address = result.getString(7).toLowerCase();
                this.jobtitle = result.getString(8).toLowerCase();
                this.phone = result.getString(9).toLowerCase();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // get acount details
    public String getemail() {
        return email;
    }
    public String getphone() {
        return phone;
    }
    public String getaddress() {
        return address;
    }
    public String getpassword() {
        return password;
    }
    public String getfirstname() {
        return first_name;
    }
    public String getlastname() {
        return last_name;
    }
    public String getnationnality() {
        return nationnality;
    }
    public String getid() {
        return id;
    }
    public String jobtitle() {
        return jobtitle;
    }
    public String getgender() {
        return gender;
    }
    public String getaccounttype(){
        return accounttype;
    }
    
}
