package Controllers.Employer;

import java.io.IOException;
import java.util.ArrayList;

import Controllers.Profile;
import Main.App;
import Main.DataBaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class CourierTable {

    private DataBaseConnection connection;
    private ArrayList<Courier> couriers;
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
            FXMLLoader loder = new FXMLLoader(getClass().getResource("../../Resources/VIEW/Employer/CourierForm.fxml"));
            Pane root = loder.load();
            CourierForm controller = loder.getController();
            controller.setConnection(connection);
            controller.courier = courier;
            controller.Courier_id.setText(courier.getCourierId());
            controller.Courier_Address.setText(courier.getAddress());
            controller.Courier_Price.setText(courier.getPrice());
            controller.Courier_Collect_Date.setText(courier.getCollectDate());
            if(All){
                controller.PlaceToShowStatus.getChildren().clear();
                Label Status = new Label(courier.getStatus());
                Status.setPrefSize(183, 25);
                Status.setTextAlignment(TextAlignment.CENTER);
                Status.setAlignment(Pos.CENTER);
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


    public void setConnection(DataBaseConnection connection) {
        this.connection = connection;
    }
    @FXML
    private void switchToHomePage(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Resources/VIEW/Employer/Home.fxml"));
            Pane root = loader.load();
            Home controller = loader.getController();
            controller.setConnection(connection);
            // get window parentstage
            App.changeStage(root);
        }
        catch (Exception e) {
            System.out.println("ERREUR :( \n" + e);
        }
    }
    @FXML
    public void showProfile() throws IOException{
        try {
            FXMLLoader loder = new FXMLLoader(getClass().getResource("../Resources/VIEW/Profile.fxml"));
            Pane root = loder.load();
            Profile controller = loder.getController();
            controller.connection=connection;
            App.changeStage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void CloseWindow() {
        connection.Disconnect();
        App.getpStage().close();
    }
    public void MinimizeWindow() {
        App.getpStage().setIconified(true);
    }
}
