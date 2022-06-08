package Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Controllers.Courier;
import Controllers.Payment;
import Controllers.Receiver;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;

public class Client_Connection extends DataBaseConnection{
    public Client_Connection(Boolean parent) {
        super(parent);
    }
    public void AddMail(String cin_, Courier courier){
        try {
            System.out.println("Start");
            int mail_id = getnextCourierId();
            int receiver_id = getnextReceiverId();
            Receiver receiver = courier.getReceiver();
            // get deliveryline
            int deliveryLine_id = getdeliverylineId(user_account.getaddress(),receiver.getReceiverAddress());
            String qry3 = "INSERT INTO POSTCOURIER_RECEIVER (RECEIVER_ID,FIRSTNAME,LASTNAME, ADDRESS,PHONENBR)"+
                          "VALUES('"+receiver_id+"','"+receiver.getFirstName()+"','"+receiver.getLastName()+
                          "' , '"+receiver.getReceiverAddress()+"' ,'"+receiver.getReceiverPhonenbr()+"')";
            statement.executeUpdate(qry3);
            String qry4 = "INSERT INTO POSTCOURIER (ID, WEIGHT, ADDRESS, COLLECT_DATE, CLIENT_ID, BACKUPPHONENBR, PRICE,RECEIVER_ID,DISCRIPTION,DELIVERYLINE)"+
                          "VALUES('"+"RR"+
                          String.format("%09d", mail_id)+
                          "MA"+"','"+courier.getWeight()+"' , '"+courier.getReceiver().getReceiverAddress()+
                          "' ,TO_DATE('"+courier.getCollectDate()+"', 'DD-MM-YYYY HH24:MI') , '"+
                          cin_+"' , '"+courier.getBackupphonenbr()+"','"+courier.getPrice()+"','"+
                          receiver_id+"','"+courier.getDiscription()+"',"+deliveryLine_id+")";
            
            statement.executeUpdate(qry4);
            System.out.println("Show save a copy");
            Button save_copy = new Button("Save A Copy");
            save_copy.setOnAction(e -> {
                // ask user for outout directory
                SaveCopy(mail_id, receiver);
            });
            App.ExtraButton = save_copy;
            App.CurrentNotification =  "Courier added successfully \n Your Courier id is : "+
                                       "RR"+String.format("%09d", mail_id)+"MA";
            System.out.println("showDialog");

        } catch (Exception e) {
            e.printStackTrace();
            App.CurrentNotification = "Mail not added double check your information";
        }
    }
    private void SaveCopy(int mail_id, Receiver receiver) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory == null) {
            return;
        }
        WriteToFile(
            "RR"+String.format("%09d", mail_id)+"MA",
            user_account.getfirstname(),
            user_account.getlastname(),
            user_account.getaddress(),
            user_account.getphone(),
            receiver.getFirstName(),
            receiver.getLastName(),
            receiver.getReceiverAddress(),
            receiver.getReceiverPhonenbr()
        );
        try {
            System.out.println();
            Runtime.getRuntime().exec(App.path_to_dependencies+"\\assets\\CourierFormCreator.exe --dir="+App.path_to_dependencies+"\\assets"
                ).waitFor();
        } catch (Exception e1) {
            e1.printStackTrace();
            App.CurrentNotification = "Failed to get sources from exe.";
            return;
        }
        try {
            System.out.println(selectedDirectory.getAbsolutePath()+"\\"+"RR"+String.format("%09d", mail_id)+"MA.docx");
            Tools_.copyDirectory(
                new File(App.path_to_dependencies+"\\assets\\result.docx"),
                new File(selectedDirectory.getAbsolutePath()+"\\"+"RR"+String.format("%09d", mail_id)+"MA.docx")
                );
        } catch (IOException e1) {
            e1.printStackTrace();
            App.CurrentNotification = "Failed to copy file go to \n"+
            App.path_to_dependencies+"/result.docx";
            return;
        }
        App.CurrentNotification =   "Courier saved successfully ";
    }
    private int getnextReceiverId() throws SQLException {
        String qry2 = "SELECT COUNT(*) FROM POSTCOURIER_RECEIVER";
        result = statement.executeQuery(qry2);
        
        while (result.next()) {
            return result.getInt(1);
        }
        return 0;
    }
    private int getnextCourierId() throws SQLException {
        String qry1 = "SELECT SUBSTR(MAX(id),3,9) FROM POSTCOURIER";
        result = statement.executeQuery(qry1);
        while (result.next()) {
            return result.getInt(1)+1;
        }
        return 0;
    }
    private boolean WriteToFile(String CourierId, String user_FN, String user_LN, String user_address, String user_Phone, String FirstName, String LastName, String address, String phonenbr) {
        try {
            FileWriter myWriter = new FileWriter(App.path_to_dependencies+"\\assets\\CurrentCourierInfo.txt");
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
            e.printStackTrace();
            App.CurrentNotification = "File not Created";
            return false;
        }
    }
    public ResultSet GetEmails() throws SQLException {
        
        String rs = "select * from POSTCOURIER ";
        System.out.println(rs+"\n ");
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
                App.CurrentNotification = "Failed to get sources.";
            }
            return null;
    }
    public int getdeliverylineId(String Sender_Address,String Receiver_Address) {

        // slice address till M
        Sender_Address = Sender_Address.split(":")[0];
        Receiver_Address = Receiver_Address.split(":")[0];
        // qwery
        String qry = "select id from POSTDELIVERY_LINE where lower(from_)='"+Sender_Address.toLowerCase()+"' and lower(to_)='"+Receiver_Address.toLowerCase()+"'";
        try{
            result = statement.executeQuery(qry);
            while (result.next()) {
                return result.getInt(1);
            }
            throw new Exception("Delivery line not found");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(qry);
        }
        return 0;
    }
    public ArrayList<Payment> GetPayment() {
        String qry = "select * from POSTPAYMENT where client_id='"+user_account.getid()+"'";
        ArrayList<Payment> payments = new ArrayList<Payment>();
        try {
            result = statement.executeQuery(qry);
            while (result.next()) {
                payments.add(new Payment(result.getInt(1),result.getString(2),result.getString(3),result.getString(4)));
            }
            return payments;
        } catch (SQLException e) {
            e.printStackTrace();
            App.CurrentNotification = "Failed to get sources.";
        }
        return null;
    }
    public String CountCourierPayment(int paid) {
        String qry = "select count(*) from POSTCOURIER where Paid="+paid+" and Client_id='"+user_account.getid()+"'";
        System.out.println(qry);
        try {
            result = statement.executeQuery(qry);
            while (result.next()) {
                return result.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            App.CurrentNotification = "Failed to get sources.";
        }
        return null;
    }
    public String CountCourierPaymentAmount() {
        String qry = "select sum(price) from POSTCOURIER where Paid=0 and status='Delivered' and Client_id='"+user_account.getid()+"'";
        try {
            result = statement.executeQuery(qry);
            while (result.next()) {
                return result.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            App.CurrentNotification = "Failed to get sources.";
        }
        return "0";
    }
}
