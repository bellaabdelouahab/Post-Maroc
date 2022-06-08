package Main;

import java.sql.SQLException;
import java.util.ArrayList;

import Controllers.Courier;
import Controllers.Payment;

public class Employer_Connection extends DataBaseConnection {

    public Employer_Connection(Boolean parent) {
        super(parent);
    }
    public ArrayList<Courier> getCourier(String status) {
        // TODO: please fix this
        System.out.println(getUser_account().getdeliveryline());
        String qry1 = "select * from POSTCOURIER where status in("+status+") and deliveryLine="+getUser_account().getdeliveryline()+"  ORDER BY id ";
        try {
            System.out.println(qry1);
            result = statement.executeQuery(qry1);
            ArrayList<Courier> couriers = new ArrayList<Courier>();
            while (result.next()) {
                String id = result.getString(1);
                float weight = result.getFloat(2);
                String address = result.getString(3);
                String collect_date = result.getString(4);
                String client_id = result.getString(5);
                String backup_phonenbr = result.getString(6);
                float price = result.getFloat(7);
                String status_ = result.getString(8);
                String receiver_id = result.getString(9);
                String creationdate = result.getString(10);
                String discription = result.getString(11);
                // create a courier class with previous information
                couriers.add(new Courier(id, weight, address, collect_date, client_id, backup_phonenbr, price,status_, receiver_id, creationdate, discription));   
            }
            return couriers;
        } catch (SQLException e) {
            App.CurrentNotification = "Failed to get sources.";
            
        }
        return null;
    }
    public Courier getCourierbyid(String CourierId,Boolean DeliveryLine) {
        // TODO: please fix this
        System.out.println(getUser_account().getdeliveryline());
        String qry1 = "select * from POSTCOURIER where id='"+CourierId+"' and deliveryLine="+getUser_account().getdeliveryline()+"  ORDER BY id ";
        if(!DeliveryLine)
            qry1 = "select * from POSTCOURIER where id='"+CourierId+"'  ORDER BY id ";
        try {
            System.out.println(qry1);
            result = statement.executeQuery(qry1);
            while (result.next()) {
                String id = result.getString(1);
                float weight = result.getFloat(2);
                String address = result.getString(3);
                String collect_date = result.getString(4);
                String client_id = result.getString(5);
                String backup_phonenbr = result.getString(6);
                float price = result.getFloat(7);
                String status_ = result.getString(8);
                String receiver_id = result.getString(9);
                String creationdate = result.getString(10);
                String discription = result.getString(11);
                // create a courier class with previous information
                return new Courier(id, weight, address, collect_date, client_id, backup_phonenbr, price,status_, receiver_id, creationdate, discription);   
            }
            App.CurrentNotification = "Courier Does Not Exist.";
        } catch (SQLException e) {
            App.CurrentNotification =  "Failed to get sources,courier not found.";
            
        }
        return null;
    }
    
