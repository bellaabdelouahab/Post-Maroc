package Controllers.Client;

import Controllers.Courier;
import Main.App;
import Main.Client_Connection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class create_mail{
    Client_Connection connection;
    static Courier courier = new Courier();
    @FXML Pane forms;
    
    public int currentform=1;
    private form_1 Form1controller ;
    private form_2 Form2controller ;
    @FXML
    private void ValidateMail(){
        if (currentform==1){
            Courier Courier_cach = Form1controller.validateForm1();
            if(Courier_cach==null)return;
            courier=Courier_cach;
            SwitchToForm2();
            currentform++;
            return;
        }
        if (currentform==2){
            Courier Courier_cach = Form2controller.validateForm2();
            if(Courier_cach==null)return;
            courier=Courier_cach;
            // get details from text fields
            String cin_ = connection.getUser_account().getid();
            // add details 
            new Thread(new Runnable() {
                @Override
                public void run() {
                connection.AddMail( cin_,courier);
                }
            });
        } 
        
        
    }
    @FXML
    private void SwitchToForm2(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/VIEW/Client/CourierFillForm_2.fxml"));
            Pane root = loader.load();
            Form2controller = loader.getController();
            Form2controller.setConnection(connection);
            Form2controller.courier=courier;
            forms.getChildren().clear();
            forms.getChildren().add(root);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML 
    public void SwitchToForm1(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/VIEW/Client/CourierFillForm_1.fxml"));
            Pane root = loader.load();
            Form1controller = loader.getController();
            Form1controller.setConnection(connection);
            Form1controller.fillinfo();
            Form1controller.courier=courier;
            forms.getChildren().clear();
            forms.getChildren().add(root);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void switchToHomePage(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/VIEW/Client/Home.fxml"));
            Pane root = loader.load();
            client_home controller = loader.getController();
            controller.setConnection(connection);
            App.changeStage(root);
        }
        catch (Exception e) {
            System.out.println("ERREUR :( \n" + e);
        }
    }
    
    
    @FXML
    private void showProfile(){
        App.ShowProfile();
    }
    @FXML
    private void CloseWindow() {
        App.CloseWindow();
    }
    @FXML   
    private void MinimizeWindow() {
        App.getpStage().setIconified(true);
    }
    @FXML
    private void Logout(){
        App.Logout();
    }
}
