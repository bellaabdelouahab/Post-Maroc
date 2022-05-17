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
        //TODO Auto-generated constructor stub
    }
    private int mail_id;
    public void AddMail(String cin_, Courier courier){
        try {
            String qry1 = "SELECT COUNT(*) FROM POSTCOURIER";
            result = statement.executeQuery(qry1);
            while (result.next()) {
                mail_id = result.getInt(1);
            }
            String qry2 = "SELECT COUNT(*) FROM POSTCOURIER_RECEIVER";
            result = statement.executeQuery(qry2);
            int receiver_id=0;
            while (result.next()) {
                receiver_id = result.getInt(1);
            }
            // generate a 16bite random password
            String qry3 = "INSERT INTO POSTCOURIER (ID, WEIGHT, ADDRESS, COLLECT_DATE, CLIENT_ID, BACKUPPHONENBR, PRICE,RECEIVER_ID,DISCRIPTION)"+
                          "VALUES('"+"RR"+
                          String.format("%09d", mail_id)+
                          "MA"+"','"+courier.getWeight()+"' , '"+courier.getReceiver().getReceiverAddress()+
                          "' ,TO_DATE('"+courier.getCollectDate()+"', 'DD-MM-YYYY HH24:MI') , '"+
                          cin_+"' , '"+courier.getBackupphonenbr()+"','"+courier.getPrice()+"','"+receiver_id+"','"+courier.getDiscription()+"')";
            Receiver receiver = courier.getReceiver();
            String qry4 = "INSERT INTO POSTCOURIER_RECEIVER (RECEIVER_ID,FIRSTNAME,LASTNAME, ADDRESS,PHONENBR)"+
                          "VALUES('"+receiver_id+"','"+receiver.getFirstName()+"','"+receiver.getLastName()+
                          "' , '"+receiver.getReceiverAddress()+"' ,'"+receiver.getReceiverPhonenbr()+"')";
            statement.executeUpdate(qry3);
            statement.executeUpdate(qry4);
            Button save_copy = new Button("Save A Copy");
            // run pdfgenerator on action
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
                    tools_.copyDirectory(
                        new File(System.getProperty("user.dir")+"\\src\\Resources\\OutputCourierForm\\DocxForm\\result.docx"),
                        selectedDirectory);
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
}
