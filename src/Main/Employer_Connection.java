package Main;

import java.sql.SQLException;
import java.util.ArrayList;

import Controllers.Courier;

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
            App.ShowNotificationWindow("Error",  "Failed to get sources.",null);
            
        }
        return null;
    }
    public Courier getCourierbyid(String CourierId) {
        // TODO: please fix this
        System.out.println(getUser_account().getdeliveryline());
        String qry1 = "select * from POSTCOURIER where id='"+CourierId+"' and deliveryLine="+getUser_account().getdeliveryline()+"  ORDER BY id ";
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
        } catch (SQLException e) {
            App.ShowNotificationWindow("Error",  "Failed to get sources,courier not found.",null);
            
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
            App.ShowNotificationWindow("Error",  "please contact your administrator.",null);
            
        }
        return null;
    }
}