    public String getclientFullname(String clientId) {
        String qry1 = "select First_Name,Last_Name from POSTCLIENT where id='"+clientId+"'";
        try {
            result = statement.executeQuery(qry1);
            while (result.next()) {
                String First_Name = result.getString(1);
                String Last_Name = result.getString(2);
                return First_Name+" "+Last_Name;
            }
        } catch (SQLException e) {
            App.CurrentNotification = "please contact your administrator.";
            
        }
        return null;
    }
    public void UpdateCourierStatus(String CourierId, String Status) {
        String qry1 = "update POSTCOURIER set status='"+Status+"' where id='" + CourierId + "'";
        try {
            statement.executeUpdate(qry1);
            App.CurrentNotification =  "Courier successfully Updated ";
        } catch (SQLException e) {
            App.CurrentNotification =  "Unable to Update try later or contact your administrator";
        }
    }
    public ClientAccount getClient(String ClientId) {
        String qry1 = "select * from POSTCLIENT where lower(id)='"+ClientId.toLowerCase()+"'";
        try {
            result = statement.executeQuery(qry1);
            while (result.next()) {
                String id = result.getString(1);
                String email = result.getString(2);
                String firstname = result.getString(3);
                String lastname = result.getString(4);
                String nationnality = result.getString(5);
                String gender = result.getString(6);    
                String address = result.getString(7);
                String jobtitle = result.getString(8);
                String phonenbr = result.getString(9);
                return new ClientAccount(id,email, firstname, lastname, nationnality,gender,address,jobtitle,phonenbr);
            }
            App.CurrentNotification = "Client Dose not Exist";
        }
        catch(Exception e){
            System.out.println("error at emp con line 115");
            App.CurrentNotification = "Connection Problem, Please Restart App";
        }
        return null;
    }
    public void deleteClient(String clientid) {
        // delete all related courier
        String qry = "delete from POSTCOURIER where client_id='"+clientid+"'";
        try {
            statement.executeUpdate(qry);
        } catch (SQLException e) {
            App.CurrentNotification = "Failed to delete couriers";
        }
        String qry1 = "delete from POSTCLIENT where id='"+clientid+"'";
        try {
            statement.executeUpdate(qry1);
            App.CurrentNotification =  "Client successfully deleted ";
        } catch (SQLException e) {
            App.CurrentNotification =  "Unable to delete try later or contact your administrator";
        }
    }
    public void updateClientinfo(String clientid, String email, String firstname, String lastname,
     String nationnality,String gender,String address,String jobtitle,String phonenbr) {
        String qry = "update POSTCLIENT set first_name='"+firstname+
        "',last_name='"+lastname+"',address='"+address+
        "',phonenbr='"+phonenbr+"',JOB_TITLE='"+jobtitle+
        "' where lower(id)='"+clientid.toLowerCase()+"'";
        try {
            statement.executeUpdate(qry);
            App.CurrentNotification =  "Client successfully updated ";
        } catch (SQLException e) {
            e.printStackTrace();
            App.CurrentNotification =  "Unable to update try later or contact your administrator";
        }
     }
    public ArrayList<Payment> getClientPayment(String clientid) {
        String qry = "select * from POSTPayment where lower(client_id)='"+clientid.toLowerCase()+"'";
        try {
            result = statement.executeQuery(qry);
            ArrayList<Payment> payment = new ArrayList<Payment>();
            while (result.next()) {
                int id = result.getInt(1);
                String client_id = result.getString(2);
                String amount = result.getString(3);
                String date = result.getString(4);
                payment.add(new Payment(id,client_id,amount,date));
            }
            if(!payment.isEmpty()){
                return payment;
            }
            App.CurrentNotification = "Client Dose not Exist";
        }
        catch(Exception e){
            e.printStackTrace();
            App.CurrentNotification = "Connection Problem, Please Restart App";
        }
        return null;
    }
    public float getpandingpayment(String clientid) {
        String qry = "select sum(price) from postcourier where status='Delivered' and paid=0 and client_id='"+clientid+"'";
        try {
            result = statement.executeQuery(qry);
            while (result.next()) {
                return result.getFloat(1);
            }
            App.CurrentNotification = "Client Dose not Exist";
        }
        catch(Exception e){
            e.printStackTrace();
            App.CurrentNotification = "Connection Problem, Please Restart App";
        }
        return 0;
    }
    public void Pay(String clientId, Float Payment) {
        String qry = "Select max(Payment_id) from POSTPayment";
        try {
            result = statement.executeQuery(qry);
            int nextpaymentid=0;
            while (result.next()) {
                nextpaymentid = result.getInt(1)+1;
            }
            if (nextpaymentid==0) {
                throw new Exception("Table payment error");
            }
            System.out.println("payment id :"+nextpaymentid);
            
            String qry2 = "select id,price from POSTCOURIER where lower(client_id)='"+clientId.toLowerCase()+
                    "' and status='Delivered' and paid=0 order by price DESC ";
            result = statement.executeQuery(qry2);
            ArrayList<String> paidcouriersid = new ArrayList<String>();
            while (result.next()) {
                    String id = result.getString(1);
                    Float paid = result.getFloat(2);
                    System.out.println("id="+id+" paid="+paid);
                    if((Payment-paid)>=0){
                        paidcouriersid.add(id);
                        Payment-=paid;
                    }
                }
                for (int i = 0; i < paidcouriersid.size(); i++) {
                    String qry3 = "update POSTCOURIER set paid=1 where id='"+paidcouriersid.get(i)+"'";
                    statement.executeUpdate(qry3);
                }
                if(Payment==0){
                    App.CurrentNotification = "Payment Successful";
                }
                else{
                    App.CurrentNotification = "Payment Successful, Your change is "+Payment+"You Still need to pay "+this.getpandingpayment(clientId);
                }
            String qry4 = "insert into POSTPAYMENT (PAYMENT_ID, CLIENT_ID, AMOUNT) VALUES ('"+nextpaymentid+"', '"+clientId+"', '"+Payment+"')";
            statement.executeQuery(qry4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getcity(String employee_id,String column) {
        String qry = "select "+column+"_ from POSTDELIVERY_LINE where id=(select deliveryline_id from POSTEMPLOYEE where lower(id) = lower('"+employee_id+"'))";
        try {
            result = statement.executeQuery(qry);
            while (result.next()) {
                return result.getString(1);
            }
            App.CurrentNotification = "Employee Dose not Exist";
        }
        catch(Exception e){
            e.printStackTrace();
            App.CurrentNotification = "Connection Problem, Please Restart App";
        }
        return null;
    }
    public String getName(String employeeid) {
        String qry = " select concat(concat(first_name,' '),last_name) from POSTEMPLOYEE where lower(id)='"+employeeid.toLowerCase()+"'";
        try {
            result = statement.executeQuery(qry);
            while (result.next()) {
                return result.getString(1);
            }
            App.CurrentNotification = "Employee Dose not Exist";
        }
        catch(Exception e){
            e.printStackTrace();
            App.CurrentNotification = "Connection Problem, Please Restart App";
        }
        return null;
    }
    public void updatedeliveryline(String id, String from, String to) {
        String qry = "select id from POSTDELIVERY_LINE where FROM_='"+from+"' and TO_='"+to+"'";
        try {
            int id_=0;
            result = statement.executeQuery(qry);
            while (result.next()) {
                id_ = result.getInt(1);
            }
            if(id_==0){
                throw new Exception("Table deliveryline error");
            }
            String qry2 = "update POSTEMPLOYEE set deliveryline_id='"+id_+"' where lower(id)='"+id.toLowerCase()+"'";
            statement.executeUpdate(qry2);
            App.CurrentNotification = "Delivery Line Updated";
        }
        catch(Exception e){
            e.printStackTrace();
            App.CurrentNotification = "Connection Problem, Please Restart App";
        }
    }
}
