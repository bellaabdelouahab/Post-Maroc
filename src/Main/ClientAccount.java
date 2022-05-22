package Main;

public class ClientAccount {
    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private String nationnality;
    private String gender;
    private String address;
    private String jobtitle;
    private String phonenbr;
    // constructor
    ClientAccount(String id,String email,String firstname,String lastname,String nationnality,String gender,String address,String jobtitle,String phonenbr){
        this.id=id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.nationnality = nationnality;
        this.gender = gender;
        this.address = address;
        this.jobtitle = jobtitle;
        this.phonenbr = phonenbr;
    }
    // getters
    public String getId() {
        return id;
    }
    public String getemail() {
        return email;
    }
    public String getphone() {
        return phonenbr;
    }
    public String getaddress() {
        return address;
    }
    public String getfirstname() {
        return firstname;
    }
    public String getlastname() {
        return lastname;
    }
    public String getnationnality() {
        return nationnality;
    }
    public String getjobtitle() {
        return jobtitle;
    }
    public String getgender() {
        return gender;
    }
}
