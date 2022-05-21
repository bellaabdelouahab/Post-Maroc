package Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import Controllers.Courier;
import Controllers.Receiver;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;

public class Client_Connection extends DataBaseConnection{
    public Client_Connection(Boolean parent) {
        super(parent);
    }
    public void AddMail(String cin_, Courier courier){
        try {
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
            Button save_copy = new Button("Save A Copy");
            save_copy.setOnAction(e -> {
                // ask user for outout directory
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
                //  open an exe 
                try {
                    System.out.println();
                    Runtime.getRuntime().exec(
                        System.getProperty("user.dir")+"\\src\\Main\\pythoncode\\CourierFormCreator.exe"
                        );
                } catch (IOException e1) {
                    e1.printStackTrace();
                    App.ShowNotificationWindow("Error",  "Failed to get sources from exe.",null);
                }
                try {
                    System.out.println(selectedDirectory.getAbsolutePath()+"\\"+"RR"+String.format("%09d", mail_id)+"MA.docx");
                    tools_.copyDirectory(
                        new File(System.getProperty("user.dir")+"\\src\\Resources\\OutputCourierForm\\DocxForm\\result.docx"),
                        new File(selectedDirectory.getAbsolutePath()+"\\"+"RR"+String.format("%09d", mail_id)+"MA.docx")
                        );
                } catch (IOException e1) {
                    e1.printStackTrace();
                    App.ShowNotificationWindow("Error",  "Failed to copy file go to \n"+
                    System.getProperty("user.dir")+"\\src\\Resources\\OutputCourierForm\\DocxForm\\result.docx",null);
                }
                App.ShowNotificationWindow("info",  "Courier saved successfully ",null);
            });
            App.ShowNotificationWindow("info",  "Courier added successfully \n Your Courier id is : "+
                                       "RR"+String.format("%09d", mail_id)+"MA",save_copy);
        } catch (Exception e) {
            e.printStackTrace();
            App.ShowNotificationWindow("info", "Mail not added double check your information",null);
        }
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
        String qry1 = "SELECT COUNT(*) FROM POSTCOURIER";
        result = statement.executeQuery(qry1);
        while (result.next()) {
            return result.getInt(1);
        }
        return 0;
    }
    private boolean WriteToFile(String CourierId, String user_FN, String user_LN, String user_address, String user_Phone, String FirstName, String LastName, String address, String phonenbr) {
        try {
            FileWriter myWriter = new FileWriter(System.getProperty("user.dir")+"\\src\\Resources\\OutputCourierForm\\prototype\\CurrentCourierInfo.txt");
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
            App.ShowNotificationWindow("Error",  "File not Created",null);
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
                App.ShowNotificationWindow("Error",  "Failed to get sources.",null);
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
}
