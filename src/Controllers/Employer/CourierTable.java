package Controllers.Employer;

import java.io.IOException;
import java.util.ArrayList;

import Controllers.Courier;
import Main.App;
import Main.Employer_Connection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class CourierTable {

    private Employer_Connection connection;
    private ArrayList<Courier> couriers;
    @FXML Pane ChildPaneXS;
    @FXML
    private VBox CourierVbox2;
    @FXML
    private VBox CourierVbox1;
    

    public void SetData(Boolean Waiting, Boolean Confirmed,Boolean All) throws IOException {
        if (Waiting){
            this.couriers = connection.getCourier("'Waiting'");
        }
        else if(Confirmed){
            this.couriers = connection.getCourier("'Supported'");
        }
        else if(All){
            this.couriers = connection.getCourier("'Waiting' , 'Supported' , 'Delivered'");
        }
        else {
            return;
        }
        // loop over couriers with index  
        if(couriers == null){
            System.out.println("No couriers");
        }
        for (Courier courier : couriers) {
            // get the courier
            FXMLLoader loder = new FXMLLoader(getClass().getResource("/Resources/VIEW/Employer/CourierForm.fxml"));
            Pane root = loder.load();
            CourierForm controller = loder.getController();
            controller.setConnection(connection);
            controller.courier = courier;
            controller.PrentController = this;
            controller.Courier_id.setText(courier.getCourierId());
            controller.clientname.setText(connection.getclientFullname(courier.getClientId()));
            controller.Courier_Address.setText(courier.getAddress());
            controller.Courier_Price.setText(courier.getPrice().toString());
            controller.Courier_Collect_Date.setText(courier.getCollectDate());
            if(All){
                controller.PlaceToShowStatus.getChildren().clear();
                Label Status = new Label(courier.getStatus());
                Status.setPrefSize(183, 25);
                Status.setTextAlignment(TextAlignment.CENTER);
                Status.setAlignment(Pos.CENTER);
                Status.setStyle("-fx-text-fill:#fff");
                controller.PlaceToShowStatus.getChildren().add(Status);
            }
            else if(Confirmed){
                controller.PlaceToShowStatus.getChildren().clear();
            }
            if(couriers.indexOf(courier)%2==0){
                CourierVbox1.getChildren().add(root);
                CourierVbox1.setPrefHeight(CourierVbox1.getPrefHeight()+root.getPrefHeight());
            }
            else{
                CourierVbox2.getChildren().add(root);
                CourierVbox2.setPrefHeight(CourierVbox2.getPrefHeight()+root.getPrefHeight());
            }
        }
    }
    public void ShowAllinfo(String CourierId){
        FXMLLoader loder = new FXMLLoader(getClass().getResource("/Resources/VIEW/Employer/CourierInfo.fxml"));
        
        try {
            Pane root = loder.load();
            CourierInfoForm controller = loder.getController();
            controller.setConnection(connection);
            controller.Fillinfo(CourierId,true);
            controller.closebutton.setOnAction(e->{
                App.BaseWindow.getChildren().remove(root);
                ChildPaneXS.setEffect(null);
            });
            root.setOnMouseClicked(arg0->{
                App.BaseWindow.getChildren().remove(root);
                ChildPaneXS.setEffect(null);
            });
            root.setLayoutX(137);
            root.setLayoutY(77);
            App.BaseWindow.getChildren().add(root);
            GaussianBlur blur =  new GaussianBlur();
            blur.setRadius(4);
            ChildPaneXS.setEffect(blur);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void setConnection(Employer_Connection connection) {
        this.connection = connection;
    }
    @FXML
    private void switchToHomePage(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/VIEW/Employer/Home.fxml"));
            Pane root = loader.load();
            Home controller = loader.getController();
            controller.setConnection(connection);
            controller.init();
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
        App.getprimaryStage().setIconified(true);
    }
    @FXML
    private void Logout(){
        App.Logout();
    }
}
