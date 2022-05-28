package Controllers.Employer;

import java.util.ArrayList;

import Controllers.Payment;
import Main.Employer_Connection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class ClientPayment {
    Employer_Connection connection;
    @FXML private Pane paymentpane;
    @FXML private VBox vcontainer;
    @FXML Label pandingpayment;
    @FXML Button closebutton;
    @FXML TextField Amount;
    String clientId;

    

    public void SetData(String clientid) {
        ArrayList<Payment> payments = connection.getClientPayment(clientid);
        if(payments == null){
            closebutton.fire();
            return;
        }
        // loop over
        for (Payment payment : payments){
            Pane pen = paymentpane(payment.getAMOUNT(),payment.getDATE());
            vcontainer.setPrefHeight(vcontainer.getPrefHeight()+pen.getPrefHeight());
            vcontainer.getChildren().add(pen);
        }
        pandingpayment.setText(String.valueOf(connection.getpandingpayment(clientid)));
        clientId = clientid;
    }
    private Pane paymentpane(String Amount, String Date) {
        String style_ = "-fx-background-color:  #11111100;-fx-background-radius:0;-fx-border-radius: 0;-fx-text-fill: white;";
        Pane cont = new Pane();
        cont.setPrefSize(200, 50);
        TextField amount = new TextField(Amount);
        amount.setPrefSize(140, 25);
        amount.setLayoutX(25);
        amount.setStyle(style_);
        Label dh = new Label("DH");
        dh.setPrefSize(17, 25);
        dh.setLayoutX(165);
        dh.setStyle(style_);
        TextField date = new TextField(Date);
        date.setPrefSize(140, 25);
        date.setLayoutX(35);
        date.setLayoutY(25);
        date.setStyle(style_);
        Line line =  new Line();
        line.setStartX(-100);
        line.setStartY(0);
        line.setEndX(100);
        line.setLayoutX(100);
        line.setLayoutY(50);
        cont.getChildren().addAll(amount,dh,date,line);
        return cont;
    }
    @FXML
    private void PayAll(){
        Float payment = Float.parseFloat(pandingpayment.getText());
        System.out.println("runing");
        connection.Pay(clientId,payment);
    }
    @FXML
    private void PayAmount(){
        Float paymentamount = Amount.getText().isEmpty() ? 0.1f : Float.parseFloat(Amount.getText());
        connection.Pay(clientId,paymentamount);
    }
    public void setConnection(Employer_Connection connection) {
            this.connection = connection;
        }
}
