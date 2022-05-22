package Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.image.Image;

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
    private int deliveryline;
    private String jobclass;

    public UserAccount(String username, String password,String id,String accounttype) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.accounttype=accounttype;
    }
    public void setacountdetails(Statement statement) {
        try {
            if(this.accounttype.equals("client")){
                String Qry = "select * from postclient where lower(id)='" + this.id + "'";
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
            }
            else if (this.accounttype.equals("employee")) {
                String Qry = "select * from postemployee where lower(id)='" + this.id.toLowerCase() + "'";
                System.out.println(Qry);
                ResultSet result = statement.executeQuery(Qry);
                while (result.next()) {
                    this.email = result.getString(2).toLowerCase();
                    this.first_name = result.getString(3).toLowerCase();
                    this.last_name = result.getString(4).toLowerCase();
                    this.nationnality = result.getString(5).toLowerCase();
                    this.address = result.getString(6).toLowerCase();
                    this.phone = result.getString(7).toLowerCase();
                    this.deliveryline =Integer.parseInt(result.getString("DELIVERYLINE_ID"));
                    this.jobclass = result.getString("JOBCLASS");
                }
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
    public String getjobtitle() {
        return jobtitle;
    }
    public String getgender() {
        return gender;
    }
    public String getaccounttype(){
        return accounttype;
    }
    public Image getImage() {
        Image profilepic;
        // System.out.println(path+"/src/Resources/IMAGES/ProfilePictures/" + this.id + ".png");
        try{
            profilepic = new Image("/Resources/IMAGES/ProfilePictures/" + this.id + ".png");
        }
        catch(Exception e){
            System.out.println("no image ==> using default image");
            profilepic = new Image("/Resources/IMAGES/ProfilePictures/Default.png");
        }
        return profilepic;
    }
    public int getdeliveryline(){
        return deliveryline;
    }    
    public String getjobclass(){
        return jobclass;
    }
    // set acount details
    public void setemail(String email) {
        this.email = email;
    }
    public void setphone(String phone) {
        this.phone = phone;
    }
    public void setaddress(String address) {
        this.address = address;
    }
    public void setfirstname(String first_name) {
        this.first_name = first_name;
    }
    public void setlastname(String last_name) {
        this.last_name = last_name;
    }
    public void setjobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }
}
